package springclient.tree;

import javax.swing.tree.TreeSelectionModel;


public class SelectionModelContiguos implements SelectionModel {

	public Integer getSwingValue() {
		
		return TreeSelectionModel.CONTIGUOUS_TREE_SELECTION;
	}
}
