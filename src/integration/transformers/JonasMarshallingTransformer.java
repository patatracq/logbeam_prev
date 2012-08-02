package integration.transformers;

import jonas.JonasMarshaller;
import logbeam.Constants;

import org.apache.log4j.Logger;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;

public class JonasMarshallingTransformer {

	private static final Logger logger = Logger.getLogger( JonasMarshallingTransformer.class );
	
	private Boolean formattedOutput = false;
	
	@Transformer
	public Message< String > marshal( Message< ? > message ) {
		
		JonasMarshaller marshaller = new JonasMarshaller( formattedOutput );
		Message< String > jonasMessage = MessageBuilder.withPayload( marshaller.marshal( message.getPayload() ) ).copyHeaders( message.getHeaders() ).setHeader( Constants.PAYLOAD_CLASS, message.getPayload().getClass().getName() ).build();
		
		logger.debug( jonasMessage.toString() );
		return jonasMessage;
	}
	
	public Boolean getFormattedOutput() {
		
		return formattedOutput;
	}
	
	public void setFormattedOutput( Boolean formattedOutput ) {
		
		this.formattedOutput = formattedOutput;
	}
}
