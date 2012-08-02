package logbeam.client.action.filter;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;
import logbeam.client.bind.logmessagefilter.LogMessageFilterOriginalMessage;
import logbeam.client.bind.logmessagefilter.LogMessageFilterRegex;


public class LogMessageToFilter extends Action {

	private LogMessageFilterOriginalMessage originalMessage;
	private LogMessageFilterRegex regex;
	
	@Override
	protected void executeAction( Object source ) {

		/*
		 * Escape regex special characters.
		 */
		regex.setValue( "^" + originalMessage.getValue()
			.replace( "[", "\\[" )
			.replace( "]", "\\]" )
			.replace( "\\", "\\\\" )
			.replace( "^", "\\^" )
			.replace( "$", "\\$" )
			.replace( ".", "\\." )
			.replace( "|", "\\|" )
			.replace( "?", "\\?" )
			.replace( "*", "\\*" )
			.replace( "+", "\\+" )
			.replace( "(", "\\(" )
			.replace( ")", "\\)" ) + "$" );
	}

	@Required
	public void setOriginalMessage( LogMessageFilterOriginalMessage originalMessage ) {
		
		this.originalMessage = originalMessage;
	}
	
	@Required
	public void setRegex( LogMessageFilterRegex regex ) {
		
		this.regex = regex;
	}
}
