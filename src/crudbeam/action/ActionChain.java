package crudbeam.action;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;


public class ActionChain extends Action {

	private List< Action > actions;
	
	@Override
	protected void executeAction( Object source ) {
		
		Iterator< Action > iterator = actions.iterator();
		while ( iterator.hasNext() ) {
			Action action = iterator.next();
			action.execute( source );
		}
	}

	@Required
	public void setActions( List< Action > actions ) {
		
		this.actions = actions;
	}
}
