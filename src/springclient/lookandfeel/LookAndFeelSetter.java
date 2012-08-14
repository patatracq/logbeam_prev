package springclient.lookandfeel;

import javax.swing.UIManager;

import springclient.UiException;


public class LookAndFeelSetter {

	public LookAndFeelSetter( LookAndFeel lookAndFeel ) {
		
		try {
			UIManager.setLookAndFeel( lookAndFeel.getClassName() );
		} catch ( Exception e ) {
			throw new UiException( "Could not load look and feel due to: " + e.getMessage(), e );
		}
	}
}
