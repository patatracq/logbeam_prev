package logbeam.server;

import java.util.Calendar;

import logbeam.business.logmessage.LogMessageContainer;

import org.apache.log4j.Logger;

import util.date.DateUtility;

public class MessageCleaner implements Runnable {

	private static Logger logger = Logger.getLogger( MessageCleaner.class );
	
	private Short hoursToLive;
	private LogMessageContainer logMessageContainer;
	
	public void run() {
		
		while ( true ) {
			logger.info( "Deleting old log messages from database" );
			
			Calendar limit = Calendar.getInstance();
			limit = DateUtility.rollbackHour( hoursToLive, limit );
		
			clean( limit.getTimeInMillis() );
	
			Calendar nextExecution = Calendar.getInstance();
			nextExecution = DateUtility.rollforwardHour( hoursToLive, nextExecution );
			
			logger.info( "Done" );
			
			boolean executeAgain = false;
			while ( !executeAgain ) {
				sleep( 60000L );
				
				Calendar now = Calendar.getInstance();
				if ( now.after( executeAgain ) ) {
					executeAgain = true;
				}
			}
		}
	}
	
	public void clean( Long millis ) {

		logMessageContainer.deleteLogMessagesOlderThan( millis );
	}
	
	public void setHoursToLive( Short hoursToLive ) {
		
		this.hoursToLive = hoursToLive;
	}
	
	public void setLogMessageContainer( LogMessageContainer logMessageDao ) {
		
		this.logMessageContainer = logMessageDao;
	}
	
	private void sleep( long millis ) {

		try {
			Thread.sleep( millis );
		} catch ( InterruptedException ie ) {
			logger.warn( ie );
		}
	}
}
