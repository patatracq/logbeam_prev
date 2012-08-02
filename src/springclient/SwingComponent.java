
package springclient;

import java.awt.Dimension;

import javax.swing.JComponent;


public interface SwingComponent< T, J > {

	public boolean getEnabled();
	public void setEnabled( boolean enabled );
	
	public void setAlignX( float alignment );
	public void setAlignY( float alignment );
	
	public void setPreferredSize( Dimension size );
	public void revalidate();
	
	public J toSwing();
	public JComponent getJComponent();
}
