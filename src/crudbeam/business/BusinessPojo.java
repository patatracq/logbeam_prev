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

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import jonas.annotations.JonasElement;

/**
 * Abstract base class for Business POJO's.
 * 
 */
@MappedSuperclass
public abstract class BusinessPojo {

	private Long id;

	@Id
	@GeneratedValue
	@JonasElement
	public Long getId() {

		return id;
	}

	public void setId( Long id ) {

		this.id = id;
	}
}
