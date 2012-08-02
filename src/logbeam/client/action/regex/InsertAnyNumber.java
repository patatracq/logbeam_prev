package logbeam.client.action.regex;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;
import springclient.text.TextArea;


public class InsertAnyNumber extends Action {

	private static final String REGEX_ANY_NUMBER = "[0-9]";
	
	private TextArea regexTextArea;
	
	@Override
	protected void executeAction( Object source ) {
		
		StringBuffer regex = new StringBuffer( regexTextArea.getText() );
		int offset = regexTextArea.toSwing().getCaretPosition();
		regex.insert( offset, REGEX_ANY_NUMBER );
		regexTextArea.setText( regex.toString() );
		regexTextArea.toSwing().setCaretPosition( offset + REGEX_ANY_NUMBER.length() );
		regexTextArea.toSwing().grabFocus();
	}

	@Required
	public void setRegexTextArea( TextArea regexTextArea ) {
		
		this.regexTextArea = regexTextArea;
	}
}
