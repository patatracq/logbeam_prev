package springclient.action;

import org.apache.log4j.Logger;

import crudbeam.action.Action;


public class Exit extends Action {

	private static Logger logger = Logger.getLogger( Exit.class );

	@Override
	protected void executeAction( Object source ) {

		logger.debug( "Executing action Exit" );
		System.exit( 0 );
	}
}
