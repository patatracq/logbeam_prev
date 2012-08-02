package springclient.list;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicBorders;

import springclient.DefaultSwingComponent;
import crudbeam.action.Action;


public class List extends DefaultSwingComponent< List, JList > implements MouseListener {

	private GenericListModel listModel;
	private Action doubleClickAction;
	
	public List( Dimension preferredSize, GenericListModel listData ) {
		
		super( new JList() );

		toSwing().setPreferredSize( preferredSize );
		toSwing().setBorder( BasicBorders.getTextFieldBorder() );
		listModel = listData;
		
		toSwing().setModel( listModel );
		toSwing().addMouseListener( this );
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
	
	public int getSelectedRow() {
		
		return toSwing().getSelectedIndex();
	}
	
	public void setDoubleClickAction( Action doubleClickAction ) {
		
		this.doubleClickAction = doubleClickAction;
	}

	@Override
	public void mouseEntered( MouseEvent arg0 ) {
		
		// Nothing
	}

	@Override
	public void mouseExited( MouseEvent arg0 ) {
		
		// Nothing
	}

	@Override
	public void mousePressed( MouseEvent arg0 ) {
		
		// Nothing
	}

	@Override
	public void mouseReleased( MouseEvent arg0 ) {
		
		// Nothing
	}
}
