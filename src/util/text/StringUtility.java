package util.text;

public abstract class StringUtility {
	
	public static String cutAndTrim( String s, int length ) {
		
		if ( s.length() >= length ) {
			String result = s;
			result = result.substring( 0, length - 3 );
			return result + "...";
		} else {
			return s.trim();
		}
	}

	public static String wordWrap( String s, int suggestedLineLength ) {
		
		StringBuffer t = new StringBuffer();
		for ( int i = 0, j = 0; i < s.length(); i++, j++ ) {
			if ( ( s.charAt( i ) == ' ' || s.charAt( i ) == '\t' ) && j >= suggestedLineLength ) {
				t.append( "\n" );
				j = 0;
			} else {
				t.append( s.charAt( i ) );
			}
		}
		
		return t.toString();
	}
}
