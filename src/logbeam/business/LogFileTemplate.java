
package logbeam.business;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

import jonas.annotations.JonasElement;
import crudbeam.business.BusinessPojo;

@Entity
@Cacheable
public class LogFileTemplate extends BusinessPojo {

	private String filename;

	@Column( nullable = false )
	@JonasElement
	public String getFilename() {
		
		return filename;
	}
	
	public void setFilename( String filename ) {
		
		this.filename = filename;
	}

	@Override
	public boolean equals( Object object ) {
		
		return
			( object != null ) &&
			( object instanceof LogFileTemplate ) &&
			filename.equals( ( (LogFileTemplate) object ).filename );
	}
	
	@Override
	public int hashCode() {
		
		return filename.hashCode();
	}
	
	@Override
	public String toString() {
		
		return filename;
	}
}
