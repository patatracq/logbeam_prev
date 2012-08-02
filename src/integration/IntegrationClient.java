package integration;

import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.support.MessageBuilder;

import crudbeam.cyberspace.CyberspaceClient;
import crudbeam.cyberspace.CyberspaceReply;
import crudbeam.cyberspace.CyberspaceRequest;


public class IntegrationClient implements CyberspaceClient {

	private static Logger logger = Logger.getLogger( IntegrationClient.class );
	private static Semaphore channelLock = new Semaphore( 1 );
	
	private MessageChannel requestChannel;
	private PollableChannel replyChannel;
	
	private Integer timeout = 60;

	@Override
	public CyberspaceReply< ? > callServer( CyberspaceRequest request ) {
		
		Message< CyberspaceRequest > requestMessage = MessageBuilder.withPayload( request ).build();
		
		try {
			channelLock.acquire();
		} catch ( InterruptedException e ) {
			logger.warn( "Interrupted while waiting for channel lock", e );
		}
		
		requestChannel.send( requestMessage );
		
		@SuppressWarnings( "unchecked" )
		Message< CyberspaceReply< ? > > replyMessage = (Message< CyberspaceReply< ? > >) replyChannel.receive( timeout * 1000L );
		
		channelLock.release();
		
		return replyMessage.getPayload();
	}

	public void setRequestChannel( MessageChannel requestChannel ) {
		
		this.requestChannel = requestChannel;
	}

	public void setReplyChannel( PollableChannel replyChannel ) {
		
		this.replyChannel = replyChannel;
	}
	
	public void setTimeout( Integer timeout ) {
		
		this.timeout = timeout;
	}
}
