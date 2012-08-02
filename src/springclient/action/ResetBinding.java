package springclient.action;

import crudbeam.action.Action;
import crudbeam.bind.PropertyBinding;


public class ResetBinding extends Action {

	private PropertyBinding< ? > binding;
	
	@Override
	protected void executeAction( Object source ) {
		
		binding.reset();
	}

	public void setBinding( PropertyBinding< ? > binding ) {
		
		this.binding = binding;
	}
}
