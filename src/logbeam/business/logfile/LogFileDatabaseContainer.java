package logbeam.business.logfile;

import logbeam.business.LogFile;

import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.database.DatabaseContainer;


public class LogFileDatabaseContainer extends DatabaseContainer
		implements LogFileContainer {

	@Override
	@Transactional
	public BusinessPojo get( Long id ) {
		
		return (BusinessPojo) getCurrentSession().get( LogFile.class, id );
	}
}
