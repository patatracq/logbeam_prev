package logbeam.client.bind.logmessage;

import crudbeam.bind.PropertyBinding;


public class LogMessageMillis extends PropertyBinding< Long > {

	private Long millis;

	@Override
	public void reset() {

		millis = 0L;
	}

	@Override
	public Long getValue() {

		return millis;
	}

	@Override
	public void setValue( Long value ) {
		
		millis = value;
		super.notifyListeners( millis );
	}

	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		if ( value.getValue().getClass().equals( Long.class ) ) {
			setValue( (Long) value.getValue() );
		}
	}
}
