package springclient.action;

import crudbeam.action.Action;
import crudbeam.bind.PropertyBinding;


public class CopyBindingValue extends Action {

	private PropertyBinding< ? > from;
	private PropertyBinding< ? > to;
	
	@Override
	protected void executeAction( Object source ) {
		
		to.setValue( from );
	}

	public void setFrom( PropertyBinding< ? > from ) {
		
		this.from = from;
	}
	
	public void setTo( PropertyBinding< ? > to ) {
		
		this.to = to;
	}
}
