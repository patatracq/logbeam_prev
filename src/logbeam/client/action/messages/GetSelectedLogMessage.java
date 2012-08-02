package logbeam.client.action.messages;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;
import logbeam.client.LogMessagesTable;
import logbeam.client.bind.agent.AgentName;
import logbeam.client.bind.logfile.LogFilename;
import logbeam.client.bind.logmessage.LogMessage;
import springclient.table.Table;


public class GetSelectedLogMessage extends Action {

	private LogMessagesTable logMessagesTable;
	
	private AgentName agentName;
	private LogFilename logFilename;
	private LogMessage logMessage;
	
	@Override
	protected void executeAction( Object source ) {
		
		if ( source instanceof Table ) {
			Table table = (Table) source;
			
			logbeam.business.LogMessage logMessageObject = logMessagesTable.getRow( table.getSelectedRow() );
			if ( logMessageObject != null ) {
				agentName.setAgent( logMessageObject.getLogFile().getAgent() );
				logFilename.setValue( logMessageObject.getLogFile().getFilename() );
				logMessage.setValue( logMessageObject.getLogMessage() );
			}
		}
	}

	@Required
	public void setLogMessagesTable( LogMessagesTable logMessagesTable ) {
		
		this.logMessagesTable = logMessagesTable;
	}
	
	@Required
	public void setAgentName( AgentName agentName ) {

		this.agentName = agentName;
	}
	
	@Required
	public void setLogFilename( LogFilename logFilename ) {

		this.logFilename = logFilename;
	}
	
	@Required
	public void setLogMessage( LogMessage logMessage ) {
	
		this.logMessage = logMessage;
	}
}
