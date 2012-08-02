
package logbeam.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import jonas.annotations.JonasElement;
import crudbeam.business.BusinessPojo;

@Entity
@Cacheable
public class AgentTemplate extends BusinessPojo {

	private String name;
	private List< LogFileTemplate > logFileTemplates= new ArrayList< LogFileTemplate >();
	
	@Column( unique = true, nullable = false )
	@JonasElement
	public String getName() {
		
		return name;
	}
	
	public void setName( String name ) {
		
		this.name = name;
	}

	@OneToMany( fetch = FetchType.EAGER )
	@JoinColumn( name = "id" )
	@JonasElement
	public List< LogFileTemplate > getLogFileTemplates() {
		
		return logFileTemplates;
	}

	public void setLogFileTemplates( List< LogFileTemplate > logFileTemplates ) {
		
		this.logFileTemplates = logFileTemplates;
	}

	@Override
	public boolean equals( Object object ) {
		
		if ( object == null ) {
			return false;
		}
		
		boolean result = object instanceof AgentTemplate;
		
		if ( name != null ) {
			result = result && name.equals( ( (AgentTemplate) object ).name );
		}
		
		return result;
	}
	
	@Override
	public int hashCode() {
		
		int result = 0;
		
		if ( name != null ) {
			result += name.hashCode();
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		
		return name;
	}
}
