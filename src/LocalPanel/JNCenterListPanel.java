package LocalPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Listener.JNNoteTableListener;
import LocalUI.ComponentUtil;



public class JNCenterListPanel {
	private JPanel centerListPanel;

	public JNCenterListPanel() {
		centerListPanel = new JPanel();
		centerListPanel.setBorder(BorderFactory.createEmptyBorder());
		centerListPanel.setLayout(new BorderLayout());
		//centerListPanel.add(createSearchField(), BorderLayout.NORTH);
		centerListPanel.add(createTable(), BorderLayout.CENTER);
	}

	public JPanel getCenterListPanel() {
		return centerListPanel;
	}

	/**
	 * 搜索框
	 * 
	 * @return
	 */
	public JComponent createSearchField() {
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		JTextField searchField = new JTextField(30);
		JButton btn = new JButton("未来的搜索功能");
		jp.add(createSearchList(), BorderLayout.WEST);
		jp.add(searchField, BorderLayout.CENTER);
		jp.add(btn, BorderLayout.EAST);
		return jp;
	}

	public JComponent createSearchList() {
		JComboBox selectionModeComboBox = new JComboBox() {
			public Dimension getMaximumSize() {
				return getPreferredSize();
			}
		};
		selectionModeComboBox.addItem("按时间排序");
		return selectionModeComboBox;
	}

	public JScrollPane createTable() {
		JNNoteTableModel noteTableModel = new JNNoteTableModel();
		JTable notetable = new JTable(noteTableModel);
		notetable.getSelectionModel().addListSelectionListener(new JNNoteTableListener());

		ComponentUtil.setComponent("noteTableModel", noteTableModel);
		ComponentUtil.setComponent("notetable", notetable);
		new JNNoteTableListener().getNoteTable();
		// notetable.setRowHeight(50);
		JScrollPane scrollpane = new JScrollPane(notetable);
		return scrollpane;
	}
}