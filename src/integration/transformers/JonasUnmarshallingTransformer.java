package integration.transformers;

import jonas.JonasUnmarshaller;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;

public class JonasUnmarshallingTransformer {

	private static final Logger logger = Logger.getLogger( JonasUnmarshallingTransformer.class );
	
	@Transformer
	public Message< ? > unmarshal( Message< String > message ) {
	
		logger.debug( (String) message.getPayload() );
		
		JonasUnmarshaller unmarshaller = new JonasUnmarshaller();
		Message< ? > transformedMessage = MessageBuilder.withPayload( unmarshaller.unmarshal( (String) message.getPayload() ) ).copyHeaders( message.getHeaders() ).build();

		return transformedMessage;
	}
}
