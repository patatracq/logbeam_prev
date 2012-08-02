package springclient;

import javax.swing.JDialog;

import springclient.panel.Panel;


@SuppressWarnings("serial")
public class Dialog extends JDialog implements BoundComponent {

	private Panel< ?, ? > mainPanel;

	@Override
	public void save() {
		
		mainPanel.save();
	}
	
	public Panel< ?, ? > getMainPanel() {
		
		return mainPanel;
	}

	public void setMainPanel( Panel< ?, ? > mainPanel ) {
		
		this.mainPanel = mainPanel;
		super.setContentPane( this.mainPanel.getJComponent() );
	}
}
