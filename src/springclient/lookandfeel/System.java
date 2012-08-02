package springclient.lookandfeel;

import javax.swing.UIManager;

public class System implements LookAndFeel {

	private String className = UIManager.getSystemLookAndFeelClassName();
	
	@Override
	public String getClassName() {

		return className;
	}

}
