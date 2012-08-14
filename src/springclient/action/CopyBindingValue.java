package springclient.action;

import org.apache.log4j.Logger;

import crudbeam.action.Action;
import crudbeam.bind.PropertyBinding;


public class CopyBindingValue extends Action {

	private static Logger logger = Logger.getLogger( CopyBindingValue.class );
	
	private PropertyBinding< ? > from;
	private PropertyBinding< ? > to;
	
	@Override
	protected void executeAction( Object source ) {
		
		logger.debug( "Executing action CopyBindingValue" );
		to.setValue( from );
		logger.debug( "Done" );
	}

	public void setFrom( PropertyBinding< ? > from ) {
		
		this.from = from;
	}
	
	public void setTo( PropertyBinding< ? > to ) {
		
		this.to = to;
	}
}
