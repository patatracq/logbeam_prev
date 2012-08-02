package jonas;


public class JonasMarshalException extends RuntimeException {

	private static final long serialVersionUID = -3807496059731704917L;

	public JonasMarshalException( String message ) {
		
		super( "Marshal error: " + message );
	}

	public JonasMarshalException( String message, Throwable cause ) {
		
		super( "Marshal error: " + message, cause );
	}
}
