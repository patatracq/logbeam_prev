package springclient.action;

import org.apache.log4j.Logger;

import crudbeam.action.Action;
import springclient.Dialog;

public class OpenDialog extends Action {

	private static Logger logger = Logger.getLogger( OpenDialog.class );

	private Dialog dialog;

	@Override
	protected void executeAction( Object source ) {

		logger.debug( "Executing action OpenDialog" );

		dialog.pack();
		dialog.setVisible( true );

		logger.debug( "Done" );
	}
	
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}
}
