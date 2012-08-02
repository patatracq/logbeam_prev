package springclient.tree;

import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public class GenericTreeModel implements TreeModel {

	private DomainSpecificTreeModel domainTree;
	
	public GenericTreeModel( DomainSpecificTreeModel domainTree ) {
		
		this.domainTree = domainTree; 
	}
	
	@Override
	public void addTreeModelListener( TreeModelListener listener ) {

		domainTree.addTreeModelListener( listener );
	}

	@Override
	public void removeTreeModelListener( TreeModelListener listener ) {

		domainTree.removeTreeModelListener( listener );
	}

	@Override
	public Object getChild( Object parent, int childIndex ) {
	
		List< ? > children = domainTree.getChildren( parent );
		
		if ( children != null && childIndex < children.size() ) {
			return children.get( childIndex );
		} else {
			return null;
		}
	}

	@Override
	public int getChildCount( Object parent ) {

		List< ? > children = domainTree.getChildren( parent );
		if ( children != null ) {
			return children.size();
		} else {
			return 0;
		}
	}

	@Override
	public int getIndexOfChild( Object parent, Object child ) {

		List< ? > children = domainTree.getChildren( parent );
		if ( children != null ) {
			return children.indexOf( child );
		} else {
			return -1;
		}
	}

	@Override
	public Object getRoot() {
		
		return domainTree;
	}

	@Override
	public boolean isLeaf( Object node ) {
		
		return domainTree.isLeaf( node );
	}

	@Override
	public void valueForPathChanged( TreePath path, Object newValue ) {}
}
