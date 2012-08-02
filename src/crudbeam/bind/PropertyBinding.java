package crudbeam.bind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class PropertyBinding< T > {

	private List< PropertyChangedListener< T > > listeners = new ArrayList< PropertyChangedListener< T > >();
	
	public abstract void reset();
	
	public abstract T getValue();
	public abstract void setValue( T value );
	public abstract void setValue( PropertyBinding< ? > value );
	
	public void save() {}
	
	public void delete() {}
	
	public void setValue( PropertyChangedListener< T > source, T value ) {
		
		listeners.remove( source );
		setValue( value );
		listeners.add( source );
	}
	
	public void addPropertyChangedListener( PropertyChangedListener< T > listener ) {
		
		listeners.add( listener );
	}
	
	public void deletePropertyChangedListener( PropertyChangedListener< T > listener ) {
		
		listeners.remove( listener );
	}
	
	protected void notifyListeners( T newValue ) {
		
		Iterator< PropertyChangedListener< T > > iterator = listeners.iterator();
		while ( iterator.hasNext() ) {
			iterator.next().propertyChanged( newValue );
		}
	}
	
}
