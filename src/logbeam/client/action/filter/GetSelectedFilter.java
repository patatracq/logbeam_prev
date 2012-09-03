package logbeam.client.action.filter;

import logbeam.business.LogMessageFilter;
import logbeam.client.bind.logmessagefilter.LogMessageFilterOriginalMessage;
import logbeam.client.bind.logmessagefilter.LogMessageFilterRegex;
import logbeam.client.bind.logmessagefilter.LogMessageFilterTestResult;

import org.springframework.beans.factory.annotation.Required;

import springclient.table.Table;
import crudbeam.action.Action;


public class GetSelectedFilter extends Action {

	private LogMessageFilterRegex regex;
	private LogMessageFilterOriginalMessage originalMessage;
	private LogMessageFilterTestResult testResult;
	
	@Override
	protected void executeAction( Object source ) {
	
		if ( source != null && source instanceof Table ) {
			Table table = (Table) source;
			
			Object row = table.getModel().getValueAt( table.getSelectedRow() );
			if ( row != null && row instanceof LogMessageFilter ) {
				LogMessageFilter filter = (LogMessageFilter) row;
				regex.setFilter( filter );
				originalMessage.setFilter( filter );
				testResult.setFilter( filter );
			}
		}
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
