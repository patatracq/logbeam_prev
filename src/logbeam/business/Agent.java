
package logbeam.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.log4j.Logger;

import jonas.JonasUnmarshalListener;
import jonas.annotations.JonasElement;
import jonas.annotations.JonasExcludeIfAncestor;
import crudbeam.business.BusinessPojo;

@Entity
public class Agent extends BusinessPojo implements JonasUnmarshalListener {

	private static Logger logger = Logger.getLogger( Agent.class );
	
	private String name;
	private List< LogFile > logFiles = new ArrayList< LogFile >();
	private Boolean status;
	private String statusInformation;
	private Timestamp lastReport;
	
	@Column( unique = true, nullable = false )
	@JonasElement
	public String getName() {
		
		return name;
	}
	
	public void setName( String name ) {
		
		this.name = name;
	}

	@OneToMany( fetch = FetchType.EAGER )
	@JoinColumn( name = "agent_id" )
	@JonasElement
	@JonasExcludeIfAncestor( ancestorClass = LogFile.class )
	public List< LogFile > getLogFiles() {
		
		return logFiles;
	}

	public void setLogFiles( List< LogFile > logFiles ) {
		
		this.logFiles = logFiles;
	}

	@Column
	@JonasElement
	public Boolean getStatus() {
		
		return status;
	}

	public void setStatus( Boolean status ) {
		
		this.status = status;
		
		if ( this.status != null && this.status ) {
			setStatusInformation( "OK" );
		}
	}

	@Column
	@JonasElement
	public String getStatusInformation() {
		
		return statusInformation;
	}

	public void setStatusInformation( String statusInformation ) {
		
		this.statusInformation = statusInformation;
	}

	@Column
	@JonasElement
	public Timestamp getLastReport() {
		
		return lastReport;
	}

	public void setLastReport( Timestamp lastReport ) {
		
		this.lastReport = lastReport;
	}

	@Override
	public boolean equals( Object object ) {
		
		if ( object == null ) {
			return false;
		}
		
		boolean result = object instanceof Agent;
		
		if ( name != null ) {
			result = result && name.equals( ( (Agent) object ).name );
		}
		
		return result;
	}
	
	public boolean notEquals( Agent agent ) {
		return
				( agent == null ) ||
				( name == null ? agent.name != null : !name.equals( agent.name ) ) ||
				( status == null ? agent.status != null : !status.equals( agent.status ) ) ||
				( statusInformation == null ? agent.statusInformation != null : !statusInformation.equals( agent.statusInformation ) );
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
	
	public String print() {
		
		StringBuffer s = new StringBuffer();
		s.append( name );
		s.append( "(" );
		s.append( getId() );
		s.append( "," );
		s.append( status );
		s.append( "," );
		s.append( statusInformation );
		s.append( "," );
		s.append( lastReport );
		s.append( ")" );
		
		return s.toString();
	}

	@Override
	public void unmarshalDone() {
		
		Iterator< LogFile > iterator = logFiles.iterator();
		while ( iterator.hasNext() ) {
			
			LogFile logFile = iterator.next();
			if ( logFile.getAgent() == null ) {
				logFile.setAgent( this );
			}
		}
	}
}
