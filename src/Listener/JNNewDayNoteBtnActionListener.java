package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.UUID;

import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import LocalPanel.JNConstans;
import LocalPanel.JNStatusPanel;
import LocalUI.ComponentUtil;
import LocalWork.CategoryNode;
import LocalWork.CloseTabPanel;
import LocalWork.CustomMutableTreeNode;
import Main.DateUtil;
import TextArea.Window;
import YuNote.Category;
import YuNote.CategoryDao;
import YuNote.Note;

import jodd.util.StringUtil;


public class JNNewDayNoteBtnActionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		/**
		 * 1.创建我的日记分类 2.检查我的日记下面是否有年的目录，否则创建 3.检查年目录下是否有月的目录，否则创建
		 * 4.检查月目录下是否有日的目录，否则创建
		 */

		int yearid = this.checkYearCategory();
		int monthid = this.checkMonthCategory(yearid);
		int dayid = this.checkDayCategory(monthid);

		this.setSelectionPath(dayid);

		JNConstans.CUR_SELECT_CATEGORY_ID = dayid;

		JNStatusPanel.setStatusMessage("新建日记");
		JTabbedPane tabbedPane = (JTabbedPane) ComponentUtil.getComponent("tabbedPane");
		Note note = new Note();
		
		
		
		
		String uuid = UUID.randomUUID().toString();
		uuid = StringUtil.replace(uuid, "-", "").toUpperCase();
		note.setUuid(uuid);
		//note.setContent("");
		note.setTitle(DateUtil.getNowDate());
		note.setCategoryId(dayid);

		CloseTabPanel closeTabPanel = new CloseTabPanel(note.getTitle(), tabbedPane);
		
		
		Window window = new Window(note);
		note.setwindow(window);
		
		tabbedPane.addTab(note.getTitle(), window);
		
		
		
		int size = tabbedPane.getTabCount() - 1;
		tabbedPane.setSelectedIndex(size);
		tabbedPane.setTabComponentAt(size, closeTabPanel);
		JNConstans.CUR_SELECT_TAB_INDEX = size;
		JNTabListener.addTab(note.getUuid());

	}

	private CustomMutableTreeNode tempNode;// 临时node

	/**
	 * 在我的日记下面创建year
	 */
	private int checkYearCategory() {
		int year = DateUtil.getYear();

		Category yearCategory = new Category();
		yearCategory.setCategoryName(year + "年");
		yearCategory.setParentId(2);

		CategoryDao cdao = new CategoryDao();
		Category tempCategory = cdao.query(yearCategory);
		if (tempCategory == null) {
			yearCategory = cdao.save(yearCategory);
			CategoryNode yearCategoryNode = new CategoryNode(yearCategory.getId(), yearCategory.getCategoryName(),
					yearCategory.getParentId());
			CustomMutableTreeNode yearNode = new CustomMutableTreeNode(yearCategory.getCategoryName());
			yearNode.setCusObject(yearCategoryNode);
			JTree jtree = (JTree) ComponentUtil.getComponent("categoryTree");

			CustomMutableTreeNode rootNode = (CustomMutableTreeNode) jtree.getModel().getRoot();

			this.visitAllNodes(jtree, rootNode, 2);

			tempNode.add(yearNode);

			jtree.updateUI();
		} else {
			yearCategory = tempCategory;
		}
		return yearCategory.getId();

	}

	/**
	 * 在我的日记下面创建month
	 */
	private int checkMonthCategory(int yearid) {
		int month = DateUtil.getMonth();

		Category monthCategory = new Category();
		monthCategory.setCategoryName((month+1) + "月");
		monthCategory.setParentId(yearid);

		CategoryDao cdao = new CategoryDao();
		Category tempCategory = cdao.query(monthCategory);
		if (tempCategory == null) {
			monthCategory = cdao.save(monthCategory);
			CategoryNode monthCategoryNode = new CategoryNode(monthCategory.getId(), monthCategory.getCategoryName(),
					monthCategory.getParentId());
			CustomMutableTreeNode monthNode = new CustomMutableTreeNode(monthCategory.getCategoryName());
			monthNode.setCusObject(monthCategoryNode);
			JTree jtree = (JTree) ComponentUtil.getComponent("categoryTree");

			CustomMutableTreeNode rootNode = (CustomMutableTreeNode) jtree.getModel().getRoot();

			this.visitAllNodes(jtree, rootNode, yearid);

			tempNode.add(monthNode);
			jtree.updateUI();
		} else {
			monthCategory = tempCategory;
		}
		return monthCategory.getId();

	}

	/**
	 * 在我的日记下面创建day
	 */
	private int checkDayCategory(int monthid) {
		int day = DateUtil.getDay();

		Category dayCategory = new Category();
		dayCategory.setCategoryName(day + "日");
		dayCategory.setParentId(monthid);

		CategoryDao cdao = new CategoryDao();
		Category tempCategory = cdao.query(dayCategory);
		if (tempCategory == null) {
			dayCategory = cdao.save(dayCategory);
			CategoryNode dayCategoryNode = new CategoryNode(dayCategory.getId(), dayCategory.getCategoryName(),
					dayCategory.getParentId());
			CustomMutableTreeNode dayNode = new CustomMutableTreeNode(dayCategory.getCategoryName());
			dayNode.setCusObject(dayCategoryNode);
			JTree jtree = (JTree) ComponentUtil.getComponent("categoryTree");
			CustomMutableTreeNode rootNode = (CustomMutableTreeNode) jtree.getModel().getRoot();

			this.visitAllNodes(jtree, rootNode, monthid);

			tempNode.add(dayNode);

			jtree.updateUI();

			dayNode.setParent(tempNode);
			TreePath visiblePath = new TreePath(((DefaultTreeModel) jtree.getModel()).getPathToRoot(dayNode));
			jtree.expandPath(visiblePath);
			jtree.setSelectionPath(visiblePath);

		} else {
			dayCategory = tempCategory;
		}
		return dayCategory.getId();

	}

	/**
	 * 遍历所有节点，返回指定节点
	 * 
	 * @param tree
	 * @param node
	 */
	public void visitAllNodes(JTree tree, TreeNode node, int categoryId) {

		tree.makeVisible(new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(node)));
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				CustomMutableTreeNode n = (CustomMutableTreeNode) e.nextElement();
				CategoryNode cnote = (CategoryNode) n.getCusObject();
				if (cnote.getId() == categoryId) {
					tempNode = n;
				} else {
					visitAllNodes(tree, n, categoryId);
				}
			}
		}
	}

	public void setSelectionPath(int dayId) {
		JTree jtree = (JTree) ComponentUtil.getComponent("categoryTree");
		CustomMutableTreeNode rootNode = (CustomMutableTreeNode) jtree.getModel().getRoot();

		this.visitAllNodes(jtree, rootNode, dayId);

		TreePath visiblePath = new TreePath(((DefaultTreeModel) jtree.getModel()).getPathToRoot(tempNode));
		jtree.expandPath(visiblePath);
		jtree.setSelectionPath(visiblePath);

		JNConstans.CUR_SELECT_CATEGORY_ID = dayId;

	}

}
