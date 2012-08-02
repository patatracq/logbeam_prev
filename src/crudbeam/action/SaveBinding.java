package crudbeam.action;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;


public class SaveBinding extends Action {

	private PropertyBinding< ? > binding;
	
	@Override
	protected void executeAction( Object source ) {

		binding.save();
	}

	@Required
	public void setBinding( PropertyBinding< ? > binding ) {
		
		this.binding = binding;
	}
}
