package logbeam.server;

import java.util.Iterator;

import logbeam.business.LogMessage;
import logbeam.business.LogMessageFilter;
import logbeam.business.logmessagefilter.LogMessageFilterContainer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

public class MessageFilter {

	private static Logger logger = Logger.getLogger( MessageFilter.class );
	
	private LogMessageFilterContainer container;

	public Boolean pass( LogMessage message ) {
		
		Iterator< LogMessageFilter > iterator = container.getAll().iterator();
		
		while ( iterator.hasNext() ) {
			
			LogMessageFilter filter = iterator.next();
			if ( filter.matches( message.getLogMessage() ) ) {
				logger.debug( "Message didn't pass filter: " + message.getLogMessage() );
				return false;
			}
		}
		
		return true;
	}
	
	@Required
	public void setContainer( LogMessageFilterContainer container ) {
		
		this.container = container;
	}
}
