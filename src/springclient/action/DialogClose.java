package springclient.action;

import org.apache.log4j.Logger;

import crudbeam.action.Action;
import springclient.Dialog;

public class DialogClose extends Action {

	private static Logger logger = Logger.getLogger( DialogClose.class );

	private Dialog dialog;
	private Action action;

	@Override
	protected void executeAction( Object source ) {
		
		logger.debug( "Executing action DialogClose" );

		dialog.dispose();
		if ( action != null ) {
			action.execute( source );
		}

		logger.debug( "Done" );
	}
	
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}

	public void setAction( Action action ) {
		
		this.action = action;
	}
}
