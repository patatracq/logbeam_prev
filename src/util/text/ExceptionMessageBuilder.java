
package util.text;

public abstract class ExceptionMessageBuilder {

	public static String getMessage( Throwable t ) {
		
		StringBuffer s = new StringBuffer( t.getClass().getName() + ": " + t.getMessage() );
		Throwable cause = t.getCause();
		while ( cause != null ) {
			s.append( "\n  caused by:" + cause.getClass().getName() + ": " + cause.getMessage() );
			cause = cause.getCause();
		}
		
		return s.toString();
	}
	
	public static String getStackTrace( Throwable t ) {
		
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StringBuffer stackTraceString = new StringBuffer();
		for ( int i = 0; i < stackTrace.length; i++ ) {
			stackTraceString.append( stackTrace[ i ].getClassName() + ":" + stackTrace[ i ].getLineNumber() );
			if ( i < stackTrace.length - 1 ) {
				stackTraceString.append( ", " );
			}
		}
		
		return stackTraceString.toString();
	}
}
