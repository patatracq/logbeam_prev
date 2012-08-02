package logbeam.client.event;

import org.springframework.context.ApplicationEvent;

import crudbeam.event.EventFactory;



public class RegexUnfocusEventFactory implements EventFactory {

	@Override
	public ApplicationEvent createEvent( Object source ) {
		
		return new RegexUnfocusEvent( source );
	}

}
