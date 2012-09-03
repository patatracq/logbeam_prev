package springclient.action;

import crudbeam.action.Action;
import springclient.Dialog;

public class DialogApply extends Action {

	private Dialog dialog;
	private Action action;
	
	@Override
	public void executeAction( Object source ) {
	
		dialog.save();
		if ( action != null ) {
			action.execute( source );
		}
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
