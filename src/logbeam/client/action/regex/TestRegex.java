package logbeam.client.action.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;

import logbeam.client.bind.logmessagefilter.LogMessageFilterOriginalMessage;
import logbeam.client.bind.logmessagefilter.LogMessageFilterRegex;
import logbeam.client.bind.logmessagefilter.LogMessageFilterTestResult;


public class TestRegex extends Action {

	LogMessageFilterRegex regex;
	LogMessageFilterOriginalMessage originalMessage;
	LogMessageFilterTestResult testResult;
	
	@Override
	protected void executeAction( Object source ) {
		
		String input = originalMessage.getValue();
		Pattern pattern = Pattern.compile( regex.getValue() );
		
		long start = System.currentTimeMillis();
		Matcher matcher = pattern.matcher( input );
		boolean matches = matcher.matches();
		long done = System.currentTimeMillis();
		
		StringBuffer resultMessage = new StringBuffer();
		if ( matches ) {
			resultMessage.append( "Match" );
		} else {
			resultMessage.append( "No match" );
		}
		
		resultMessage.append( " (" + Long.toString( done - start ) + " ms)" );
		
		testResult.setValue( resultMessage.toString() );
	}

	@Required
	public void setRegex( LogMessageFilterRegex regex ) {
		
		this.regex = regex;
	}

	@Required
	public void setOriginalMessage( LogMessageFilterOriginalMessage originalMessage ) {
		
		this.originalMessage = originalMessage;
	}

	@Required
	public void setTestResult( LogMessageFilterTestResult testResult ) {
		
		this.testResult = testResult;
	}
}
