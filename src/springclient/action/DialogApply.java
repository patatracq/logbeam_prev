package springclient.action;

import org.apache.log4j.Logger;

import crudbeam.action.Action;
import springclient.Dialog;

public class DialogApply extends Action {

	private static Logger logger = Logger.getLogger( DialogApply.class );
	
	private Dialog dialog;
	private Action action;
	
	@Override
	public void executeAction( Object source ) {
	
		logger.debug( "Executing action DialogApply" );
		
		dialog.save();
		if ( action != null ) {
			action.execute( source );
		}
		
		logger.debug( "Done" );
	}

	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}

	public Dialog getDialog() {
		
		return dialog;
	}

	public void setAction( Action action ) {
		
		this.action = action;
	}

	public Action getAction() {
		
		return action;
	}
}
