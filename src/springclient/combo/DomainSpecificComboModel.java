package springclient.combo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;


public abstract class DomainSpecificComboModel implements ComboBoxModel {

	private List< ListDataListener > listeners = new ArrayList< ListDataListener >();
	
	@Override
	public abstract Object getElementAt( int index );

	@Override
	public abstract int getSize();

	@Override
	public abstract Object getSelectedItem();

	@Override
	public abstract void setSelectedItem( Object anItem );
	
	@Override
	public void addListDataListener( ListDataListener listener ) {

		listeners.add( listener );
	}

	@Override
	public void removeListDataListener( ListDataListener listener ) {
		
		listeners.remove( listener );
	}
}
