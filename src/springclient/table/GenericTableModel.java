package springclient.table;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


public class GenericTableModel implements TableModel {

	private Column[] columns;
	
	private DomainSpecificTableModel domainTable;
	
	public GenericTableModel( DomainSpecificTableModel domainSpecificTableModel ) {

		this.domainTable = domainSpecificTableModel;
		this.domainTable.setSwingTableModel( this );
	}

	public void setColumns( Column[] columns ) {
		
		this.columns = columns;
	}
	
	public Column[] getColumns() {
		
		return columns;
	}
	
	public Object getValueAt( int row ) {
		
		return domainTable.getValueAt( row );
	}
	
	@Override
	public void addTableModelListener( TableModelListener listener ) {

		domainTable.addTableModelListener( listener );
	}

	@Override
	public void removeTableModelListener( TableModelListener listener ) {

		domainTable.removeTableModelListener( listener );
	}

	@Override
	public Class< ? > getColumnClass( int columnIndex ) {
		
		if ( columnIndex < columns.length ) {
			return columns[ columnIndex ].getType();
		} else {
			return null;
		}
	}

	@Override
	public int getColumnCount() {

		return columns.length;
	}

	@Override
	public String getColumnName( int columnIndex ) {

		if ( columnIndex < columns.length ) {
			return columns[ columnIndex ].getCaption();
		} else {
			return null;
		}
	}

	@Override
	public int getRowCount() {
		
		return domainTable.getRowCount();
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {
		
		return domainTable.getValueAt( rowIndex, columnIndex );
	}

	@Override
	public boolean isCellEditable( int rowIndex, int columnIndex ) {

		return false;
	}

	@Override
	public void setValueAt( Object value, int rowIndex, int columnIndex ) {}
}
