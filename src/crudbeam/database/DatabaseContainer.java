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
package crudbeam.database;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import crudbeam.business.BusinessPojo;
import crudbeam.business.Container;

/**
 * Abstract Database Container For Business Objects. It holds a Hibernate
 * Session Factory.
 */
public abstract class DatabaseContainer extends Container {

	private static Logger logger = Logger.getLogger( DatabaseContainer.class );
	
	private SessionFactory sessionFactory;

	/**
	 * Save (insert/update) a business object. An active database
	 * session must exist. 
	 * 
	 * @param businessObject
	 */
	@Override
	@Transactional
	public void save( BusinessPojo businessObject ) {
		
		logger.debug( "Executing statement DatabaseContainer.save( " + businessObject + " )" );
		
		if ( businessObject.getId() != null ) {
			getCurrentSession().update( businessObject );
			changed( businessObject );
		} else {
			getCurrentSession().save( businessObject );
			created( businessObject );
		}
		
		logger.debug( "Done" );
	}
	
	/**
	 * Delete a business object. An active database
	 * session must exist.
	 * 
	 * @param businessObject
	 */
	@Override
	@Transactional
	public void delete( BusinessPojo businessObject ) {
		
		logger.debug( "Executing statement DatabaseContainer.delete( " + businessObject + " )" );

		getCurrentSession().delete( businessObject );
		deleted( businessObject );
		
		logger.debug( "Done" );
	}
	
	/**
	 * Delete a business object quietly. Used for deleting large collections
	 * for examble. An active database session must exist.
	 * 
	 * @param businessObject
	 */
	@Transactional
	public void deleteQuiet( BusinessPojo businessObject ) {
		
		logger.debug( "Executing statement DatabaseContainer.delete( " + businessObject + " )" );

		getCurrentSession().delete( businessObject );
		
		logger.debug( "Done" );
	}
	
	/**
	 * For Spring Framework IoC use only.
	 * 
	 * @param sessionFactory
	 */
	@Required
	public void setSessionFactory( SessionFactory sessionFactory ) {
		
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Provides subclasses with the active database session.
	 * 
	 * @return Active session
	 */
	protected Session getCurrentSession() {
		
		return sessionFactory.getCurrentSession();
	}
}
