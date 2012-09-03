package springclient.action;

import org.springframework.beans.factory.annotation.Required;

import springclient.Dialog;
import crudbeam.action.Action;

public class DialogOk extends Action {

	private Dialog dialog;
	private Action action;

	@Override
	protected void executeAction( Object source ) {
		
		dialog.save();
		dialog.dispose();
		if ( action != null ) {
			action.execute( source );
		}
	}
	
	@Required
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
	}

	public void setAction( Action action ) {
		
		this.action = action;
	}
}
