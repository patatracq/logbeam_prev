package logbeam.client.action.messages;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;
import logbeam.client.LogMessagesTable;
import springclient.menu.CheckBoxMenuItem;


public class PauseMessages extends Action {

	private LogMessagesTable logMessagesTable;
	
	@Override
	protected void executeAction( Object source ) {
		
		if ( source instanceof CheckBoxMenuItem ) {
			
			CheckBoxMenuItem checkBox = (CheckBoxMenuItem) source;
			if ( checkBox.isSelected() ) {
				logMessagesTable.pause();
			} else {
				logMessagesTable.resume();
			}
		}
	}

	@Required
	public void setLogMessagesTable( LogMessagesTable logMessagesTable ) {
		
		this.logMessagesTable = logMessagesTable;
	}
}
