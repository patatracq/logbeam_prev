package logbeam.client.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class Connected extends ApplicationEvent {

	public Connected( Object source ) {

		super( source );
	}
}
