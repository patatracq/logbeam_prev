/*
 * This file is part of CrudBeam.
 * 
 * CrudBeam is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CrudBeam is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CrudBeam.  If not, see <http://www.gnu.org/licenses/>.
 */
package crudbeam.business;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import crudbeam.event.BusinessObjectChanged;
import crudbeam.event.BusinessObjectCreated;
import crudbeam.event.BusinessObjectDeleted;
import crudbeam.event.BusinessObjectEvent;


/**
 * Abstract base class for Business Object Containers.
 * 
 */
public abstract class Container implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;
	
	public abstract void save( BusinessPojo businessObject );
	public abstract void delete( BusinessPojo businessObject );
	
	@Override
	public void setApplicationEventPublisher( ApplicationEventPublisher publisher ) {
		
		this.publisher = publisher;
	}
	
	protected void publish( BusinessObjectEvent event ) {
		
		publisher.publishEvent( event );
	}
	
	protected void created( BusinessPojo object ) {
		
		publish( new BusinessObjectEvent( this, new BusinessObjectCreated(), object ) );
	}
	
	protected void changed( BusinessPojo object ) {
		
		publish( new BusinessObjectEvent( this, new BusinessObjectChanged(), object ) );
	}
	
	protected void deleted( BusinessPojo object ) {
		
		publish( new BusinessObjectEvent( this, new BusinessObjectDeleted(), object ) );
	}
}
