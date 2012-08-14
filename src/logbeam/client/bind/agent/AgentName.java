package logbeam.client.bind.agent;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;
import logbeam.business.Agent;
import logbeam.business.agent.AgentContainer;


public class AgentName extends PropertyBinding< String > {

	private Agent agent;
	private AgentContainer container;

	@Override
	public void reset() {
		
		agent = null;
		super.notifyListeners( getValue() );
	}
	
	@Override
	public String getValue() {

		if ( agent != null ) {
			return agent.getName();
		} else {
			return "";
		}
	}

	@Override
	public void setValue( String value ) {

		if ( agent == null ) {
			agent = (Agent) container.get( 0L );
		}
		
		agent.setName( value );
		super.notifyListeners( agent.getName() );
	}

	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		if ( value != null && value.getValue() != null && value.getValue().getClass().equals( String.class ) ) {
			setValue( (String) value.getValue() );
		}
	}

	@Override
	public void save() {

		container.save( agent );
	}

	@Override
	public void delete() {
		
		container.delete( agent );
		agent = null;
	}

	public void setAgent( Agent agent ) {
		
		this.agent = agent;
		super.notifyListeners( agent.getName() );
	}

	@Required
	public void setContainer( AgentContainer container ) {
		
		this.container = container;
	}
}
