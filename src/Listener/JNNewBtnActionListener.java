package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JTabbedPane;

import YuNote.Note;

import LocalPanel.JNConstans;
import LocalPanel.JNNoteEditorPanel;
import LocalPanel.JNStatusPanel;
import LocalUI.ComponentUtil;
import LocalWork.CloseTabPanel;

import jodd.util.StringUtil;



public class JNNewBtnActionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		JNStatusPanel.setStatusMessage("新建笔记");
		JTabbedPane tabbedPane = (JTabbedPane) ComponentUtil.getComponent("tabbedPane");
		Note note = new Note();
		String uuid = UUID.randomUUID().toString();
		uuid = StringUtil.replace(uuid, "-", "").toUpperCase();
		note.setUuid(uuid);
		note.setCategoryId(JNConstans.CUR_SELECT_CATEGORY_ID);
		//note.setContent("");
		note.setTitle("newnote");
		CloseTabPanel closeTabPanel = new CloseTabPanel(note.getTitle(), tabbedPane);
		tabbedPane.addTab(note.getTitle(), new JNNoteEditorPanel(note, JNConstans.EDITOR_TYPE_WRITE).getEditorPanel());
		int size = tabbedPane.getTabCount() - 1;
		tabbedPane.setSelectedIndex(size);
		tabbedPane.setTabComponentAt(size, closeTabPanel);
		JNConstans.CUR_SELECT_TAB_INDEX = size;
		JNTabListener.addTab(note.getUuid());

	}

}
