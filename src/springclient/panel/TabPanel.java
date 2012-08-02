
package springclient.panel;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.JTabbedPane;

import springclient.BoundComponent;
import springclient.DefaultSwingComponent;
import springclient.SwingComponent;



public class TabPanel extends DefaultSwingComponent< TabPanel, JTabbedPane > implements Panel< TabPanel, JTabbedPane > {

	private LinkedHashMap< String, SwingComponent< ?, ? > > tabs;
	
	public TabPanel() {
		
		super( new JTabbedPane() );
	}

	@Override
	public void save() {
		
		Iterator< SwingComponent< ?, ? > > iterator = tabs.values().iterator();
		while ( iterator.hasNext() ) {
			SwingComponent< ?, ? > component = iterator.next();
			if ( BoundComponent.class.isInstance( component ) ) {
				( (BoundComponent) component ).save();
			}
		}
	}

	public void setTabs( LinkedHashMap< String, SwingComponent< ?, ? > > tabs ) {
		
		this.tabs = tabs;
		super.toSwing().removeAll();
		
		Iterator< Entry< String, SwingComponent< ?, ? > > > iterator = tabs.entrySet().iterator();
		while ( iterator.hasNext() ) {
			Entry< String, SwingComponent< ?, ? > > tab = iterator.next();
			toSwing().addTab( tab.getKey(), tab.getValue().getJComponent() );
		}
	}

	public LinkedHashMap< String, SwingComponent< ?, ? > > getTabs() {
		
		return tabs;
	}
}
