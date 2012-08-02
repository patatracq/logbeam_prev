package logbeam.client.event;

import org.springframework.context.ApplicationEvent;


@SuppressWarnings( "serial" )
public class RegexUnfocusEvent extends ApplicationEvent {

	public RegexUnfocusEvent( Object source ) {
		
		super( source );
	}

}
