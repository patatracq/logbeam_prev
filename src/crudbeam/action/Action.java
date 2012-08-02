package crudbeam.action;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import crudbeam.event.ErrorEvent;

public abstract class Action implements ApplicationEventPublisherAware {

	private static final Logger logger = Logger.getLogger( Action.class );
	
	private ApplicationEventPublisher publisher;
	
	public void execute( Object source ) {
		
		logger.debug( "Executing action " + this );

		try {
			executeAction( source );
		} catch ( Exception e ) {
			error( "Error in action " + this, e );
		}
		
		logger.debug( "Action " + this + " completed" );
	}
	
	protected abstract void executeAction( Object source );
	
	@Override
	public void setApplicationEventPublisher( ApplicationEventPublisher publisher ) {

		this.publisher = publisher;
	}
	
	protected void error( Exception e ) {
		
		publisher.publishEvent( new ErrorEvent( this, e ) );
	}

	protected void error( String message, Exception e ) {
		
		publisher.publishEvent( new ErrorEvent( this, message, e ) );
	}
}
