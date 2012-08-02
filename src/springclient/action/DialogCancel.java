package springclient.action;

import crudbeam.action.Action;
import springclient.Dialog;

public class DialogCancel extends Action {

	private Dialog dialog;

	@Override
	protected void executeAction( Object source ) {
		
		dialog.dispose();
	}
	
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}
}
