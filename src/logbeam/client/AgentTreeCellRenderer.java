package logbeam.client;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import logbeam.business.Agent;
import logbeam.business.LogFile;


@SuppressWarnings( "serial" )
public class AgentTreeCellRenderer extends DefaultTreeCellRenderer {

	public AgentTreeCellRenderer() {
		
		super.textSelectionColor = Color.BLACK;
		super.backgroundSelectionColor = Color.LIGHT_GRAY;
	}
	
	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus ) {

		super.getTreeCellRendererComponent(
				tree,
				value,
				sel,
				expanded,
				leaf,
				row,
				hasFocus );

		if ( value instanceof AgentTree ) {
			AgentTree agentTree = (AgentTree) value;
			if ( agentTree.getStatus() ) {
				super.setForeground( Color.BLACK );
			} else {
				super.setForeground( Color.RED );
			}
		} else if ( value instanceof Agent ) {
			Agent agent = (Agent) value;
			if ( agent.getStatus() != null && agent.getStatus() ) {
				super.setForeground( Color.BLACK );
			} else {
				super.setForeground( Color.RED );
			}
		} else if ( value instanceof LogFile ) {
			LogFile logFile = (LogFile) value;
			if ( logFile.getStatus() != null && logFile.getStatus() ) {
				super.setForeground( Color.BLACK );
			} else {
				super.setForeground( Color.RED );
			}
		}
		
		return this;
	}
}
