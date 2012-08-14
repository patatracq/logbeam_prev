package logbeam.client.event;

import org.springframework.context.ApplicationEvent;


@SuppressWarnings( "serial" )
public class AgentSelectedEvent extends ApplicationEvent {

	public AgentSelectedEvent( Object source ) {

		super( source );
	}

}
