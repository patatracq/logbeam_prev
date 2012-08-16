package logbeam.business.agent;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import logbeam.business.Agent;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.database.DatabaseContainer;


public class AgentDatabaseContainer extends DatabaseContainer
		implements AgentContainer {

	private static Logger logger = Logger.getLogger( AgentDatabaseContainer.class );
	
	@Override
	@Transactional
	public BusinessPojo get( Long id ) {
		
		logger.debug( "Executing query AgentDatabaseContainer.get( " + id + " )" );
		
		BusinessPojo pojo = (BusinessPojo) getCurrentSession().get( Agent.class, id );
		
		logger.debug( "Done" );
		return pojo;
	}
	
	@Override
	@SuppressWarnings( "unchecked" )
	@Transactional( readOnly = true )
	public Collection< Agent > getAll() {
	
		logger.debug( "Executing query AgentDatabaseContainer.getAll" );

		List< Agent > agents = (List< Agent >) getCurrentSession().createCriteria( Agent.class )
				.addOrder( Order.asc( "name" ) )
				.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY )
				.list();
		
		logger.debug( "Done" );
		return agents;
	}
	
	@Override
	@Transactional( readOnly = true )
	public Agent getByName( String name ) {

		logger.debug( "Executing query AgentDatabaseContainer.getByName( " + name + " )" );
		
		@SuppressWarnings( "unchecked" )
		List< Agent > results = getCurrentSession().createCriteria( Agent.class )
			.add( Restrictions.eq( "name", name ) )
		    .list();
		
		logger.debug( "Done" );
		
		if ( !results.isEmpty() ) {
			return results.get( 0 );
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void keepAlive( Agent agent ) {

		logger.debug( "Executing query AgentDatabaseContainer.keepAlive( " + agent.getName() + " )" );

		Agent lastKnownStatus = null;
		if ( agent.getId() != null ) {
			lastKnownStatus = (Agent) getCurrentSession().get( Agent.class, agent.getId() );
		} else {
			@SuppressWarnings( "unchecked" )
			List< Agent > results = (List< Agent >) getCurrentSession().createCriteria( Agent.class )
					.add( Restrictions.eq( "name", agent.getName() ) )
					.list();
			
			if ( !results.isEmpty() ) {
				lastKnownStatus = results.get( 0 );
			}
		}

		boolean newStatus = false;
		if ( lastKnownStatus == null ) {
			logger.warn( "New agent " + agent.getName() );
			lastKnownStatus = agent;
			newStatus = true;
		} else {
			if ( ( agent.getStatus() == null ? lastKnownStatus.getStatus() != null : !agent.getStatus().equals( lastKnownStatus.getStatus() ) ) ) {
				if ( agent.getStatus() != null && agent.getStatus() == true ) {
					logger.debug( "Previous status: " + lastKnownStatus.print() );
					logger.debug( "New status     : " + agent.print() );
					logger.warn( "Agent " + agent.getName() + " is ok" );
				} else {
					logger.warn( "Agent " + agent.getName() + " is in trouble: " + agent.getStatusInformation() );
				}
				
				newStatus = true;
			}
		}

		lastKnownStatus.setStatus( agent.getStatus() );
		lastKnownStatus.setStatusInformation( agent.getStatusInformation() );
		lastKnownStatus.setLastReport( new Timestamp( System.currentTimeMillis() ) );
		getCurrentSession().saveOrUpdate( lastKnownStatus );
		
		if ( newStatus ) {
			changed( lastKnownStatus );
		}
		logger.debug( "Saved status: " + lastKnownStatus.print() );
		
		logger.debug( "Done" );
	}

	@Override
	protected Long getPersistentId( BusinessPojo businessObject ) {
		
		if ( businessObject instanceof Agent ) {
			Agent agent = (Agent) businessObject;
			Agent persistedAgent = getByName( agent.getName() );
			return ( persistedAgent == null ? null : persistedAgent.getId() );
		} else {
			throw new RuntimeException( "Illegal parameter businessObject to AgentDatabaseContainer.getPersistentId. Parameter should be an instance of Agent, but is " + businessObject.getClass().getName() );
		}
	}
}
