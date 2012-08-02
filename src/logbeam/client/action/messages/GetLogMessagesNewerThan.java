package logbeam.client.action.messages;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;

import logbeam.business.LogMessage;
import logbeam.business.logmessage.LogMessageContainer;
import logbeam.client.LogMessagesTable;
import logbeam.client.bind.logmessage.LogMessageMillis;

public final class GetLogMessagesNewerThan extends Action {

	private LogMessageMillis binding;
	private LogMessageContainer logMessageContainer;
	private LogMessagesTable logMessagesTable;

	@Override
	protected void executeAction( Object source ) {

		logMessagesTable.show( new ArrayList< LogMessage >( logMessageContainer.getNewerThan( binding.getValue() ) ) );
	}
	
	@Required
	public void setBinding( LogMessageMillis binding ) {
		
		this.binding = binding;
	}
	
	@Required
	public void setLogMessageContainer( LogMessageContainer dao ) {

		this.logMessageContainer = dao;
	}
	
	@Required
	public void setLogMessagesTable( LogMessagesTable table ) {

		this.logMessagesTable = table;
	}
}
