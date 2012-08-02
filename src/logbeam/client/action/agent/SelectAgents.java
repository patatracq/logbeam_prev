package logbeam.client.action.agent;

import java.util.Vector;

import javax.swing.tree.TreePath;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.action.Action;

import logbeam.business.Agent;
import logbeam.client.LogMessagesTable;
import springclient.tree.Tree;


public class SelectAgents extends Action {

	private LogMessagesTable logMessagesTable;
	
	@Override
	protected void executeAction( Object source ) {
		
		if ( source != null && source instanceof Tree ) {
			Vector< Agent > selectedAgents = new Vector< Agent >();
			TreePath[] paths = ( (Tree) source ).toSwing().getSelectionPaths();
			if ( paths != null ) {
				for ( int i = 0; i < paths.length; i++ ) {
					if ( paths[ i ].getLastPathComponent() instanceof Agent ) {
						selectedAgents.add( (Agent) paths[ i ].getLastPathComponent() ); 
					}
				}
				
				logMessagesTable.setActiveAgents( selectedAgents );
			}
		}
	}

	@Required
	public void setLogMessagesTable( LogMessagesTable logMessagesTable ) {
		
		this.logMessagesTable = logMessagesTable;
	}
}
