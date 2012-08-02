package jonas;


public class JonasUnmarshalException extends RuntimeException {

	private static final long serialVersionUID = 3651648160597035041L;

	public JonasUnmarshalException( int row, int column, String message, String json ) {
		
		super( "Unmarshal error at (" + Integer.toString( row ) + ", " + Integer.toString( column ) + "): " + message + "\n" + json );
	}

	public JonasUnmarshalException( int row, int column, String message, String json, Throwable cause ) {
		
		super( "Unmarshal error at (" + Integer.toString( row ) + ", " + Integer.toString( column ) + "): " + message + "\n" + json, cause );
	}
}
