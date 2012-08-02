package crudbeam.event;

import org.springframework.context.ApplicationEvent;


public interface EventFactory {

	public ApplicationEvent createEvent( Object source );
}
