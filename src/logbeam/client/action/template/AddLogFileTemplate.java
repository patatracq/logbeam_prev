package logbeam.client.action.template;

import java.util.List;

import logbeam.business.LogFileTemplate;
import logbeam.client.bind.agenttemplate.AgentTemplateLogFiles;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;
import crudbeam.bind.StringBinding;


public class AddLogFileTemplate extends Action {

	private StringBinding name;
	private AgentTemplateLogFiles logFiles;
	
	@Override
	protected void executeAction( Object source ) {
		
		LogFileTemplate template = new LogFileTemplate();
		template.setFilename( name.getValue() );
		List< LogFileTemplate > templates = logFiles.getValue();
		templates.add( template );
		logFiles.setValue( templates );
	}

	@Required
	public void setName( StringBinding name ) {
		
		this.name = name;
	}
	
	@Required
	public void setLogFiles( AgentTemplateLogFiles logFiles ) {
		
		this.logFiles = logFiles;
	}
}
