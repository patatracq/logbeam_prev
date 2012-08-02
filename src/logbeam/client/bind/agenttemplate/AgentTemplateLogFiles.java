package logbeam.client.bind.agenttemplate;

import java.util.ArrayList;
import java.util.List;

import logbeam.business.AgentTemplate;
import logbeam.business.LogFileTemplate;
import logbeam.business.agenttemplate.AgentTemplateContainer;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;


public class AgentTemplateLogFiles extends PropertyBinding< List< LogFileTemplate > > {

	private AgentTemplate agentTemplate;
	private AgentTemplateContainer container;
//	private LogFileTemplateContainer logFileTemplateContainer;

	@Override
	public void reset() {
		
		agentTemplate = null;
		super.notifyListeners( getValue() );
	}
	
	@Override
	public List< LogFileTemplate > getValue() {

		if ( agentTemplate != null ) {
			return agentTemplate.getLogFileTemplates();
		} else {
			return new ArrayList< LogFileTemplate >();
		}
	}

	@Override
	public void setValue( List< LogFileTemplate > value ) {

		if ( agentTemplate == null ) {
			agentTemplate = (AgentTemplate) container.get( 0L );
		}
		
		agentTemplate.setLogFileTemplates( value );
		notifyListeners( agentTemplate.getLogFileTemplates() );
	}

	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		throw new RuntimeException( this.getClass().getName() + ".setValue( PropertyBinding< ? > value ) not implemented" );
	}

	@Override
	public void save() {

		container.save( agentTemplate );
	}
	
	@Override
	public void delete() {
		
		container.delete( agentTemplate );
		agentTemplate = null;
	}

	public AgentTemplate getAgentTemplate() {
		
		return agentTemplate;
	}
	
	public void setAgentTemplate( AgentTemplate agentTemplate ) {
		
		this.agentTemplate = agentTemplate;
		notifyListeners( agentTemplate.getLogFileTemplates() );
	}

	@Required
	public void setContainer( AgentTemplateContainer container ) {
		
		this.container = container;
	}
	
/*	@Required
	public void setLogFileTemplateContainer( LogFileTemplateContainer logFileTemplateContainer ) {
		
		this.logFileTemplateContainer = logFileTemplateContainer;
	}*/
}
