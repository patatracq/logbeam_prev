package logbeam.business.logmessagefilter;

import java.util.Collection;
import java.util.Iterator;

import logbeam.business.LogMessageFilter;
import crudbeam.cyberspace.CyberspaceContainer;
import crudbeam.cyberspace.CyberspaceRequest;


public class LogMessageFilterCyberspaceContainer extends CyberspaceContainer< LogMessageFilter > implements
		LogMessageFilterContainer {

	private boolean allLoaded = false;
	
	@Override
	@SuppressWarnings( "unchecked" )
	public Collection< LogMessageFilter > getAll() {
		
		if ( !allLoaded ) {
			getObjects().clear();
			
			Collection< LogMessageFilter > filters =
					(Collection< LogMessageFilter >) getClient().callServer( new CyberspaceRequest( super.getDatabaseContainerId(), "getAll", null ) ).getReply();
			
			Iterator< LogMessageFilter > iterator = filters.iterator();
			while ( iterator.hasNext() ) {
				LogMessageFilter filter = iterator.next();
				getObjects().put( filter.getId(), filter );
			}
		}
		
		allLoaded = true;

		return getObjects().values();
	}
	
	@Override
	protected LogMessageFilter newObject() {

		return new LogMessageFilter();
	}
}
