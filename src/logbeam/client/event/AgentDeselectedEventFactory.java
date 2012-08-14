package logbeam.client.event;

import org.springframework.context.ApplicationEvent;

import crudbeam.event.EventFactory;



public class AgentDeselectedEventFactory implements EventFactory {

	@Override
	public ApplicationEvent createEvent( Object source ) {
		
		return new AgentDeselectedEvent( source );
	}
}
