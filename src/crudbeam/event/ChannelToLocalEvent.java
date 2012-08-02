package crudbeam.event;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.ServiceActivator;


public class ChannelToLocalEvent implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;
	
	@ServiceActivator
	public void publishLocally( Message< ? > event ) {
		
		publisher.publishEvent( (BusinessObjectEvent) event.getPayload() );
	}
	
	@Required
	public void setApplicationEventPublisher( ApplicationEventPublisher publisher ) {
		
		this.publisher = publisher;
	}
}
