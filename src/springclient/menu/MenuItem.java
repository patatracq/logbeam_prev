
package springclient.menu;

import javax.swing.JMenuItem;

import springclient.DefaultButton;

public class MenuItem extends DefaultButton< MenuItem, JMenuItem > {

	public MenuItem() {
		
		super( new JMenuItem() );
	}

	public MenuItem( JMenuItem jMenuItem ) {

		super( jMenuItem );
	}
}
