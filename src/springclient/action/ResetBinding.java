package springclient.action;

import org.apache.log4j.Logger;

import crudbeam.action.Action;
import crudbeam.bind.PropertyBinding;


public class ResetBinding extends Action {

	private static Logger logger = Logger.getLogger( ResetBinding.class );
	
	private PropertyBinding< ? > binding;
	
	@Override
	protected void executeAction( Object source ) {
		
		logger.debug( "Executing action ResetBinding for " + binding );
		
		binding.reset();
		
		logger.debug( "Done" );
	}

	public void setBinding( PropertyBinding< ? > binding ) {
		
		this.binding = binding;
	}
}
