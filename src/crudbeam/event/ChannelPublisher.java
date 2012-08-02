package crudbeam.event;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;



public class ChannelPublisher implements ApplicationListener< BusinessObjectEvent > {

	private MessageChannel channel;
	
	@Override
	public void onApplicationEvent( BusinessObjectEvent event ) {

		Message< BusinessObjectEvent > eventMessage = MessageBuilder.withPayload( event ).build();
		channel.send( eventMessage );
	}

	@Required
	public void setChannel( MessageChannel channel ) {
		
		this.channel = channel;
	}
}
