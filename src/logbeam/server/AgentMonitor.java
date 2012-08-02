package logbeam.server;

import java.util.Date;
import java.util.Iterator;

import logbeam.business.Agent;
import logbeam.business.agent.AgentContainer;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public class AgentMonitor {

	private static Logger logger = Logger.getLogger( AgentMonitor.class );
	
	private Short keepAliveInterval;
	private AgentContainer agentContainer;
	
	public Short getKeepAliveInterval() {
		
		return keepAliveInterval;
	}

	public void setKeepAliveInterval( Short keepAliveInterval ) {
		
		this.keepAliveInterval = keepAliveInterval;
	}

	public AgentContainer getAgentContainer() {
		
		return agentContainer;
	}

	public void setAgentContainer( AgentContainer agentContainer ) {
		
		this.agentContainer = agentContainer;
	}

	@Transactional
	public void keepAliveCheck() {
		
		logger.debug( "Keep alive check starts" );
		
		Date now = new Date();
		Iterator< Agent > iterator = agentContainer.getAll().iterator();
		while ( iterator.hasNext() ) {
			Agent agent = iterator.next();
			logger.debug( "Checking agent " + agent.getName() );
			
			if ( agent.getLastReport() == null || ( now.getTime() - agent.getLastReport().getTime() > ( keepAliveInterval * 1000L ) ) ) {
				if ( agent.getStatus() != null && agent.getStatus() == true ) {
					logger.warn( "Agent " + agent.getName() + " maybe is in trouble" );
					
					agent.setStatus( false );
					agent.setStatusInformation( "No life sign within keep alive period." );
					
					logger.debug( "No life sign from " + agent.getName() );
					agentContainer.save( agent );
				}
			} else {
				logger.debug( "No change in state for " + agent.getName() );
			}
		}
		
		logger.debug( "Done" );
	}
}
