package logbeam.business.logmessagefilter;

import java.util.Collection;
import java.util.List;

import logbeam.business.LogMessageFilter;

import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.database.DatabaseContainer;


public class LogMessageFilterDatabaseContainer extends DatabaseContainer
		implements LogMessageFilterContainer {

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
}
