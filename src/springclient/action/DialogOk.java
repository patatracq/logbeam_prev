package springclient.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import springclient.Dialog;
import crudbeam.action.Action;

public class DialogOk extends Action {

	private static Logger logger = Logger.getLogger( DialogOk.class );
	
	private Dialog dialog;
	private Action action;

	@Override
	protected void executeAction( Object source ) {
		
		logger.debug( "Executing action DialogOk" );

		dialog.save();
		dialog.dispose();
		if ( action != null ) {
			action.execute( source );
		}

		logger.debug( "Done" );
	}
	
	@Required
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}

	public void setAction( Action action ) {
		
		this.action = action;
	}
}
