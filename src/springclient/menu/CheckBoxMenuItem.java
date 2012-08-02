package springclient.menu;

import javax.swing.JCheckBoxMenuItem;

import springclient.SelectComponent;

public class CheckBoxMenuItem extends MenuItem implements SelectComponent {

	public CheckBoxMenuItem() {
		
		super( new JCheckBoxMenuItem() );
	}
	
	@Override
	public boolean isSelected() {

		return toSwing().getState();
	}
	
	@Override
	public JCheckBoxMenuItem toSwing() {
		
		return (JCheckBoxMenuItem) super.toSwing();
	}
}
