package springclient.combo;

import javax.swing.JComboBox;

import springclient.DefaultSwingComponent;


public class ComboBox extends DefaultSwingComponent< ComboBox, JComboBox > {

	private GenericComboModel comboModel;
	
	public ComboBox( GenericComboModel comboData ) {
		
		super( new JComboBox() );
		
		comboModel = comboData;
		toSwing().setModel( comboModel );
	}

}
