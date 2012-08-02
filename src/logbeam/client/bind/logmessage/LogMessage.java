package logbeam.client.bind.logmessage;

import crudbeam.bind.PropertyBinding;


public class LogMessage extends PropertyBinding< String > {

	private String logMessage;

	@Override
	public void reset() {

		logMessage = null;
	}

	@Override
	public String getValue() {

		return logMessage;
	}

	@Override
	public void setValue( String value ) {
		
		logMessage = value;
		super.notifyListeners( logMessage );
	}

	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		if ( value.getValue().getClass().equals( String.class ) ) {
			setValue( (String) value.getValue() );
		}
	}
}
