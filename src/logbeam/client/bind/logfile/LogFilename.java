package logbeam.client.bind.logfile;

import crudbeam.bind.PropertyBinding;


public class LogFilename extends PropertyBinding< String > {

	private String logFilename;

	@Override
	public void reset() {

		logFilename = null;
	}

	@Override
	public String getValue() {

		return logFilename;
	}

	@Override
	public void setValue( String value ) {
		
		logFilename = value;
		super.notifyListeners( logFilename );
	}

	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		if ( value.getValue().getClass().equals( String.class ) ) {
			setValue( (String) value.getValue() );
		}
	}
}
