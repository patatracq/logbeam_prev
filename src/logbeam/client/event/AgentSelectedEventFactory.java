package logbeam.client.event;

import org.springframework.context.ApplicationEvent;

import crudbeam.event.EventFactory;



public class AgentSelectedEventFactory implements EventFactory {

	@Override
	public ApplicationEvent createEvent( Object source ) {
		
		return new AgentSelectedEvent( source );
	}
}
