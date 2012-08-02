package logbeam.business.logmessage;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import logbeam.business.LogMessage;
import logbeam.server.MessageFilter;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.database.DatabaseContainer;


public class LogMessageDatabaseContainer extends DatabaseContainer
		implements LogMessageContainer {

	private static Logger logger = Logger.getLogger( LogMessageDatabaseContainer.class );
	
	private MessageFilter filter;
	
	@Override
	@Transactional
	public void save( BusinessPojo businessObject ) {
		
		logger.debug( "Executing statement LogMessageDatabaseContainer.save" );
		
		if ( filter.pass( (LogMessage) businessObject ) ) {
			super.save( businessObject );
		}
		
		logger.debug( "Done" );
	}
	
	@Override
	@Transactional( readOnly = true )
	public BusinessPojo get( Long id ) {

		logger.debug( "Executing query LogMessageDatabaseContainer.get( " + id + " )" );
		
		BusinessPojo pojo = (BusinessPojo) getCurrentSession().get( LogMessage.class, id );
		
		logger.debug( "Done" );
		
		return pojo;
	}
	
	@Override
	@SuppressWarnings( "unchecked" )
	@Transactional( readOnly = true )
	public Collection< LogMessage > getNewerThan( Long milliseconds ) {

		logger.debug( "Executing query LogMessageDatabaseContainer.getNewerThan( " + milliseconds + " )" );

		Collection< LogMessage > messages = getCurrentSession().createCriteria( LogMessage.class )
				.add( Restrictions.gt( "timestamp", new Date( milliseconds  ) ) )
				.addOrder( Order.desc( "timestamp" ) )
				.list();
		
		logger.debug( "Done" );
		
		return messages;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	@Transactional
	public void deleteLogMessagesOlderThan( long millis ) {
		
		logger.debug( "Executing query LogMessageDatabaseContainer.delete( " + millis + " )" );

		Transaction transaction = getCurrentSession().beginTransaction();
		List< LogMessage > results = getCurrentSession().createCriteria( LogMessage.class )
				.add( Restrictions.lt( "timestamp", new Date( millis ) ) )
				.setMaxResults( 100000 )
				.list();
		
		while ( results.size() > 0 ) {
			Iterator< LogMessage > iterator = results.iterator();
			while ( iterator.hasNext() ) {
				LogMessage logMessage = iterator.next();
				deleteQuiet( logMessage );

			}
			
			transaction.commit();
			logger.debug( "Commit" );

			transaction = getCurrentSession().beginTransaction();
			results = getCurrentSession().createCriteria( LogMessage.class )
					.add( Restrictions.lt( "timestamp", new Date( millis ) ) )
					.setMaxResults( 100000 )
					.list();
		}
		transaction.commit();

		logger.debug( "Done" );
	}
	
	@Required
	public void setFilter( MessageFilter filter ) {
		
		this.filter = filter;
	}
}
