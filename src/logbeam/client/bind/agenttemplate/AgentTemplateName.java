package logbeam.client.bind.agenttemplate;

import logbeam.business.AgentTemplate;
import logbeam.business.agenttemplate.AgentTemplateContainer;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;


public class AgentTemplateName extends PropertyBinding< String > {

	private AgentTemplate agentTemplate;
	private AgentTemplateContainer container;

	@Override
	public void reset() {
		
		agentTemplate = null;
		super.notifyListeners( getValue() );
	}
	
	@Override
	public String getValue() {

		if ( agentTemplate != null ) {
			return agentTemplate.getName();
		} else {
			return "";
		}
	}

	@Override
	public void setValue( String value ) {

		if ( agentTemplate == null ) {
			agentTemplate = (AgentTemplate) container.get( 0L );
		}
		
		agentTemplate.setName( value );
		super.notifyListeners( agentTemplate.getName() );
	}

	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		if ( value.getValue().getClass().equals( String.class ) ) {
			setValue( (String) value.getValue() );
		}
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

	public void setAgentTemplate( AgentTemplate agentTemplate ) {
		
		this.agentTemplate = agentTemplate;
		super.notifyListeners( agentTemplate.getName() );
	}

	@Required
	public void setContainer( AgentTemplateContainer container ) {
		
		this.container = container;
	}
}
