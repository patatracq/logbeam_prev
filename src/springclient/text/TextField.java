
package springclient.text;

import javax.swing.JTextField;

import crudbeam.bind.PropertyBinding;


public class TextField extends DefaultTextComponent< TextField, JTextField > {
	
	public TextField( int columns, PropertyBinding< String > textBinding ) {
		
		super( new JTextField( columns ), textBinding );
	}
}
