package crudbeam.action;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import crudbeam.event.EventFactory;


public class FireEvent extends Action implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;
	private EventFactory eventFactory;
	
	@Override
	protected void executeAction( Object source ) {
		
		if ( eventFactory != null ) {
			publisher.publishEvent( eventFactory.createEvent( source ) );
		}
	}

	@Override
	public void setApplicationEventPublisher( ApplicationEventPublisher publisher ) {
		
		this.publisher = publisher;
	}
	
	public void setEventFactory( EventFactory eventFactory ) {

		this.eventFactory = eventFactory;
	}
}
