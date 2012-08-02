
package logbeam.business;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import jonas.annotations.JonasElement;
import crudbeam.business.BusinessPojo;

@Entity
public class LogMessage extends BusinessPojo {
	
	public static String LOG_MESSAGE = "logMessage";
	
	private static int maxLength = 1024;
	
	private LogFile logFile;
	private String logMessage;
	private Timestamp timestamp;

	@ManyToOne
	@JonasElement
	public LogFile getLogFile() {
		
		return logFile;
	}

	public void setLogFile( LogFile logFile ) {
		
		this.logFile = logFile;
	}

	@Column( nullable = false, length = 1024 )
	@JonasElement
	public String getLogMessage() {
		
		return logMessage;
	}

	public void setLogMessage( String logMessage ) {
		
		if ( logMessage.length() >= maxLength ) {
			
			this.logMessage = logMessage.substring( 0, maxLength - 4 ) + "...";
		} else {
			this.logMessage = logMessage;
		}
		
		this.logMessage = this.logMessage.replace( '"', '\'' );
	}

	@Basic( optional = false )
	@JonasElement
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp( Timestamp timestamp ) {
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals( Object object ) {
		
		if ( object == null ) {
			return false;
		}
		
		boolean result = object instanceof LogMessage;
		
		if ( logFile != null ) {
			result = result && logFile.equals( ( (LogMessage) object ).logFile );
		}
		
		if ( logMessage != null ) {
			result = result && logMessage.equals( ( (LogMessage) object ).logMessage );
		}
		
		if ( timestamp != null ) {
			result = result && timestamp.equals( ( (LogMessage) object ).timestamp );
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		
		int result = 0;
		
		if ( logFile != null ) {
			result += logFile.hashCode();
		}
		
		if ( logMessage != null ) {
			result += logMessage.hashCode();
		}
		
		if ( timestamp != null ) {
			result += timestamp.hashCode();
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		
		return logMessage;
	}
}
