package logbeam.client.event;

import org.springframework.context.ApplicationEvent;


@SuppressWarnings( "serial" )
public class AgentDeselectedEvent extends ApplicationEvent {

	public AgentDeselectedEvent( Object source ) {

		super( source );
	}
}
