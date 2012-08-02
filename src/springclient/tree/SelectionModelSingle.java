package springclient.tree;

import javax.swing.tree.TreeSelectionModel;


public class SelectionModelSingle implements SelectionModel {

	public Integer getSwingValue() {
		
		return TreeSelectionModel.SINGLE_TREE_SELECTION;
	}
}
