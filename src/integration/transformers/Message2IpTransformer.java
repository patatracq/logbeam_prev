package integration.transformers;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;

public class Message2IpTransformer {

	private static final Logger logger = Logger.getLogger( Message2IpTransformer.class );
	
	@Transformer
	public Message< ? > message2Ip( Message< ? > message ) {
	
		logger.debug( "Message2IpTransformer input: " + message );
		
		Message< ? > ipMessage = MessageBuilder.withPayload( message.getHeaders().toString() + message.getPayload().toString() ).copyHeaders( message.getHeaders() ).build();
		
		logger.debug( "Message2IpTransformer output: " + ipMessage );
		return ipMessage;
	}
}
