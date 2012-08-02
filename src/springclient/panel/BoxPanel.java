package springclient.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import springclient.BoundComponent;
import springclient.SwingComponent;



public abstract class BoxPanel extends DefaultPanel< BoxPanel > implements Panel< BoxPanel, JPanel > {

	public static int X = BoxLayout.X_AXIS;
	public static int Y = BoxLayout.Y_AXIS;

	private int orientation;
	public int space = PanelDefaults.SPACE;
	private List< SwingComponent< ?, ? > > components;
	
	protected BoxPanel( int orientation ) {
		
		super( new JPanel() );
		
		this.orientation = orientation;
		BoxLayout layout = new BoxLayout( toSwing(), this.orientation );
		toSwing().setLayout( layout );
		
	}
	
	@Override
	public void save() {
		
		Iterator< SwingComponent< ?, ? > > iterator = components.iterator();
		while ( iterator.hasNext() ) {
			SwingComponent< ?, ? > component = iterator.next();
			if ( BoundComponent.class.isInstance( component ) ) {
				( (BoundComponent) component ).save();
			}
		}
	}

	public int getSpace() {
		
		return space;
	}

	public void setSpace( int space ) {
		
		this.space = space;
	}
	
	public List< SwingComponent< ?, ? > > getComponents() {
		
		return components;
	}

	public void setComponents( List< SwingComponent< ?, ? > > components ) {
		
		this.components = components;
		
		toSwing().removeAll();
		toSwing().add( space() );
		
		Iterator< SwingComponent< ?, ? > > iterator = this.components.iterator();
		while ( iterator.hasNext() ) {
			
			toSwing().add( iterator.next().getJComponent() );
			toSwing().add( space() );
		}
	}

	private Component space() {
		
		if ( orientation == BoxLayout.X_AXIS ) {
			return horizontalSpace();
		} else {
			return verticalSpace();
		}
	}
	
	private Component horizontalSpace() {
		
		return Box.createRigidArea( new Dimension( space, 0 ) );
	}

	private Component verticalSpace() {
		
		return Box.createRigidArea( new Dimension( 0, space ) );
	}
}
