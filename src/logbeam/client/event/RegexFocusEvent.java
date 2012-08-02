package logbeam.client.event;

import org.springframework.context.ApplicationEvent;


@SuppressWarnings( "serial" )
public class RegexFocusEvent extends ApplicationEvent {

	public RegexFocusEvent( Object source ) {
		
		super( source );
	}

}
