package logbeam.business.logfiletemplate;

import logbeam.business.LogFileTemplate;

import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.database.DatabaseContainer;


public class LogFileTemplateDatabaseContainer extends DatabaseContainer
		implements LogFileTemplateContainer {

	@Override
	@Transactional
	public BusinessPojo get( Long id ) {
		
		return (BusinessPojo) getCurrentSession().get( LogFileTemplate.class, id );
	}
}
