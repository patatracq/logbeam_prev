package logbeam.business.agenttemplate;

import java.util.Collection;
import java.util.List;

import logbeam.business.AgentTemplate;

import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.database.DatabaseContainer;


public class AgentTemplateDatabaseContainer extends DatabaseContainer
		implements AgentTemplateContainer {

	@Override
	@Transactional
	public BusinessPojo get( Long id ) {
		
		return (BusinessPojo) getCurrentSession().get( AgentTemplate.class, id );
	}
	
	@Override
	@SuppressWarnings( "unchecked" )
	@Transactional( readOnly = true )
	public Collection< AgentTemplate > getAll() {
		
		return (List< AgentTemplate >) getCurrentSession().createCriteria( AgentTemplate.class )
				.addOrder( Order.asc( "name" ) )
				.list();
	}
}
