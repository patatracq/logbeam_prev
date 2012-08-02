package springclient;

import org.springframework.context.ApplicationListener;

import crudbeam.action.Action;
import crudbeam.bind.StringBinding;
import crudbeam.event.ErrorEvent;


public class ErrorHandler implements ApplicationListener< ErrorEvent > {

	private Action action;
	private StringBinding binding;
	
	@Override
	public void onApplicationEvent( ErrorEvent event ) {
	
		binding.setValue( event.getMessage() );
		action.execute( this );
	}
	
	public void setAction( Action action ) {
		
		this.action = action;
	}
	
	public void setBinding( StringBinding binding ) {
		
		this.binding = binding;
	}
}
