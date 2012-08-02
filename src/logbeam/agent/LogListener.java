/*
 * This file is part of log-beam.
 * 
 * log-beam is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * log-beam is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with log-beam.  If not, see <http://www.gnu.org/licenses/>.
 */

package logbeam.agent;

import java.sql.Timestamp;

import logbeam.business.LogFile;
import logbeam.business.LogMessage;
import logbeam.business.logfile.LogFileContainer;
import logbeam.business.logmessage.LogMessageContainer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import util.tail.TailListener;

/**
 * Listens for events from a tailer.
 * @author Jonas Stråle
 *
 */
public class LogListener implements TailListener {

	private static Logger logger = Logger.getLogger( LogListener.class );
	
	private LogFile logFile;
	private LogFileContainer logFileContainer;
	private LogMessageContainer logMessageContainer;

	@Override
	public void exception( Exception e ) {

		logger.debug( "Exception from tailer: " + e.getMessage() );
		
		logFile.setStatus( false );
		logFile.setStatusInformation( e.getMessage() );
		
		try {
			logFileContainer.save( logFile );
		} catch ( Exception e2 ) {
			logger.error( "Can't save error status for log file '" + logFile.getFilename() + "'", e );
		}
	}

	@Override
	public void newLine( String line ) {
		
		logger.debug( "New line: " + line );
		if ( line.length() > 0 ) {
			LogMessage logMessage = new LogMessage();
			logMessage.setTimestamp( new Timestamp( System.currentTimeMillis() ) );
			logMessage.setLogFile( logFile );
			logMessage.setLogMessage( line.trim() );
	
			try {
				logMessageContainer.save( logMessage );
				ok();
			} catch ( Exception e ) {
				exception( e );
			}
		}
	}
	
	public LogFile getLogFile() {
		
		return logFile;
	}

	public void setLogFile( LogFile logFile ) {
		
		this.logFile = logFile;
	}
	
	@Required
	public void setLogFileContainer( LogFileContainer newLogFileStatus ) {
		
		this.logFileContainer = newLogFileStatus;
	}
	
	@Required
	public void setLogMessageContainer( LogMessageContainer messagePublisher ) {
		
		this.logMessageContainer = messagePublisher;
	}
	
	private void ok() {
		
		if ( !logFile.getStatus() ) {
			logFile.setStatus( true );
			logFileContainer.save( logFile );
		}
	}
}
