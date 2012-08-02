package springclient.panel;

import javax.swing.JSplitPane;

import springclient.BoundComponent;
import springclient.DefaultSwingComponent;
import springclient.SwingComponent;




public abstract class SplitPanel extends DefaultSwingComponent< SplitPanel, JSplitPane > implements Panel< SplitPanel, JSplitPane > {

	public static final int X = JSplitPane.HORIZONTAL_SPLIT;
	public static final int Y = JSplitPane.VERTICAL_SPLIT;
	
	private SwingComponent< ?, ? > top;
	private SwingComponent< ?, ? > bottom;
	private SwingComponent< ?, ? > left;
	private SwingComponent< ?, ? > right;
	
	protected SplitPanel( int orientation ) {
		
		super( new JSplitPane( orientation ) );
	}
	
	@Override
	public void save() {
		
		if ( top != null && BoundComponent.class.isInstance( top ) ) {
			( (BoundComponent) top ).save();
		}
		
		if ( bottom != null && BoundComponent.class.isInstance( bottom ) ) {
			( (BoundComponent) bottom ).save();
		}
		
		if ( left != null && BoundComponent.class.isInstance( left ) ) {
			( (BoundComponent) left ).save();
		}
		
		if ( right != null && BoundComponent.class.isInstance( right ) ) {
			( (BoundComponent) right ).save();
		}
	}
	
	public void setTop( SwingComponent< ?, ? > component ) {
	
		toSwing().setTopComponent( component.getJComponent() );
		top = component;
	}
	
	public void setBottom( SwingComponent< ?, ? > component ) {

		toSwing().setBottomComponent( component.getJComponent() );
		bottom = component;
	}
	
	public void setLeft( SwingComponent< ?, ? > component ) {
	
		toSwing().setLeftComponent( component.getJComponent() );
		left = component;
	}

	public void setRight( SwingComponent< ?, ? > component ) {
	
		toSwing().setRightComponent( component.getJComponent() );
		right = component;
	}
}
