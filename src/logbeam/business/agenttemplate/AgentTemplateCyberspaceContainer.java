package logbeam.business.agenttemplate;

import java.util.Collection;
import java.util.Iterator;

import logbeam.business.AgentTemplate;
import crudbeam.cyberspace.CyberspaceContainer;
import crudbeam.cyberspace.CyberspaceRequest;


public class AgentTemplateCyberspaceContainer extends CyberspaceContainer< AgentTemplate > implements
		AgentTemplateContainer {

	private boolean allLoaded = false;
	
	@Override
	@SuppressWarnings( "unchecked" )
	public Collection< AgentTemplate > getAll() {
		
		if ( !allLoaded ) {
			getObjects().clear();
			
			Collection< AgentTemplate > templates =
					(Collection< AgentTemplate >) getClient().callServer( new CyberspaceRequest( "agentTemplateDatabaseContainer", "getAll", null ) ).getReply();
			
			Iterator< AgentTemplate > iterator = templates.iterator();
			while ( iterator.hasNext() ) {
				AgentTemplate template = iterator.next();
				getObjects().put( template.getId(), template );
			}
		}
		
		allLoaded = true;

		return getObjects().values();
	}
	
	@Override
	protected AgentTemplate newObject() {

		return new AgentTemplate();
	}
}
