package crudbeam.action;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import crudbeam.event.EventFactory;


public class FireEvent extends Action implements ApplicationEventPublisherAware {

	private static Logger logger = Logger.getLogger( FireEvent.class );
	
	private ApplicationEventPublisher publisher;
	private EventFactory eventFactory;
	
	@Override
	protected void executeAction( Object source ) {
		
		logger.debug( "Executing action FireEvent" );
		
		if ( eventFactory != null ) {
			publisher.publishEvent( eventFactory.createEvent( source ) );
		}
		
		logger.debug( "Done" );
	}

	@Override
	public void setApplicationEventPublisher( ApplicationEventPublisher publisher ) {
		
		this.publisher = publisher;
	}
	
	public void setEventFactory( EventFactory eventFactory ) {

		this.eventFactory = eventFactory;
	}
}
