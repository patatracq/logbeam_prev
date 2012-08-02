package jonas;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jonas.annotations.JonasCreator;

import org.apache.log4j.Logger;


public class JonasUnmarshaller extends Jonas {

	private static Logger logger = Logger.getLogger( JonasUnmarshaller.class );
	
	private int row;
	private int column;
	private int previousColumn;
	
	private int pos;
	private String jonas;
	
	public Object unmarshal( String jonas ) {
	
		logger.debug( "Unmarshal: " + jonas );
		
		this.row = 1;
		this.column = 1;
		
		this.pos = 0;
		this.jonas = jonas;
		
		return unmarshalElement();
	}
	
	private Object unmarshalElement() {
		
		skipWhitespace();
		Class< ? > elementClass = unmarshalClass();
		skipWhitespace();
		assertNext( VALUE, " after element class." );
		Object element = unmarshalValue( elementClass );
		if ( JonasUnmarshalListener.class.isInstance( element ) ) {
			( (JonasUnmarshalListener) element ).unmarshalDone();
		}
		
		return element;
	}
	
	private Class< ? > unmarshalClass() {
		
		assertNext( CLASS_BEGIN, " before class name." );
		skipWhitespace();
		String className = unmarshalClassName();
		skipWhitespace();
		assertNext( CLASS_END, " after class name." );
		
		Class< ? > clazz = null;
		try {
			clazz = Class.forName( className );
		} catch ( ClassNotFoundException e ) {
			error( "Error unmarshalling class " + className, e );
		}
		
		return clazz;
	}
	
	private Object unmarshalValue( Class< ? > clazz ) {
		
		skipWhitespace();
		if ( checkNext( NULL ) ) {
			assertNext( NULL );
			return null;
		} else if ( clazz.equals( Date.class ) ) {
			return unmarshalDate();
		} else if ( clazz.equals( Timestamp.class ) ) {
			return unmarshalTimestamp();
		} else if ( clazz.equals( String.class ) ) {
			return unmarshalString();
		} else if (	clazz.equals( Boolean.class ) ) {
			return unmarshalBoolean();
		} else if (	clazz.equals( Byte.class ) ) {
			return unmarshalByte();
		} else if (	clazz.equals( Character.class ) ) {
			return unmarshalCharacter();
		} else if (	clazz.equals( Short.class ) ) {
			return unmarshalShort();
		} else if (	clazz.equals( Integer.class ) ) {
			return unmarshalInteger();
		} else if (	clazz.equals( Long.class ) ) {
			return unmarshalLong();
		} else if (	clazz.equals( Float.class ) ) {
			return unmarshalFloat();
		} else if (	clazz.equals( Double.class ) ) {
			return unmarshalDouble();
		} else if ( clazz.equals( List.class ) ) {
			return unmarshalList();
		} else {
			return unmarshalObject( clazz );
		}
	}
	
	private Object unmarshalObject( Class< ? > clazz ) {
		
		skipWhitespace();
		if ( checkNext( LIST_BEGIN ) ) {
			return unmarshalList();
		}
		
		try {
			assertNext( BLOCK_BEGIN, " before object members." );
		} catch ( Exception e ) {
			error( "Perhaps unmarshalling for " + clazz.getName() + " isn't implemented", e );
		}
		
		Object object = null;
		try {
			if ( clazz.getAnnotation( JonasCreator.class ) == null ) {
				object = clazz.newInstance();
			} else {
				Constructor< ? > constructor = clazz.getConstructor( Object.class );
				object = constructor.newInstance( this );
			}
		} catch ( Exception e ) {
			error( "Can't create object of class " + clazz.getName(), e );
		}
		
		unmarshalMembers( object );
		skipWhitespace();
		assertNext( BLOCK_END, " after object members." );
		
		return object;
	}
	
	private void unmarshalMembers( Object object ) {
		
		skipWhitespace();
		
		String name = unmarshalIdentifier();
		while ( name.length() > 0 ) {
			Object value = unmarshalElement();

			StringBuffer setterName = new StringBuffer();
			setterName.append( SET );
			setterName.append( name.substring( 0, 1 ).toUpperCase() );
			if ( name.length() > 1 ) {
				setterName.append( name.substring( 1 ) );
			}
			
			StringBuffer getterName = new StringBuffer();
			getterName.append( GET );
			getterName.append( setterName.substring( 3 ) );
			Method getter = null;
			try {
				getter = object.getClass().getMethod( getterName.toString() );
			} catch ( Exception e ) {
				error( "Could not find setter method " + getterName.toString() + " in " + object.getClass().getName(), e );
			}
			
			Class< ? > memberClass = getter.getReturnType();
			
			Method setter = null;
			try {
				setter = object.getClass().getMethod( setterName.toString(), memberClass );
			} catch ( Exception e ) {
				error( "Could not find setter method " + setterName.toString() + " in " + object.getClass().getName(), e );
			}
			
			try {
				setter.invoke( object, value );
			} catch ( Exception e ) {
				error( "Could not set field " + name + " in " + object.getClass().getName(), e );
			}
			
			skipWhitespace();
			if ( checkNext( LIST ) ) {
				assertNext( LIST, " before next member." );
				skipWhitespace();
				name = unmarshalIdentifier();
			} else {
				name = "";
			}
		}
	}
	
	private List< ? > unmarshalList() {
		
		skipWhitespace();
		assertNext( LIST_BEGIN, " before list." );
		
		List< Object > list = new ArrayList< Object >();
		skipWhitespace();
		// TODO: Possible infinite loop?
		while ( !checkNext( LIST_END ) ) {
			Object object = unmarshalElement();
			list.add( object );
			
			skipWhitespace();
			if ( checkNext( LIST ) ) {
				assertNext( LIST, " between list elements." );
			} else {
				break;
			}
		}
		
		assertNext( LIST_END, " after list." );
		return list;
	}
	
	private Date unmarshalDate() {
		
		skipWhitespace();
		String dateString = unmarshalString();
		Date date = Date.valueOf( dateString );
		
		return date;
	}
	
	private Timestamp unmarshalTimestamp() {
		
		skipWhitespace();
		String timestampString = unmarshalString();
		Timestamp timestamp = Timestamp.valueOf( timestampString );
		
		return timestamp;
	}
	
	private String unmarshalString() {

		StringBuffer string = new StringBuffer(); 
		skipWhitespace();
		assertNext( STRING, " before string constant." );
		Character c = unmarshalCharacter();
		while ( c != STRING ) {
			string.append( c );
			c = unmarshalCharacter();
		}
		
		return string.toString();
	}
	
	private Boolean unmarshalBoolean() {
		
		skipWhitespace();
		String value = unmarshalIdentifier();
		
		return Boolean.parseBoolean( value );
	}
	
	private Byte unmarshalByte() {
		
		skipWhitespace();
		String value = unmarshalIdentifier();
		
		return Byte.parseByte( value );
	}
	
	private Short unmarshalShort() {
		
		skipWhitespace();
		String value = unmarshalIdentifier();
		
		return Short.parseShort( value );
	}
	
	private Integer unmarshalInteger() {
		
		skipWhitespace();
		String value = unmarshalIdentifier();
		
		return Integer.parseInt( value );
	}
	
	private Long unmarshalLong() {
		
		skipWhitespace();
		String value = unmarshalIdentifier();
		
		return Long.parseLong( value );
	}
	
	private Float unmarshalFloat() {
		
		skipWhitespace();
		String value = unmarshalIdentifier();
		
		return Float.parseFloat( value );
	}

	private Double unmarshalDouble() {
		
		skipWhitespace();
		String value = unmarshalIdentifier();
		
		return Double.parseDouble( value );
	}
	private String unmarshalClassName() {
		
		StringBuffer identifier = new StringBuffer();
		Character c = next();
		while (
			Character.isLetterOrDigit( c ) ||
			c == UNDERSCORE ||
			c == DOT ) {
		
			identifier.append( c );
			c = next();
		}
		
		decrement();
		
		return identifier.toString();
	}
	
	private String unmarshalIdentifier() {
		
		StringBuffer identifier = new StringBuffer();
		Character c = next();
		while (
			Character.isLetterOrDigit( c ) ||
			c == UNDERSCORE ) {
		
			identifier.append( c );
			c = next();
		}
		
		decrement();

		return identifier.toString();
	}
	
	private Character unmarshalCharacter() {
		
		Character c = next();
		return c;
	}
	
	private boolean increment() {
		
		previousColumn = column;
		if ( jonas.charAt( pos ) == NEWLINE ) {
			row++;
			column = 1;
		} else {
			column++;
		}
		
		pos++;
		if ( pos >= jonas.length() ) {
			return false;
		}
		
		return true;
	}
	
	private void decrement() {
		
		if ( pos == 0 ) {
			return;
		}
		
		if ( column == 1 ) {
			row--;
			column = previousColumn;
		}
		
		pos--;
	}
	
	private void assertNext( Character c ) {
		
		assertNext( c, "." );
	}
	
	private void assertNext( Character c, String messagePostFix  ) {

		if ( next() != c ) {
			error( "'" + c + "' expected" + messagePostFix );
		}
	}
	
	private boolean checkNext( Character c ) {
		
		boolean result = ( c == next() );
		decrement();
		return result;
	}
	
	private Character next() {
		
		if ( pos >= jonas.length() ) {
			return null;
		}
		
		Character c = jonas.charAt( pos );
		increment();

		return c;
	}
	
	private void skipWhitespace() {
		
		while ( isWhitespace() ) {
			if ( !increment() ) {
				break;
			}
		}
	}
	
	private boolean isWhitespace() {
		
		if ( pos >= jonas.length() ) {
			return false;
		}

		Character c = jonas.charAt( pos );
		return (
				c == NEWLINE ||
				c == CARRIAGE_RETURN ||
				c == TAB ||
				c == SPACE );
	}
	
	private void error( String message ) {
		
		throw new JonasUnmarshalException( row, column, message, jonas );
	}
	
	private void error( String message, Throwable cause ) {
		throw new JonasUnmarshalException( row, column, message, jonas, cause );
	}
}
