package LocalWork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import Listener.JNCategoryTreeListener;
import Listener.JNTreeMouseListener;
import YuNote.Category;



public class JNTree {

	private List<Category> categories;

	public JNTree(List<Category> categories) {
		this.categories = categories;
	}

	public JTree getJtree() {
		JTree tree = new JTree();

		tree.setEditable(true);

		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		JPopupMenu popMenu = new JPopupMenu();
		JMenuItem addItem = new JMenuItem("添加");
		JMenuItem delItem = new JMenuItem("删除");
		JMenuItem editItem = new JMenuItem("修改");
		//JMenuItem upItem = new JMenuItem("上传");
		
		
		
		
		JNTreeMouseListener jnTreeMouseListener = new JNTreeMouseListener();
		jnTreeMouseListener.setTree(tree);
		jnTreeMouseListener.setPopMenu(popMenu);
		jnTreeMouseListener.setAddItem(addItem);
		jnTreeMouseListener.setDelItem(delItem);
		jnTreeMouseListener.setEditItem(editItem);
		//jnTreeMouseListener.setupItem(upItem);
		
		addItem.addActionListener(jnTreeMouseListener);
		delItem.addActionListener(jnTreeMouseListener);
		editItem.addActionListener(jnTreeMouseListener);

		tree.addMouseListener(jnTreeMouseListener);
		popMenu.add(addItem);
		popMenu.add(delItem);
		popMenu.add(editItem);

		CustomMutableTreeNode root = new CustomMutableTreeNode("所有日记");
		createTreeNodesForCategoryNode(root, getCategoryNodeTreeFromPlainList());
		DefaultTreeModel defaultTreeModel = new DefaultTreeModel(root);
		defaultTreeModel.addTreeModelListener(new JNCategoryTreeListener());
		tree.setModel(defaultTreeModel);
		return tree;
	}

	Collection<CategoryNode> getCategoryNodeTreeFromPlainList() {
		Map<Integer, CategoryNode> nodes = new HashMap<Integer, CategoryNode>();

		for (Category category : categories) {
			CategoryNode cnode = new CategoryNode();
			cnode.setId(category.getId());
			cnode.setParentId(category.getParentId());
			cnode.setCategoryName(category.getCategoryName());
			nodes.put(category.getId(), cnode);
		}

		Collection<CategoryNode> result = new ArrayList<CategoryNode>();

		for (CategoryNode e : nodes.values()) {
			if (e.getParentId() != null && e.getParentId() != 0) {
				nodes.get(e.getParentId()).getChildren().add(e);
			} else {
				result.add(e);
			}
		}

		return result;
	}

	void createTreeNodesForCategoryNode(CustomMutableTreeNode dmtn, Collection<CategoryNode> categoryNodes) {
		for (CategoryNode child : categoryNodes) {
			CustomMutableTreeNode created = new CustomMutableTreeNode(child.getCategoryName());
			created.setCusObject(child);
			dmtn.add(created);
			createTreeNodesForCategoryNode(created, child.getChildren());
		}
	}
}
