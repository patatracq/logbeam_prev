package logbeam.business.agent;

import java.util.Collection;
import java.util.Iterator;

import logbeam.business.Agent;

import org.apache.log4j.Logger;

import crudbeam.cyberspace.CyberspaceContainer;
import crudbeam.cyberspace.CyberspaceRequest;


public class AgentCyberspaceContainer extends CyberspaceContainer< Agent > implements
		AgentContainer {

	private static Logger logger = Logger.getLogger( AgentCyberspaceContainer.class );
	private boolean allLoaded = false;
	
	@Override
	@SuppressWarnings( "unchecked" )
	public Collection< Agent > getAll() {
		
		if ( !allLoaded ) {
			logger.debug( "Loading all agents" );
			getObjects().clear();
			
			Collection< Agent > agents =
					(Collection< Agent >) getClient().callServer(
							new CyberspaceRequest(
									getDatabaseContainerId(),
									"getAll",
									null ) )
							.getReply();
			
			Iterator< Agent > iterator = agents.iterator();
			while ( iterator.hasNext() ) {
				Agent agent = iterator.next();
				getObjects().put( agent.getId(), agent );
			}
			logger.debug( "Done" );
		}
		
		allLoaded = true;

		return getObjects().values();
	}

	@Override
	public Agent getByName( String name ) {
		
		Iterator< Agent > iterator = getObjects().values().iterator();
		while ( iterator.hasNext() ) {
			Agent agent = iterator.next();
			if ( agent.getName().equals( name ) ) {
				return agent;
			}
		}
		
		Agent agent = (Agent) getClient().callServer(
				new CyberspaceRequest(
						getDatabaseContainerId(),
						"getByName",
						CyberspaceRequest.parameters( new Object[] { name } ) ) )
				.getReply();

		return agent;
	}
	
	@Override
	public void keepAlive( Agent agent ) {
		
		getClient().callServer(
				new CyberspaceRequest(
						getDatabaseContainerId(),
						"keepAlive",
						CyberspaceRequest.parameters( new Object[] { agent } ) ) );
	}
	
	@Override
	protected Agent newObject() {

		return new Agent();
	}
}
