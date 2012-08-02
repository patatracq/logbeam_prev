package logbeam.business.logmessage;

import java.util.Collection;

import crudbeam.business.ContainerInterface;

import logbeam.business.LogMessage;


public interface LogMessageContainer extends ContainerInterface {

	public Collection< LogMessage > getNewerThan( Long milliseconds );
	public void deleteLogMessagesOlderThan( long millis );
}
