
package springclient.simple;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import springclient.DefaultButton;



public class Button extends DefaultButton< Button, JButton > implements ActionListener {

	public Button() {
		
		super( new JButton() );
	}
}
