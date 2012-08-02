 
package springclient.panel;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicBorders;

import springclient.BoundComponent;
import springclient.DefaultSwingComponent;
import springclient.SwingComponent;



public class Scroll extends DefaultSwingComponent< Scroll, JScrollPane > implements Panel< Scroll, JScrollPane > {

	private SwingComponent< ?, ? > component;
	
	public Scroll( SwingComponent< ?, ? > component ) {
		
		super( new JScrollPane( component.getJComponent() ) );
		
		toSwing().setPreferredSize( component.getJComponent().getPreferredSize() );
		toSwing().setBorder( BasicBorders.getTextFieldBorder() );
		component.getJComponent().setBorder( BorderFactory.createEmptyBorder() );
		
		this.component = component;
	}

	@Override
	public void save() {

		if ( BoundComponent.class.isInstance( component ) ) {
			( (BoundComponent) component ).save();
		}
	}

	@Override
	public void revalidate() {
		
		toSwing().getViewport().revalidate();
	}
}
