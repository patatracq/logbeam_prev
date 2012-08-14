package logbeam.client.bind.agent;

import java.util.List;

import logbeam.business.Agent;
import logbeam.business.LogFile;
import logbeam.business.agent.AgentContainer;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;


public class AgentLogFiles extends PropertyBinding< List< LogFile > > {

	private Agent agent;
	private AgentContainer container;

	@Override
	public void reset() {
		
		agent = null;
		super.notifyListeners( getValue() );
	}
	
	@Override
	public List< LogFile > getValue() {

		if ( agent == null ) {
			agent = (Agent) container.get( 0L );
		}

		return agent.getLogFiles();
	}
	
	public void add( LogFile logFile ) {
		if ( agent == null ) {
			agent = (Agent) container.get( 0L );
		}

		agent.getLogFiles().add( logFile );
		notifyListeners( agent.getLogFiles() );
	}

	@Override
	public void setValue( List< LogFile > value ) {

		if ( agent == null ) {
			agent = (Agent) container.get( 0L );
		}
		
		agent.setLogFiles( value );
		notifyListeners( agent.getLogFiles() );
	}
	
	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		throw new RuntimeException( this.getClass().getName() + ".setValue( PropertyBinding< ? > value ) not implemented" );
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
		notifyListeners( agent.getLogFiles() );
	}

	@Required
	public void setContainer( AgentContainer container ) {
		
		this.container = container;
	}
}
