package springclient.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

import crudbeam.event.BusinessObjectEvent;

public abstract class DomainSpecificTreeModel {

	private String rootCaption;
	
	private Vector< TreeModelListener > listeners = new Vector< TreeModelListener >();

	public String getRootCaption() {
		
		return rootCaption;
	}
	
	public void setRootCaption( String rootCaption ) {
		
		this.rootCaption = rootCaption;
	}
	
	public void addTreeModelListener( TreeModelListener listener ) {

		listeners.add( listener );
	}

	public void removeTreeModelListener( TreeModelListener listener ) {

		listeners.remove( listener );
	}

	public abstract List< ? > getChildren( Object node );

	public abstract boolean isLeaf( Object node );

	public void onApplicationEvent( BusinessObjectEvent event ) {
		
	}
	
	@Override
	public String toString() {
		
		return rootCaption;
	}

	
	protected void treeStructureChanged( TreeModelEvent event ) {
		
		Iterator< TreeModelListener > iterator = listeners.iterator();
		while ( iterator.hasNext() ) {
			iterator.next().treeStructureChanged( event );
		}
	}
	
	protected void treeNodesInserted( TreeModelEvent event ) {
		
		Iterator< TreeModelListener > iterator = listeners.iterator();
		while ( iterator.hasNext() ) {
			iterator.next().treeNodesInserted( event );
		}
	}
	
	protected void treeNodesChanged( TreeModelEvent event ) {
		
		Iterator< TreeModelListener > iterator = listeners.iterator();
		while ( iterator.hasNext() ) {
			iterator.next().treeNodesChanged( event );
		}
	}
}
