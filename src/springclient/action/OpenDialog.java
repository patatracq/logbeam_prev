package springclient.action;

import springclient.Dialog;
import crudbeam.action.Action;

public class OpenDialog extends Action {

	private Dialog dialog;

	@Override
	protected void executeAction( Object source ) {

		dialog.pack();
		dialog.setVisible( true );
	}
	
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}
}
