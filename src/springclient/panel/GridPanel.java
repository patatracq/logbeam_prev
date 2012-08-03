package springclient.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import springclient.BoundComponent;
import springclient.SwingComponent;




public class GridPanel extends DefaultPanel< GridPanel > implements Panel< GridPanel, JPanel > {

	public static final int LEFT = GridBagConstraints.WEST;
	public static final int CENTER = GridBagConstraints.CENTER;
	public static final int RIGHT = GridBagConstraints.EAST;
	
	private int space = PanelDefaults.SPACE;
	private List< GridPanelItem > items;
	
	public GridPanel() {

		super( new JPanel() );
		
		toSwing().setLayout( new GridBagLayout() );
	}
	
	@Override
	public void save() {
		
		Iterator< GridPanelItem > iterator = this.items.iterator();
		while ( iterator.hasNext() ) {
			SwingComponent< ?, ? > component = iterator.next().getComponent();
			if ( BoundComponent.class.isInstance( component ) ) {
				( (BoundComponent) component ).save();
			}
		}
	}
	
	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public void setItems( List< GridPanelItem > items ) {
		
		this.items = items;
		
		Iterator< GridPanelItem > iterator = this.items.iterator();
		while ( iterator.hasNext() ) {
			GridPanelItem item = iterator.next();
			if ( item.getColumn() == null ) {
				this.add( item.getRow(), item.getAlign(), item.getActualSize(), item.getComponent() );
			} else {
				this.add( item.getRow(), item.getColumn(), item.getAlign(), item.getActualSize(), item.getComponent() );
			}
		}
	}

	public List< GridPanelItem > getItems() {
		
		return items;
	}

	private GridPanel add( int row, int column, int align, boolean actualSize, SwingComponent< ?, ? > component ) {
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = row;
		constraints.gridx = column;
		constraints.anchor = align;
		if ( !actualSize ) {
			constraints.fill = GridBagConstraints.BOTH;
		}
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.insets = new Insets( space, space, space, space );
		
		toSwing().add( component.getJComponent(), constraints );
		return this;
	}
	
	private GridPanel add( int row, int align, boolean actualSize, SwingComponent< ?, ? > component ) {
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = row;
		constraints.gridx = 0;
		constraints.anchor = align;
		if ( !actualSize ) {
			constraints.gridwidth = GridBagConstraints.REMAINDER;
		}
		constraints.insets = new Insets( space, space, space, space );
		
		toSwing().add( component.getJComponent(), constraints );
		return this;
	}
}
