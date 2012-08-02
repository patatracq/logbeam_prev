
package springclient.text;

import javax.swing.JTextArea;

import crudbeam.bind.PropertyBinding;




public class TextArea extends DefaultTextComponent< TextArea, JTextArea > {
	
	public TextArea( int rows, int columns, PropertyBinding< String > textBinding ) {
		
		super( new JTextArea( rows, columns ), textBinding );
		toSwing().setLineWrap( true );
		toSwing().setWrapStyleWord( true );
	}
}
