package logbeam.client.action.template;

import logbeam.business.AgentTemplate;
import logbeam.client.bind.agenttemplate.AgentTemplateLogFiles;
import logbeam.client.bind.agenttemplate.AgentTemplateName;

import org.springframework.beans.factory.annotation.Required;

import springclient.table.Table;
import crudbeam.action.Action;


public class GetSelectedTemplate extends Action {

	private AgentTemplateName name;
	private AgentTemplateLogFiles logFiles;
	
	@Override
	protected void executeAction( Object source ) {
		
		if ( source != null && source instanceof Table ) {
			Table table = (Table) source;
			
			Object row = table.getModel().getValueAt( table.getSelectedRow() );
			if ( row != null && row instanceof AgentTemplate ) {
				AgentTemplate template = (AgentTemplate) row;
				name.setAgentTemplate( template );
				logFiles.setAgentTemplate( template );
			}
		}
	}

	@Required
	public void setName( AgentTemplateName name ) {
		
		this.name = name;
	}
	
	@Required
	public void setLogFiles( AgentTemplateLogFiles logFiles ) {
		
		this.logFiles = logFiles;
	}
}
