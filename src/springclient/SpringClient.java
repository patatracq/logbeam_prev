package springclient;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import springclient.lookandfeel.LookAndFeel;

public class SpringClient {

	public static final String NULLVALUE = "<null>";
	
	private LookAndFeel lookAndFeel;
	private Window mainWindow;
	private Loadable[] loadables;
	
	public static void main(String[] args) {

		newClient( args ).run();
	}
	
	public static SpringClient newClient( String[] xmlContextConfigurationFilenames ) {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext( xmlContextConfigurationFilenames );
		context.registerShutdownHook();
		
		return context.getBean( SpringClient.class );
	}
	
	
	public void run() {
		
		mainWindow.pack();
		mainWindow.setVisible( true );
		
		for ( int i = 0; i < loadables.length; i++ ) {
			
			loadables[ i ].load();
		}
	}

	public LookAndFeel getLookAndFeel() {
		
		return lookAndFeel;
	}

	public Window getMainWindow() {
		
		return mainWindow;
	}
	
	public void setMainWindow( Window mainWindow ) {
		
		this.mainWindow = mainWindow;
	}

	public Loadable[] getLoadables() {
		
		return loadables;
	}

	public void setLoadables( Loadable[] loadables ) {
		
		this.loadables = loadables;
	}
}
