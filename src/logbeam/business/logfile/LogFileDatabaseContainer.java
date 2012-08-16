package logbeam.business.logfile;

import java.util.List;

import logbeam.business.LogFile;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.database.DatabaseContainer;


public class LogFileDatabaseContainer extends DatabaseContainer
		implements LogFileContainer {

	private static Logger logger = Logger.getLogger( LogFileDatabaseContainer.class );
	
	@Override
	@Transactional
	public BusinessPojo get( Long id ) {
		
		return (BusinessPojo) getCurrentSession().get( LogFile.class, id );
	}
	
	@Transactional
	public LogFile getByFilenameAndAgent( String filename, Long agentId ) {
		
		logger.debug( "Executing query LogFileDatabaseContainer.getByFilenameAndAgent( " + filename + ", " + agentId + " )" );
		
		@SuppressWarnings( "unchecked" )
		List< LogFile > results = getCurrentSession().createCriteria( LogFile.class )
			.add( Restrictions.eq( "filename", filename ) )
			.add( Restrictions.eq( "agent_id", agentId ) )
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

		if ( businessObject instanceof LogFile ) {
			LogFile logFile = (LogFile) businessObject;
			LogFile persistedLogFile = getByFilenameAndAgent( logFile.getFilename(), ( logFile.getAgent() == null ? null : logFile.getAgent().getId() ) );
			return ( persistedLogFile == null ? null : persistedLogFile.getId() );
		} else {
			throw new RuntimeException( "Illegal parameter businessObject to LogFileDatabaseContainer.getPersistentId. Parameter should be an instance of LogFile, but is " + businessObject.getClass().getName() );
		}
	}
}
