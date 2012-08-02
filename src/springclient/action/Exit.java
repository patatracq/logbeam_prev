package springclient.action;

import crudbeam.action.Action;


public class Exit extends Action {

	@Override
	protected void executeAction( Object source ) {

		System.exit( 0 );
	}
}
