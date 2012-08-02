package logbeam.client.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class Disconnected extends ApplicationEvent {

	public Disconnected( Object source ) {

		super( source );
	}
}
