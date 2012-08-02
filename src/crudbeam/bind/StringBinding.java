package crudbeam.bind;


public class StringBinding extends PropertyBinding< String > {

	private String string;
	
	@Override
	public void reset() {

		string = null;
		notifyListeners( string );
	}

	@Override
	public String getValue() {

		return string;
	}

	@Override
	public void setValue( String value ) {

		string = value;
		notifyListeners( string );
	}
	
	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		if ( value.getValue().getClass().equals( String.class ) ) {
			setValue( (String) value.getValue() );
		}
	}
}
