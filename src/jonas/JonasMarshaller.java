package jonas;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import jonas.annotations.JonasElement;
import jonas.annotations.JonasExcludeIfAncestor;


public class JonasMarshaller extends Jonas {

	private static Logger logger = Logger.getLogger( JonasMarshaller.class );
	
	private boolean formattedOutput = false;
	private Set< Class< ? > > ancestors = new HashSet< Class< ? > >();
	private StringBuffer jonas;
	
	public JonasMarshaller() {}
	
	public JonasMarshaller( boolean formattedOutput ) {
	
		this.formattedOutput = formattedOutput;
	}
	
	public boolean getFormattedOutput() {
		
		return formattedOutput;
	}
	
	public void setFormattedOutput( boolean formattedOutput ) {
		
		this.formattedOutput = formattedOutput;
	}
	
	public String marshal( Object root ) {

		jonas = new StringBuffer();
		marshalClass( root.getClass() );
		marshalFormatStuff( SPACE );
		jonas.append( VALUE );
		marshalFormatStuff( SPACE );
		marshalObject( root, 0 );
		marshalFormatStuff( NEWLINE );
		
		logger.debug( "Marshal: " + jonas.toString() );

		return jonas.toString();
	}
	
	private void marshalClass( Class< ? > clazz ) {
		
		jonas.append( CLASS_BEGIN );
		jonas.append( clazz.getName() );
		jonas.append( CLASS_END );
	}
	
	private void marshalObject( Object object, int indentLevel ) {
		
		jonas.append( BLOCK_BEGIN );
		marshalFormatStuff( NEWLINE );
		ancestors.add( object.getClass() );
		marshalMembers( object, indentLevel + 1 );
		ancestors.remove( object.getClass() );
		marshalFormatStuff( indent( indentLevel + 1 ) );
		jonas.append( BLOCK_END );
	}
	
	private void marshalMembers( Object object, int indentLevel ) {
		
		Method[] methods = object.getClass().getMethods();
		boolean firstMember = true;
		for ( int m = 0; m < methods.length; m++ ) {
			
			Method method = methods[ m ];
			boolean jonasElement = ( method.getAnnotation( JonasElement.class ) != null );
			JonasExcludeIfAncestor excludeIfAncestor = method.getAnnotation( JonasExcludeIfAncestor.class );
			if ( jonasElement ) {
				if ( excludeIfAncestor != null ) {
					Class< ? > ancestorClass = null;
					if ( excludeIfAncestor.ancestorClass() == null ) {
						ancestorClass = method.getReturnType();
					} else {
						ancestorClass = excludeIfAncestor.ancestorClass();
					}

					if ( !isAncestor( ancestorClass ) ) {
						if ( !firstMember ) {
							jonas.append( LIST ); 
							marshalFormatStuff( NEWLINE );
						} else {
							firstMember = false;
						}
						
						marshalMember( object, method, indentLevel + 1 );
					}
				} else {
					if ( !firstMember ) {
						jonas.append( LIST );
						marshalFormatStuff( NEWLINE );
					} else {
						firstMember = false;
					}
					
					marshalMember( object, method, indentLevel + 1 );
				}
			}
		}
		
		marshalFormatStuff( NEWLINE );
	}
	
	private void marshalMember( Object object, Method getterMethod, int indentLevel ) {

		/*
		 * Validate getter signature.
		 */
		if ( !getterMethod.getName().substring( 0, 3 ).equals( GET ) ) {
			throw new JonasMarshalException( "Illegal getter for JsonElement: " + getterMethod.getName() + ". Getter name must begin with 'get'." );
		}
		
		if ( getterMethod.getParameterTypes().length != 0 ) {
			throw new JonasMarshalException( "Illegal getter for JsonElement: " + getterMethod.getName() + ". Getters must not have any parameters." );
		}
		
		/*
		 * Member name.
		 */
		StringBuffer name = new StringBuffer();
		if ( getterMethod.getName().length() > 3 ) {
			name.append( getterMethod.getName().substring( 3, 4 ).toLowerCase() );
			if ( getterMethod.getName().length() > 4 ) {
				name.append( getterMethod.getName().substring( 4 ) );
			}
		}
		
		/*
		 * Member value.
		 */
		Object value = null;
		try {
			value = getterMethod.invoke( object );
		} catch ( Exception e ) {
			throw new JonasMarshalException( "Error invoking getter method " + getterMethod.getName() + " on " + object.getClass().getName(), e );
		}
		
		/*
		 * Member class
		 */
		Class< ? > clazz = null;
		if ( value != null ) {
			clazz = value.getClass();
		} else {
			clazz = getterMethod.getReturnType();
		}
		
		marshalFormatStuff( indent( indentLevel ) );
		marshalIdentifier( name.toString() );
		marshalFormatStuff( SPACE );
		marshalElement( clazz, value, indentLevel );
	}
	
	private void marshalList( List< ? > list, int indentLevel ) {
		
		jonas.append( LIST_BEGIN );
		marshalFormatStuff( NEWLINE );
		
		Iterator< ? > iterator = list.iterator();
		while( iterator.hasNext() ) {
			
			marshalFormatStuff( indent( indentLevel + 1 ) );
			Object value = iterator.next();
			marshalElement( value.getClass(), value, indentLevel );
			
			if ( iterator.hasNext() ) {
				jonas.append( LIST );
			}
			
			marshalFormatStuff( NEWLINE );
		}
		
		marshalFormatStuff( indent( indentLevel ) );
		jonas.append( LIST_END );
	}
	
	private void marshalElement( Class< ? > clazz, Object value, int indentLevel ) {
		
		marshalClass( clazz );
		marshalFormatStuff( SPACE );
		jonas.append( VALUE );
		marshalFormatStuff( SPACE );
		marshalValue( value, indentLevel );
	}
	
	private void marshalValue( Object value, int indentLevel ) {
		
		if ( value == null ) {
			jonas.append( NULL );
		} else if ( value.getClass().equals( String.class ) ) {
			marshalString( (String) value );
		} else if ( value.getClass().equals( Boolean.class ) ) {
			marshalBoolean( (Boolean) value );
		} else if (
				value.getClass().equals( Byte.class ) ||
				value.getClass().equals( Character.class ) ||
				value.getClass().equals( Integer.class ) ||
				value.getClass().equals( Long.class ) ||
				value.getClass().equals( Short.class ) ) {
			marshalSimpleType( value );
		} else if ( value.getClass().equals( Timestamp.class ) ) {
			marshalTimestamp( (Timestamp) value );
		} else if ( value.getClass().equals( Date.class ) ) {
			marshalDate( (Date) value );
		} else if ( value instanceof List ) {
			marshalList( (List< ? >) value, indentLevel );
		} else {
			marshalObject( value, indentLevel );
		}
	}
	
	private void marshalString( String s ) {
		
		jonas.append( STRING );
		jonas.append( s );
		jonas.append( STRING );
	}

	private void marshalBoolean( Boolean b ) {
		
		jonas.append( b.toString() );
	}
	
	private void marshalSimpleType( Object value ) {
		
		jonas.append( value.getClass().cast( value ) );
	}
	
	private void marshalTimestamp( Timestamp timestamp ) {
		
		marshalString( timestamp.toString() );
	}
	
	private void marshalDate( Date date ) {
		
		marshalString( date.toString() );
	}
	
	private void marshalIdentifier( String identifer ) {
		
		jonas.append( identifer );
	}

	private void marshalFormatStuff( Character c ) {
		
		if ( formattedOutput ) {
			jonas.append( c );
		}
	}
	
	private void marshalFormatStuff( String s ) {
		
		if ( formattedOutput ) {
			jonas.append( s );
		}
	}
	
	private boolean isAncestor( Class< ? > clazz ) {
		
		return ancestors.contains( clazz );
	}
	
	private String indent( int indentLevel ) {
		
		StringBuffer indentation = new StringBuffer();
		for ( int i = 0; i < indentLevel; i++ ) {
			
			indentation.append( INDENTATION );
		}
		
		return indentation.toString();
	}
}
