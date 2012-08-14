package logbeam.client.action.logfile;

import logbeam.client.bind.agent.AgentLogFiles;
import logbeam.client.bind.logfile.LogFilename;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;


public class AddToLogFileList extends Action {

	private static Logger logger = Logger.getLogger( AddToLogFileList.class );
	
	private LogFilename logFilename;
	private AgentLogFiles agentLogFiles;
	
	@Override
	protected void executeAction( Object source ) {
	
		logger.debug( "Executing action AddToLogFileList" );
		
		agentLogFiles.add( logFilename.getLogFile() );
		
		logger.debug( "Done" );
	}
	
	@Required
	public void setLogFilename( LogFilename logFilename ) {

		this.logFilename = logFilename;
	}
	
	@Required
	public void setAgentLogFiles( AgentLogFiles agentLogFiles ) {

		this.agentLogFiles = agentLogFiles;
	}
}
