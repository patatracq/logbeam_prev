
package springclient.text;

import javax.swing.JLabel;
import javax.swing.JTextField;

import crudbeam.bind.PropertyBinding;



public class TextLabel extends DefaultTextComponent< TextLabel, JLabel > {

	public TextLabel( int columns, PropertyBinding< String > textBinding ) {
		
		super( new JTextField( columns ), textBinding );
		
		super.setEnabled( false );
	}
}
