package logbeam.business.logmessagefilter;

import java.util.Collection;
import java.util.List;

import logbeam.business.LogMessageFilter;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.database.DatabaseContainer;


public class LogMessageFilterDatabaseContainer extends DatabaseContainer
		implements LogMessageFilterContainer {

	private static Logger logger = Logger.getLogger( LogMessageFilterDatabaseContainer.class );
	
	@Override
	@Transactional
	public BusinessPojo get( Long id ) {
		
		return (BusinessPojo) getCurrentSession().get( LogMessageFilter.class, id );
	}
	
	@Override
	@SuppressWarnings( "unchecked" )
	@Transactional( readOnly = true )
	public Collection< LogMessageFilter > getAll() {
		
		return (List< LogMessageFilter >) getCurrentSession().createCriteria( LogMessageFilter.class ).list();
	}
	
	@Transactional
	public LogMessageFilter getByRegex( String regex ) {
		
		logger.debug( "Executing query LogMessageFilterDatabaseContainer.getByRegex( " + regex + " )" );
		
		@SuppressWarnings( "unchecked" )
		List< LogMessageFilter > results = getCurrentSession().createCriteria( LogMessageFilter.class )
			.add( Restrictions.eq( "regex", regex ) )
		    .list();
		
		logger.debug( "Done" );
		
		if ( !results.isEmpty() ) {
			return results.get( 0 );
		} else {
			return null;
		}
	}

	@Override
	protected Long getPersistentId( BusinessPojo businessObject ) {
		
		if ( businessObject instanceof LogMessageFilter ) {
			LogMessageFilter logMessageFilter = (LogMessageFilter) businessObject;
			LogMessageFilter persistedLogMessageFilter = getByRegex( logMessageFilter.getRegex() );
			return ( persistedLogMessageFilter == null ? null : persistedLogMessageFilter.getId() );
		} else {
			throw new RuntimeException( "Illegal parameter businessObject to LogMessageFilterDatabaseContainer.getPersistentId. Parameter should be an instance of LogMessageFilter, but is " + businessObject.getClass().getName() );
		}
	}
}
