package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import YuNote.Category;
import YuNote.CategoryDao;

import LocalUI.ComponentUtil;
import LocalWork.CategoryNode;
import LocalWork.CustomMutableTreeNode;



public class JNTreeMouseListener implements MouseListener, ActionListener {
	private JTree tree;
	private JPopupMenu popMenu;
	private JMenuItem addItem;
	private JMenuItem delItem;
	private JMenuItem editItem;
	//private JTextArea

	// public JNTreeMouseListener(JTree tree, JPopupMenu popMenu, JMenuItem
	// addItem, JMenuItem delItem, JMenuItem editItem) {
	// this.tree = tree;
	// this.popMenu = popMenu;
	// this.addItem = addItem;
	// this.delItem = delItem;
	// this.editItem = editItem;
	// }

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public JPopupMenu getPopMenu() {
		return popMenu;
	}

	public void setPopMenu(JPopupMenu popMenu) {
		this.popMenu = popMenu;
	}

	public JMenuItem getAddItem() {
		return addItem;
	}

	public void setAddItem(JMenuItem addItem) {
		this.addItem = addItem;
	}

	public JMenuItem getDelItem() {
		return delItem;
	}

	public void setDelItem(JMenuItem delItem) {
		this.delItem = delItem;
	}

	public JMenuItem getEditItem() {
		return editItem;
	}

	public void setEditItem(JMenuItem editItem) {
		this.editItem = editItem;
	}

	public void mousePressed(MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		if (path == null) {
			return;
		}
		tree.setSelectionPath(path);

		if (e.getButton() == 3) {
			popMenu.show(tree, e.getX(), e.getY());
		}

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		CustomMutableTreeNode node = (CustomMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (e.getSource() == addItem) {
			CategoryNode categoryNode = (CategoryNode) node.getCusObject();
			String addNodeName = "新分类";
			Integer parentId = 0;
			if (!node.isRoot()) {
				parentId = categoryNode.getId();
			}

			Category category = new Category();
			category.setCategoryName(addNodeName);
			category.setParentId(parentId);

			category = new CategoryDao().saveNote(category);
			CategoryNode addCategoryNode = new CategoryNode(category.getId(), category.getCategoryName(),
					category.getParentId());

			CustomMutableTreeNode addNode = new CustomMutableTreeNode("new category");
			addNode.setCusObject(addCategoryNode);

			((DefaultTreeModel) tree.getModel()).insertNodeInto(addNode, node, node.getChildCount());
			
			tree.expandPath(tree.getSelectionPath());
		} else if (e.getSource() == delItem) {
			if (node.isRoot()) {
				return;
			}

			int childCount = node.getChildCount();
			if (childCount > 0) {
				JPanel mainPanel = (JPanel) ComponentUtil.getComponent("mainpanel");

				JOptionPane.showMessageDialog(mainPanel, "包含有子分类，请删除子分类", "无法删除", JOptionPane.WARNING_MESSAGE);

				return;
			}
			CategoryNode categoryNode = (CategoryNode) node.getCusObject();
			int id = categoryNode.getId();
			new CategoryDao().delete(id);
			((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
		} else if (e.getSource() == editItem) {
			System.out.println("node.getDepth()"+node.getDepth());
			if (node.isRoot()) {
				return;
			}
			tree.startEditingAtPath(tree.getSelectionPath());
		}

	}
}
