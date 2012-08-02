package crudbeam.event;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;

import util.text.ExceptionMessageBuilder;


public class ErrorEvent extends ApplicationEvent {

	private static Logger logger = Logger.getLogger( ErrorEvent.class );
	
	private static final long serialVersionUID = -7721392839540108195L;

	private String message;
	
	public ErrorEvent( Object source, Exception e ) {
		
		super( source );
		logger.error( e );
		this.message = ExceptionMessageBuilder.getMessage( e );
	}
	
	public ErrorEvent( Object source, String message, Exception e ) {
		
		super( source );
		logger.error( message, e );
		this.message = message + " Exception: " + ExceptionMessageBuilder.getMessage( e );
	}
	
	public String getMessage() {
		
		return message;
	}
}
