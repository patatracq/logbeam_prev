package springclient.action;

import org.apache.log4j.Logger;

import crudbeam.action.Action;
import springclient.Dialog;

public class DialogCancel extends Action {

	private static Logger logger = Logger.getLogger( DialogCancel.class );
	
	private Dialog dialog;

	@Override
	protected void executeAction( Object source ) {
		
		logger.debug( "Executing action DialogCancel" );
		dialog.dispose();
		logger.debug( "Done" );
	}
	
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}
}
