package logbeam.client.action.messages;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;
import logbeam.client.LogMessagesTable;


public class ClearClientCache extends Action {

	private LogMessagesTable logMessagesTable;
	
	@Override
	protected void executeAction( Object source ) {

		logMessagesTable.clear();
	}

	@Required
	public void setLogMessagesTable( LogMessagesTable logMessagesTable ) {
		
		this.logMessagesTable = logMessagesTable;
	}
}
