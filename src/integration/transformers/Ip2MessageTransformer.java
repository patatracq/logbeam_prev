package integration.transformers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;

public class Ip2MessageTransformer {
	
	private static final Logger logger = Logger.getLogger( Ip2MessageTransformer.class );
	
	private class IpMessage implements Message< String > {

		private Map< String, Object > headers;
		private String payload;
		
		public IpMessage() {
			
			headers = new HashMap< String, Object >();
		}
		
		@Override
		public MessageHeaders getHeaders() {
			
			return new MessageHeaders( headers );
		}
		
		public void setHeader( String name, Object value ) {
			
			headers.put( name, value );
		}

		@Override
		public String getPayload() {

			return payload;
		}
		
		public void setPayload( String payload ) {
			
			this.payload = payload;
		}
	}

	@Transformer
	public Message< ? > ip2message( Message< ? > ipMessage ) {
		
		logger.debug( "Ip2MessageTransformer input: " + ipMessage );
		
		String payloadIncludingHeaders = new String( (byte[]) ipMessage.getPayload() );
		String headersString = payloadIncludingHeaders.substring( payloadIncludingHeaders.indexOf( "{" ) + 1, payloadIncludingHeaders.indexOf( "}" ) );
		String payloadString = payloadIncludingHeaders.substring( payloadIncludingHeaders.indexOf( "}" ) + 1 );
		
		IpMessage message = new IpMessage();
		message.setPayload( payloadString );
		
		Iterator< Entry< String, Object > > iterator = ipMessage.getHeaders().entrySet().iterator();
		while ( iterator.hasNext() ) {
			Entry< String, Object > header = iterator.next();
			message.setHeader( header.getKey(), header.getValue() );
		}
		
		StringTokenizer headerTokenizer = new StringTokenizer( headersString, "," );
		while ( headerTokenizer.hasMoreTokens() ) {
			StringTokenizer headerValueTokenizer = new StringTokenizer( headerTokenizer.nextToken(), "=" );
			String name = headerValueTokenizer.nextToken().trim();
			String value = headerValueTokenizer.nextToken().trim();
			
			message.setHeader( name, value );
		}
		
		Message< ? > transformedMessage = MessageBuilder.fromMessage( message ).build();
		
		logger.debug( "Ip2MessageTransformer output: " + transformedMessage );
		return transformedMessage;
	}
}
