package springclient.action;

import springclient.Dialog;
import crudbeam.action.Action;

public class DialogClose extends Action {

	private Dialog dialog;
	private Action action;

	@Override
	protected void executeAction( Object source ) {
		
		dialog.dispose();
		if ( action != null ) {
			action.execute( source );
		}
	}
	
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}

	public void setAction( Action action ) {
		
		this.action = action;
	}
}
