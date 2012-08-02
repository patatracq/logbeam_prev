package springclient.tree;

import javax.swing.tree.TreeSelectionModel;


public class SelectionModelDiscontiguos implements SelectionModel {

	public Integer getSwingValue() {
		
		return TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
	}
}
