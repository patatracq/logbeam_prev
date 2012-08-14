package springclient.tree;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import springclient.DefaultSwingComponent;
import springclient.SwingComponent;
import crudbeam.action.Action;




public class Tree extends DefaultSwingComponent< Tree, JTree > implements TreeModelListener, TreeSelectionListener, MouseListener {

	public Action selectAction;
	public Action deselectAction;
	public Action doubleClickAction;
	
	public Tree( TreeModel treeModel ) {
		
		super( new JTree( treeModel ) );
		toSwing().addTreeSelectionListener( this );
		toSwing().addMouseListener( this );
	}
	
	public Tree( SwingComponent< ?, ? > parent, TreeModel treeModel ) {
		
		super( parent, new JTree( treeModel ) );
		toSwing().addTreeSelectionListener( this );
		toSwing().addMouseListener( this );
	}

	public void setRenderer( TreeCellRenderer renderer ) {
		
		toSwing().setCellRenderer( renderer );
	}
	
	public void setSelectionModel( SelectionModel model ) {
		
		TreeSelectionModel swingModel = null;
		if ( model.getSwingValue() != null ) {
			swingModel = new DefaultTreeSelectionModel();
			swingModel.setSelectionMode( model.getSwingValue() );
		}
		
		toSwing().setSelectionModel( swingModel );
	}
	
	public void setSelectAction( Action selectAction ) {
		
		this.selectAction = selectAction;
	}
	
	public void setDeselectAction( Action deselectAction ) {
		
		this.deselectAction = deselectAction;
	}
	
	public void setDoubleClickAction( Action doubleClickAction ) {
		
		this.doubleClickAction = doubleClickAction;
	}

	@Override
	public void valueChanged( TreeSelectionEvent e ) {

		if ( toSwing().getSelectionCount() > 0 && selectAction != null ) {
			selectAction.execute( this );
		} else if ( toSwing().getSelectionCount() == 0 && deselectAction != null ) {
			deselectAction.execute( this );
		}
	}

	@Override
	public void mouseClicked( MouseEvent event ) {

		if ( doubleClickAction != null && event.getSource() == toSwing() && event.getClickCount() >= 2 ) {
			doubleClickAction.execute( this );
		}
	}

	@Override
	public void mouseEntered( MouseEvent event ) {}

	@Override
	public void mouseExited( MouseEvent event ) {}

	@Override
	public void mousePressed( MouseEvent event ) {}

	@Override
	public void mouseReleased( MouseEvent event ) {}

	@Override
	public void treeNodesChanged( TreeModelEvent e ) {
		
		super.changed();
	}

	@Override
	public void treeNodesInserted( TreeModelEvent e ) {
		
		super.changed();
	}

	@Override
	public void treeNodesRemoved( TreeModelEvent e ) {
		
		super.changed();
	}

	@Override
	public void treeStructureChanged( TreeModelEvent e ) {
		
		super.changed();
	}
}
