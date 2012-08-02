package crudbeam.cyberspace;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationListener;

import crudbeam.business.BusinessPojo;
import crudbeam.business.Container;
import crudbeam.event.BusinessObjectChanged;
import crudbeam.event.BusinessObjectCreated;
import crudbeam.event.BusinessObjectDeleted;
import crudbeam.event.BusinessObjectEvent;


public abstract class CyberspaceContainer< T > extends Container implements
		ApplicationListener< BusinessObjectEvent > {

	private String databaseContainerId;
	private CyberspaceClient client;
	private Map< Long, T > businessObjects;
	
	public CyberspaceContainer() {
		
		businessObjects = new ConcurrentSkipListMap< Long, T >();
	}
	
	public T get( Long id ) {
		
		if ( businessObjects.containsKey( id ) ) {
			return businessObjects.get( id );
		} else if ( id == 0 ) {
			T businessObject = newObject();
			businessObjects.put( 0L, businessObject );
			return businessObject;
		} else {
			@SuppressWarnings( "unchecked" )
			T businessObject = (T) client.callServer(
					new CyberspaceRequest(
							getDatabaseContainerId(),
							"get",
							CyberspaceRequest.parameters( new Object[] { id } ) ) )
					.getReply();
			
			return businessObject;
		}
	}
	
	@Override
	public void save( BusinessPojo businessObject ) {

		client.callServer(
				new CyberspaceRequest(
						getDatabaseContainerId(),
						"save",
						CyberspaceRequest.parameters( new Object[] { businessObject } ) ) );
	}

	@Override
	public void delete( BusinessPojo businessObject ) {

		client.callServer(
				new CyberspaceRequest(
						getDatabaseContainerId(),
						"delete",
						CyberspaceRequest.parameters( new Object[] { businessObject } ) ) );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public void onApplicationEvent( BusinessObjectEvent event ) {

		BusinessPojo businessObject = event.getBusinessObject();
		if ( event.getType() instanceof BusinessObjectCreated ) {
			if ( !businessObjects.containsKey( businessObject.getId() ) ) {
				businessObjects.put( businessObject.getId(), (T) businessObject );
			}
		} else if ( event.getType() instanceof BusinessObjectChanged ) {
			businessObjects.put( businessObject.getId(), (T) businessObject );
		} else if ( event.getType() instanceof BusinessObjectDeleted ) {
			businessObjects.remove( businessObject.getId() );
		}
	}
	
	@Required
	public void setClient( CyberspaceClient client ) {
		
		this.client = client;
	}
	
	public CyberspaceClient getClient() {
		
		return client;
	}
	
	@Required
	public void setDatabaseContainerId( String databaseContainerId ) {
		
		this.databaseContainerId = databaseContainerId; 
	}
	
	public String getDatabaseContainerId() {
		
		return databaseContainerId;
	}

	protected abstract T newObject();
	
	protected Map< Long, T > getObjects() {
		
		return businessObjects;
	}
}
