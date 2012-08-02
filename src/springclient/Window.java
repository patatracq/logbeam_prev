package springclient;

import javax.swing.JFrame;

import springclient.menu.MenuBar;
import springclient.panel.Panel;


@SuppressWarnings("serial")
public class Window extends JFrame {

	private MenuBar windowMenuBar;
	private Panel< ?, ? > mainPanel;

	public Window() {
		
	}
	
	public MenuBar getWindowMenuBar() {
		
		return windowMenuBar;
	}
	
	public void setWindowMenuBar( MenuBar windowMenuBar ) {
		
		this.windowMenuBar = windowMenuBar;
	}
	
	public void setMainPanel( Panel< ?, ? > mainPanel ) {
		
		this.mainPanel = mainPanel;
	}

	public Panel< ?, ? > getMainPanel() {
		
		return mainPanel;
	}

	@Override
	public void pack() {
		
		if ( windowMenuBar != null ) {
			setJMenuBar( windowMenuBar.toSwing() );
		}
		
		if ( mainPanel != null ) {
			super.setContentPane( mainPanel.getJComponent() );
		}

		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
		super.pack();
	}
}
