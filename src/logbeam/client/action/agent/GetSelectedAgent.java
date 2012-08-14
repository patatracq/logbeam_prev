package logbeam.client.action.agent;

import javax.swing.tree.TreePath;

import logbeam.business.Agent;
import logbeam.client.bind.agent.AgentLogFiles;
import logbeam.client.bind.agent.AgentName;

import org.springframework.beans.factory.annotation.Required;

import springclient.tree.Tree;
import crudbeam.action.Action;


public class GetSelectedAgent extends Action {

	private Tree agentTree;
	private AgentName name;
	private AgentLogFiles logFiles;

	@Override
	protected void executeAction( Object source ) {

		TreePath[] paths = agentTree.toSwing().getSelectionPaths();
		if ( paths != null ) {
			if ( paths.length == 1 && paths[ 0 ].getLastPathComponent() instanceof Agent ) {
				Agent selectedAgent = (Agent) paths[ 0 ].getLastPathComponent();
				name.setAgent( selectedAgent );
				logFiles.setAgent( selectedAgent );
			}
		}
	}
	
	@Required
	public void setAgentTree( Tree agentTree ) {
		
		this.agentTree = agentTree;
	}

	@Required
	public void setName( AgentName name ) {
		
		this.name = name;
	}
	
	@Required
	public void setLogFiles( AgentLogFiles logFiles ) {
		
		this.logFiles = logFiles;
	}
}
