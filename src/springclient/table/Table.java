package springclient.table;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import crudbeam.action.Action;

import springclient.DefaultMouseListener;
import springclient.DefaultSwingComponent;




public class Table extends DefaultSwingComponent< Table, JTable > implements MouseListener {
	
	private GenericTableModel tableModel;
	
	private Action doubleClickAction;
	
	public Table( GenericTableModel tableData ) {
		
		super( new JTable() );
		
		this.tableModel = tableData;
		
		final JTable swingTable = toSwing();
		swingTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		swingTable.setModel( tableModel );
		swingTable.addMouseListener( this );
		
		TableColumnModel columnModel = toSwing().getColumnModel();
		for ( int i = 0; i < tableModel.getColumns().length; i++ ) {
			columnModel.getColumn( i ).setPreferredWidth( tableModel.getColumns()[ i ].getWidth() );
		}
		
		toSwing().getTableHeader().addMouseListener( new DefaultMouseListener() {
			
			@Override
			public void mouseClicked( MouseEvent event ) {
				if ( 
						event.getSource() == swingTable.getTableHeader() &&
						event.getClickCount() >= 2 && 
						swingTable.getTableHeader().getCursor().getType() == Cursor.E_RESIZE_CURSOR ) {
					
					resizeColumn( event );
				}
			}
		} );
	}

	public int getSelectedRow() {
		
		return toSwing().getSelectedRow();
	}
	
	public GenericTableModel getModel() {
		
		return tableModel;
	}
	
	@Override
	public void mouseClicked( MouseEvent event ) {

		if ( doubleClickAction != null && event.getSource() == toSwing() && event.getClickCount() >= 2 ) {
			int row = getSelectedRow();
			if ( row > -1 ) {
				doubleClickAction.execute( this );
			}
		}
	}

	public void setDoubleClickAction( Action doubleClickAction ) {
		
		this.doubleClickAction = doubleClickAction;
	}
	
	private void resizeColumn( int columnIndex ) {
		
		JTable table = super.toSwing();
		JTableHeader tableHeader = table.getTableHeader();
		TableColumn column = tableHeader.getColumnModel().getColumn( columnIndex );
		
		/*
		 * Determine width.
		 */
		int width = 0;
		
		/*
		 * Header width.
		 */
		TableCellRenderer headerRenderer = column.getHeaderRenderer();
		if ( headerRenderer == null ) {
			headerRenderer = tableHeader.getDefaultRenderer();
		}
		
		Component headerComponent = headerRenderer.getTableCellRendererComponent( table, column.getHeaderValue(), false, false, 0, columnIndex );
		width = Math.max( width, headerComponent.getPreferredSize().width );
		
		/*
		 * Cell width.
		 */
		Rectangle visibleRectangle = table.getVisibleRect();
		int firstRow = table.rowAtPoint( new Point( (int) visibleRectangle.getMinX(), (int) visibleRectangle.getMinY() ) );
		if ( firstRow > -1 ) {
			int lastRow = table.rowAtPoint( new Point( (int) visibleRectangle.getMaxX(), (int) visibleRectangle.getMaxY() ) );
			
			if ( lastRow == -1 ) {
				lastRow = tableModel.getRowCount();
			}
			
			for ( int row = firstRow; row < lastRow; row++ ) {
				TableCellRenderer renderer = table.getCellRenderer( row, columnIndex );
				Component cellComponent = renderer.getTableCellRendererComponent( table, table.getValueAt( row, columnIndex ), false, false, row, columnIndex );
				width = Math.max( width, cellComponent.getPreferredSize().width );
			}
		}
		
		/*
		 * Add margins.
		 */
		width += table.getColumnModel().getColumnMargin();
		
		column.setPreferredWidth( width );
	}
	
	private void resizeColumn( MouseEvent event ) {
		
		JTable table = toSwing(); 
		JTableHeader tableHeader = table.getTableHeader();
		Point point = event.getPoint();
		point.x = point.x - 5;
		int columnIndex = tableHeader.columnAtPoint( point );
		resizeColumn( columnIndex );
	}

	@Override
	public void mouseEntered( MouseEvent event ) {
		
		// Nothing
	}

	@Override
	public void mouseExited( MouseEvent event ) {
		
		// Nothing
	}

	@Override
	public void mousePressed( MouseEvent event ) {
		
		// Nothing
	}

	@Override
	public void mouseReleased( MouseEvent event ) {
		
		// Nothing
	}
}
