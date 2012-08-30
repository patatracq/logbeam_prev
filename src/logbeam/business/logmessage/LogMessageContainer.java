package logbeam.business.logmessage;

import java.util.Collection;

import logbeam.business.LogFile;
import logbeam.business.LogMessage;
import crudbeam.business.ContainerInterface;


public interface LogMessageContainer extends ContainerInterface {

	public Collection< LogMessage > getNewerThan( Long milliseconds );
	public void deleteLogMessagesOlderThan( long millis );
	public void deleteLogMessagesByLogFile( LogFile logFile );
}
