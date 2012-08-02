/*
 * This file is part of log-beam.
 * 
 * log-beam is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * log-beam is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with log-beam.  If not, see <http://www.gnu.org/licenses/>.
 */

package integration.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer;

/**
 * A simple publisher for TCP. Clients that connect to the server socket of
 * the publisher will recieve events until they disconnect. No handshake for
 * connect or disconnect operations is necessary.
 * 
 * Events are in the form of Spring Integration messages, serialized with ByteArrayCrLfSerializer.
 * 
 * Here's an example of client side configuration:
 * <code><ip:tcp-connection-factory id="eventConnectionFactory"
 * 			type="client"
 * 			host="localhost"
 * 			port="16102"
 * 			single-use="false" />
 * 			
 * 	<ip:tcp-inbound-channel-adapter id="eventAdapter"
 * 			channel="ipEventChannel"
 * 			connection-factory="eventConnectionFactory"
 * 			client-mode="true" /></code>
 *
 * Please note:
 * - The type of the connection factory is client.
 * - The connection factory is used for several events (<code>single-use="false"</code>).
 * - The inbound adapter is in client mode. This requires at least Spring Integration 2.1.0 (I think).
 * 
 * The server configuration is simpler:
 * <code><bean id="clientTcpPublisher" class="integration.tcp.TcpPublisher">
 * 		<constructor-arg value="16102" />
 * 	</bean></code>
 * 
 * The publisher itself is configured as a service-activator (see Spring Intgegration reference).
 * 
 * @author Jonas Stråle
 *
 */
public class TcpPublisher {

	/**
	 * Log4j logger.
	 */
	private static final Logger logger = Logger.getLogger( TcpPublisher.class );
	
	/**
	 * Active socket connetions list.
	 */
	private List< Socket > subscribersSockets;
	
	/**
	 * Serializer used to serialize outgoing events.
	 * TODO: Make it configurable.
	 */
	private ByteArrayCrLfSerializer serializer = new ByteArrayCrLfSerializer();

	/**
	 * Creates new publisher. It will establish aa server socket imediately.
	 * 
	 * @param port Server socket port.
	 */
	public TcpPublisher( Integer port ) {
		
		/*
		 * Final port number. Has to be final due in order to be reachable from
		 * the inline Runnable class.
		 */
		final int tcpPort = port;
		
		/*
		 * Create the active socket connections list. It has be thread safe since
		 * connections will be manipulated from two threads.
		 */
		subscribersSockets = new CopyOnWriteArrayList< Socket >();

		/*
		 * Server socket thread that listens for new connections.
		 */
		Thread listenerThread = new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				try {
					/*
					 * Establish the server socket.
					 */
					ServerSocket socket = new ServerSocket( tcpPort );
					logger.info( "Listening for connections on port " + Integer.toString( tcpPort ) );
					
					/*
					 * Loop forever.
					 */
					while ( true ) {
						/*
						 * Listen for a new connection.
						 */
						logger.debug( "Accepting new connection on port " + Integer.toString( tcpPort ) );
						Socket connection = socket.accept();
						
						/*
						 * Add the new connection to the active connections list.
						 */
						logger.debug( "New connection from " + connection.getRemoteSocketAddress() );
						subscribersSockets.add( connection );
					}
				} catch ( IOException e ) {
					
					/*
					 * Convert any I/O exceptions to runtime exceptions, according
					 * to the Spring policy.
					 */
					throw new RuntimeException( e );
				}
			}	
		}, "Tcp-Publisher-Thread" );

		/*
		 * Start the listener thread.
		 */
		listenerThread.start();
	}
	
	/**
	 * The service activator method that Spring Integration will call when there's a
	 * new message on the input-channel.
	 * 
	 * @param event Event message that will be sent out on each active connection.
	 */
	@ServiceActivator
	public void publish( Message< String > event ) {
		
		logger.debug( "New message to publish to " + Integer.toString( subscribersSockets.size() ) + " subscriber(s): " + event );

		/*
		 * Create a list with connections that aren't no longer active and therefore will be
		 * deleted.
		 */
		List< Socket > deleteList = new ArrayList< Socket >();
		Iterator< Socket > connectionIterator = subscribersSockets.iterator();
		while ( connectionIterator.hasNext() ) {
			
			Socket connection = connectionIterator.next();
			if ( connection.isConnected() ) {
				try {
					logger.debug( "Sending event to client " +
							connection.getInetAddress() + ":" +
							connection.getPort() + ": " +
							event.getPayload() );
					serializer.serialize( ( (String) event.getPayload() ).getBytes(), connection.getOutputStream() );
				} catch ( IOException e ) {
					logger.debug( "Exception sending event to client (deleting connection) " +
							connection.getInetAddress() + ":" +
							connection.getPort() + ": " +
							event.getPayload(), e );
					deleteList.add( connection );
				}
			} else {
				deleteList.add( connection );
			}
		}
		
		Iterator< Socket > deleteIterator = deleteList.iterator();
		while ( deleteIterator.hasNext() ) {
			subscribersSockets.remove( deleteIterator.next() );
		}
	}
}
