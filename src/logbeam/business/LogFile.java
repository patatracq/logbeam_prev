
package logbeam.business;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jonas.annotations.JonasElement;
import jonas.annotations.JonasExcludeIfAncestor;
import crudbeam.business.BusinessPojo;

@Entity
@Cacheable
public class LogFile extends BusinessPojo {

	private static final String STATUS_UNKNOWN = "Status unknown";
	
	private Agent agent;
	private String filename;
	private Boolean status = false;
	private String statusInformation = STATUS_UNKNOWN;
	
	@ManyToOne
	@JoinColumn( name = "agent_id", updatable = false, insertable = false )
	@JonasElement
	@JonasExcludeIfAncestor( ancestorClass = Agent.class )
	public Agent getAgent() {
		
		return agent;
	}
	
	public void setAgent( Agent agent ) {
		
		this.agent = agent;
	}
	
	@Column( nullable = false )
	@JonasElement
	public String getFilename() {
		
		return filename;
	}
	
	public void setFilename( String filename ) {
		
		this.filename = filename;
	}
	
	@Column( nullable = false )
	@JonasElement
	public Boolean getStatus() {
		
		return status;
	}

	public void setStatus( Boolean status ) {
		
		this.status = status;
		
		if ( this.status ) {
			setStatusInformation( "OK" );
		}
	}

	@Column
	@JonasElement
	public String getStatusInformation() {
		
		return statusInformation;
	}

	public void setStatusInformation( String statusInformation ) {
		
		if ( statusInformation != null ) {
			this.statusInformation = statusInformation;
		} else {
			this.statusInformation = STATUS_UNKNOWN;
		}
	}

	@Override
	public boolean equals( Object object ) {
		
		return
			( object != null ) &&
			( object instanceof LogFile ) &&
			agent.equals( ( (LogFile) object ).agent ) && 
			filename.equals( ( (LogFile) object ).filename );
	}
	
	@Override
	public int hashCode() {
		
		return agent.hashCode() + filename.hashCode();
	}
	
	@Override
	public String toString() {
		
		return filename;
	}
}
