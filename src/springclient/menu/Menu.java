package springclient.menu;

import java.util.Iterator;
import java.util.List;

import javax.swing.JMenu;

import springclient.DefaultSwingComponent;


public class Menu extends DefaultSwingComponent< Menu, JMenu > {

	private List< MenuItem > items;
	
	public Menu() {
		
		super( new JMenu() );
	}
	
	public String getCaption() {
		
		return super.toSwing().getText();
	}
	
	public void setCaption( String caption ) {
		
		super.toSwing().setText( caption );
	}
	
	public List< MenuItem > getItems() {
		
		return items;
	}
	
	public void setItems( List< MenuItem > items ) {
		
		this.items = items;
		Iterator< MenuItem > iterator = items.iterator();
		while ( iterator.hasNext() ) {
			super.toSwing().add( iterator.next().toSwing() );
		}
	}
}
