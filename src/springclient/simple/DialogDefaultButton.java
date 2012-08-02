package springclient.simple;

import springclient.Dialog;

public class DialogDefaultButton extends Button {

	private Dialog dialog;
	
	public void setDialog( Dialog dialog ) {
		
		this.dialog = dialog;
		this.dialog.getRootPane().setDefaultButton( this.toSwing() );
	}

	public Dialog getDialog() {
		
		return dialog;
	}
}
