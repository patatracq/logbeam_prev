package crudbeam.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;


public class DeleteBindingAction extends Action {

	private static Logger logger = Logger.getLogger( DeleteBindingAction.class );
	
	private PropertyBinding< ? > binding;
	
	@Override
	protected void executeAction( Object source ) {
	
		logger.debug( "Executing DeleteBindingAction for " + binding );
		
		binding.delete();
		
		logger.debug( "Done" );
	}

	@Required
	public void setBinding( PropertyBinding< ? > binding ) {
		
		this.binding = binding;
	}
	
}
