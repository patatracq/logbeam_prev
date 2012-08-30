package logbeam.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreePath;

import logbeam.business.Agent;
import logbeam.business.LogFile;
import logbeam.business.agent.AgentContainer;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;

import springclient.Loadable;
import springclient.tree.DomainSpecificTreeModel;
import crudbeam.event.BusinessObjectEvent;

public class AgentTree extends DomainSpecificTreeModel implements Loadable, ApplicationListener< BusinessObjectEvent > {

	private static Logger logger = Logger.getLogger( AgentTree.class );

	private Set< Agent > agents = new ConcurrentSkipListSet< Agent >( new AgentComparator() );
	private AgentContainer agentContainer;

	public Boolean getStatus() {
		
		if ( agents != null ) {
			Iterator< Agent > iterator = agents.iterator();
			while ( iterator.hasNext() ) {
				Agent agent = iterator.next();
				if ( agent.getStatus() == null || !agent.getStatus() ) {
					return false;
				}
			}
		}

		return true;
	}
	
	@Override
	public List< ? > getChildren( Object node ) {

		if ( node == this ) {
			ArrayList< Agent > children = new ArrayList< Agent >( agents );
			
			return children;
		} else if ( node instanceof Agent ) {
			return ( (Agent) node ).getLogFiles();
		} else {
			return null;
		}
	}

	@Override
	public boolean isLeaf(Object node) {

		if ( node instanceof LogFile ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void load() {

		agents = new ConcurrentSkipListSet< Agent >( new AgentComparator() );
		agents.addAll( agentContainer.getAll() );
		super.treeStructureChanged( new TreeModelEvent( this, new Object[] { this } ) );
	}
	
	@Override
	public void onApplicationEvent( BusinessObjectEvent event ) {

		if ( event.getBusinessObject() instanceof Agent ) {
			this.handleAgentEvent( event ); 
		} else if ( event.getBusinessObject() instanceof LogFile ) {
			this.handleLogFileEvent( event );
		}
	}

	@Override
	public String toString() {
		
		return "Agents";
	}
	
	public void setAgentContainer( AgentContainer agentSo ) {
		
		this.agentContainer = agentSo;
	}

	private int getIndexOfChild( Object parent, Object child ) {

		List< ? > children = getChildren( parent );
		if ( children != null ) {
			return children.indexOf( child );
		} else {
			return -1;
		}
	}
	
	private void handleAgentEvent( BusinessObjectEvent event ) {
		
		Agent agent = (Agent) event.getBusinessObject();
		
		if ( event.created() ) {
			agents.add( agent );
			
			treeNodesInserted( new TreeModelEvent( this, new TreePath( this ), new int[] { getIndexOfChild( this, agent ) }, new Object[] { agent } ) );
		} else if ( event.changed() ) {
			if ( agents.contains( agent ) ) {
				List< Agent > oldTree = new ArrayList< Agent >( agents );
				if ( agent.notEquals( oldTree.get( oldTree.indexOf( agent ) ) ) ) {
					logger.debug( "Agent " + agent.getName() + " has changed. Updating tree." );
					agents.remove( agent );
					agents.add( agent );
					
					treeStructureChanged( new TreeModelEvent( this, new TreePath( this ) ) );
				}
			} else {
				agents.add( agent );
				
				treeNodesInserted( new TreeModelEvent( this, new TreePath( this ), new int[] { getIndexOfChild( this, agent ) }, new Object[] { agent } ) );
			}
		} else if ( event.deleted() ) {
			if ( agents.contains( agent ) ) {
				agents.remove( agent );
				
				treeStructureChanged( new TreeModelEvent( this, new TreePath( this ), new int[] { getIndexOfChild( this, agent ) }, new Object[] { agent } ) );
			}
		}
	}

	private void handleLogFileEvent( BusinessObjectEvent event ) {
		
		LogFile logFile = (LogFile) event.getBusinessObject();
		Agent agent = logFile.getAgent();
		
		int logFileIndex = agent.getLogFiles().indexOf( logFile );
		if ( logFileIndex == -1 ) {
			agent.getLogFiles().add( logFile );
			treeNodesInserted( new TreeModelEvent( this, new TreePath( new Object[] { this, agent } ), new int[] { getIndexOfChild( agent, logFile ) }, new Object[] { logFile } ) );
		} else {
			agent.getLogFiles().remove( logFileIndex );
			agent.getLogFiles().add( logFileIndex, logFile );
			treeNodesChanged( new TreeModelEvent( this, new TreePath( new Object[] { this, agent } ), new int[] { getIndexOfChild( agent, logFile ) }, new Object[] { logFile } ) );
		}
		
		treeNodesChanged( new TreeModelEvent( this, new TreePath( this ), new int[] { getIndexOfChild( this, agent ) }, new Object[] { agent } ) );
	}
	
	private class AgentComparator implements Comparator< Agent > {

		@Override
		public int compare( Agent left, Agent right ) {

			return left.getName().compareTo( right.getName() );
		}
	}
}
