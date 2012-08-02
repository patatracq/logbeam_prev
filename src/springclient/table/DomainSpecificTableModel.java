package springclient.table;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public abstract class DomainSpecificTableModel {

	private TableModel swingTableModel;
	private Vector< TableModelListener > listeners = new Vector< TableModelListener >();

	public void addTableModelListener( TableModelListener listener ) {
		
		listeners.add( listener );
	}
	
	public void removeTableModelListener( TableModelListener listener ) {
		
		listeners.remove( listener );
	}

	public abstract int getRowCount();
	public abstract Object getValueAt( int rowIndex );
	public abstract Object getValueAt( int rowIndex, int columnIndex );

	public void setSwingTableModel( TableModel swingTableModel ) {
		
		this.swingTableModel = swingTableModel;
	}
	
	protected void tableRowsChanged() {
		
		Iterator< TableModelListener > iterator = listeners.iterator();
		while ( iterator.hasNext() ) {
			iterator.next().tableChanged( new TableModelEvent( swingTableModel ) );
		}
	}
}
