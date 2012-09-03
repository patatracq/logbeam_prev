package crudbeam.action;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;


public class DeleteBindingAction extends Action {

	private PropertyBinding< ? > binding;
	
	@Override
	protected void executeAction( Object source ) {
	
		binding.delete();
	}

	@Required
	public void setBinding( PropertyBinding< ? > binding ) {
		
		this.binding = binding;
	}
	
}
