package springclient.list;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


public abstract class DomainSpecificListModel implements ListModel {

	public java.util.List< ListDataListener > listeners = new ArrayList< ListDataListener >();

	@Override
	public abstract Object getElementAt( int index );

	@Override
	public abstract int getSize();

	@Override
	public void addListDataListener( ListDataListener listener ) {
		
		listeners.add( listener );
	}

	@Override
	public void removeListDataListener( ListDataListener listener ) {

		listeners.remove( listener );
	}
	
	protected void itemsChanged( int firstIndex, int lastIndex ) {
		
		Iterator< ListDataListener > iterator = listeners.iterator();
		while ( iterator.hasNext() ) {
			iterator.next().contentsChanged( new ListDataEvent( this, ListDataEvent.CONTENTS_CHANGED, firstIndex, lastIndex ) );
		}
	}
	
	protected void itemsDeleted( int firstIndex, int lastIndex ) {
		
		Iterator< ListDataListener > iterator = listeners.iterator();
		while ( iterator.hasNext() ) {
			iterator.next().intervalRemoved( new ListDataEvent( this, ListDataEvent.INTERVAL_REMOVED, firstIndex, lastIndex ) );
		}
	}
	
	protected void itemsInserted( int firstIndex, int lastIndex ) {
		
		Iterator< ListDataListener > iterator = listeners.iterator();
		while ( iterator.hasNext() ) {
			iterator.next().intervalAdded( new ListDataEvent( this, ListDataEvent.INTERVAL_ADDED, firstIndex, lastIndex ) );
		}
	}
}
