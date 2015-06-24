package LocalPanel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import YuNote.Note;


public class JNNoteTableModel extends AbstractTableModel {

	private Vector content = null;
	private Map<Integer, Note> noteMap = new HashMap<Integer, Note>();

	private String[] title_name = { "Dairy" };

	public JNNoteTableModel() {
		content = new Vector();
	}

	public void addRow(String name) {
		Vector v = new Vector();
		v.add(name);
		content.add(v);
	}

	public void addRows(List<Note> noteList) {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			Vector v = new Vector();
			String html = "<html>"+note.getTitle()+"<input type='hidden' value='aaa' /></html>";
			v.add(html);
			noteMap.put(i, note);
			content.add(v);
		}
	}

	public void removeRow(int row) {
		content.remove(row);
	}

	public void removeRows(int row, int count) {
		for (int i = 0; i < count; i++) {
			if (content.size() > row) {
				content.remove(row);
			}
		}
	}

	/**
	 * 让表格中某些值可修改，但需要setValueAt(Object value, int row, int col)方法配合才能使修改生效
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * 使修改的内容生效
	 */
	public void setValueAt(Object value, int row, int col) {
		((Vector) content.get(row)).remove(col);
		((Vector) content.get(row)).add(col, value);
		this.fireTableCellUpdated(row, col);
	}

	public String getColumnName(int col) {
		return title_name[col];
	}

	public int getColumnCount() {
		return title_name.length;
	}

	public int getRowCount() {
		return content.size();
	}

	public Object getValueAt(int row, int col) {
		return ((Vector) content.get(row)).get(col);
	}

	public Note getNote(Integer i) {
		return noteMap.get(i);
	}

	/**
	 * 返回数据类型
	 */
	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}

}
