package Listener;

import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import TextArea.Window;
import YuNote.Note;
import YuNote.NoteDao;

import LocalPanel.JNConstans;
import LocalPanel.JNNoteTableModel;
import LocalUI.ComponentUtil;
import LocalWork.CloseTabPanel;



public class JNNoteTableListener implements ListSelectionListener {
	private static JNNoteTableModel noteTableModel;
	private static JTable notetable;

	//static JTextArea ta;
	
	public JNNoteTableListener() {
		noteTableModel = (JNNoteTableModel) ComponentUtil
				.getComponent("noteTableModel");
		notetable = (JTable) ComponentUtil.getComponent("notetable");
	}

	public void getNoteTable() {
		int categoryId = JNConstans.CUR_SELECT_CATEGORY_ID;
		List<Note> noteList = NoteDao.getNotesByCategoryId(categoryId);
		noteTableModel.removeRows(0, noteTableModel.getRowCount());
		noteTableModel.addRows(noteList);
		notetable.updateUI();
		notetable.setRowHeight(50);
		notetable.clearSelection();
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			Note note = noteTableModel.getNote(notetable.getSelectedRow());
			if (note == null)
				return;
			String title = note.getTitle();
			//String content = note.getContent();

			
			
			
			JTabbedPane tabbedPane = (JTabbedPane) ComponentUtil
					.getComponent("tabbedPane");
			JNConstans.CUR_SELECT_UUID = note.getUuid();
			int index = JNTabListener.isOpenTab(note.getUuid());
			if (index < 0) {
				CloseTabPanel closeTabPanel = new CloseTabPanel(title,
						tabbedPane);
				
				//tabbedPane.addTab(title, new JNNoteEditorPanel(note, JNConstans.EDITOR_TYPE_READ).getEditorPanel());
				Window window = new Window(note);
				note.setwindow(window);
				//note.setJTextArea(ta);
				
				
				
				
				tabbedPane.addTab(note.getTitle(), note.getwindow());
				
				
				
				int size = tabbedPane.getTabCount() - 1;
				tabbedPane.setSelectedIndex(size);
				tabbedPane.setTabComponentAt(size, closeTabPanel);
				JNConstans.CUR_SELECT_TAB_INDEX = size;
				JNTabListener.addTab(note.getUuid());
			} else {
				tabbedPane.setSelectedIndex(index);
				JNConstans.CUR_SELECT_TAB_INDEX = index;
			}

		}

	}

	public void getSearchNoteTable() {
		// TODO Auto-generated method stub
		
	}

}
