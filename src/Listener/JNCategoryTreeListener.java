package Listener;

import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import YuNote.Category;
import YuNote.CategoryDao;

import LocalPanel.JNConstans;
import LocalUI.ComponentUtil;
import LocalWork.CategoryNode;
import LocalWork.CustomMutableTreeNode;


public class JNCategoryTreeListener implements TreeSelectionListener, TreeModelListener {

	/**
	 * 选择节点改变
	 */
	public void valueChanged(TreeSelectionEvent e) {

		JTree jtree = (JTree) ComponentUtil.getComponent("categoryTree");
		CustomMutableTreeNode defaultNode = (CustomMutableTreeNode) jtree.getLastSelectedPathComponent();
		if (defaultNode == null) {
			return;
		}
		if (defaultNode != null) {
			CategoryNode cnode = (CategoryNode) defaultNode.getCusObject();
			int cid = -1;
			if(cnode!=null){
				cid = cnode.getId();
			}
			JNConstans.CUR_SELECT_CATEGORY_ID = cid;
			new JNNoteTableListener().getNoteTable();
			if (defaultNode.isRoot()) {
			} else {
				if (defaultNode.isLeaf()) {
				} else {

				}
			}
		}
	}

	/**
	 * 修改节点名称
	 */
	public void treeNodesChanged(TreeModelEvent e) {
		TreePath treePath = e.getTreePath();
		CustomMutableTreeNode node = (CustomMutableTreeNode) treePath.getLastPathComponent();

		if (node.isRoot()) {
			return;
		}

		try {
			int[] index = e.getChildIndices();
			node = (CustomMutableTreeNode) node.getChildAt(index[0]);

			String newName = node.getUserObject() + "";
			CategoryNode updateCategoryNode = (CategoryNode) node.getCusObject();
			Category category = new Category(updateCategoryNode.getId(), newName, updateCategoryNode.getParentId());
			category = new CategoryDao().update(category);
			System.out.println(category.toString());
		} catch (NullPointerException exc) {
			exc.printStackTrace();
		}

	}

	public void treeNodesInserted(TreeModelEvent e) {
		// TODO Auto-generated method stub

	}

	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub

	}

	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub

	}

}
