package logbeam.business.logmessage;

import java.util.Collection;
import java.util.Iterator;

import logbeam.business.LogFile;
import logbeam.business.LogMessage;
import crudbeam.cyberspace.CyberspaceContainer;
import crudbeam.cyberspace.CyberspaceRequest;


public class LogMessageCyberspaceContainer extends CyberspaceContainer< LogMessage > implements
		LogMessageContainer {

	@Override
	public Collection< LogMessage > getNewerThan( Long milliseconds ) {

		getObjects().clear();

		@SuppressWarnings( "unchecked" )
		Collection< LogMessage > messages =
				(Collection< LogMessage >) getClient().callServer(
						new CyberspaceRequest(
								"logMessageDatabaseContainer",
								"getNewerThan",
								CyberspaceRequest.parameters( new Object[] { milliseconds } ) ) ).getReply();
		
		Iterator< LogMessage > iterator = messages.iterator();
		while ( iterator.hasNext() ) {
			LogMessage logMessage = iterator.next();
			getObjects().put( logMessage.getId(), logMessage );
		}
		
		return getObjects().values();
	}

	@Override
	public void deleteLogMessagesOlderThan( long millis ) {

		throw new RuntimeException( "deleteLogMessagesOlderThan not available in cyberspace" );
	}
	
	@Override
	public void deleteLogMessagesByLogFile( LogFile logFile ) {

		throw new RuntimeException( "deleteLogMessagesByLogFile not available in cyberspace" );
	}

	@Override
	protected LogMessage newObject() {

		return new LogMessage();
	}
}
