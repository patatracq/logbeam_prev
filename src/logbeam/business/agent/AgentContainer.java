package logbeam.business.agent;

import java.util.Collection;

import logbeam.business.Agent;
import crudbeam.business.ContainerInterface;


public interface AgentContainer extends ContainerInterface {

	public Collection< Agent > getAll();
	public Agent getByName( String name );
	public void keepAlive( Agent agent );
}
