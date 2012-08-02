package logbeam.business.agenttemplate;

import java.util.Collection;

import crudbeam.business.ContainerInterface;

import logbeam.business.AgentTemplate;



public interface AgentTemplateContainer extends ContainerInterface {

	public Collection< AgentTemplate > getAll();
}
