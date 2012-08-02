package springclient.menu;

import java.util.Iterator;
import java.util.List;

import javax.swing.JMenuBar;

import springclient.DefaultSwingComponent;


public class MenuBar extends DefaultSwingComponent< MenuBar, JMenuBar > {

	public MenuBar() {
		
		super( new JMenuBar() );
	}
	
	public void setMenus( List< Menu > menus ) {
		
		Iterator< Menu > iterator = menus.iterator();
		while ( iterator.hasNext() ) {
			super.toSwing().add( iterator.next().toSwing() );
		}
	}
}
