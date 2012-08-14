package logbeam.client.bind.logfile;

import logbeam.business.LogFile;
import logbeam.business.logfile.LogFileContainer;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;


public class LogFilename extends PropertyBinding< String > {

	private LogFile logFile;
	private LogFileContainer container;

	@Override
	public void reset() {

		logFile = null;
		super.notifyListeners( getValue() );
	}

	@Override
	public String getValue() {

		if ( logFile != null ) {
			return logFile.getFilename();
		} else {
			return "";
		}
	}
	
	public LogFile getLogFile() {
		
		return logFile;
	}

	@Override
	public void setValue( String value ) {
		
		if ( logFile == null ) {
			logFile = (LogFile) container.get( 0L );
		}
		
		logFile.setFilename( value );
		super.notifyListeners( logFile.getFilename() );
	}

	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		if ( value != null && value.getValue() != null && value.getValue().getClass().equals( String.class ) ) {
			setValue( (String) value.getValue() );
		}
	}
	
	@Required
	public void setContainer( LogFileContainer container ) {
		
		this.container = container;
	}
}
