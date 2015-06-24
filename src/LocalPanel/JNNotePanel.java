package LocalPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JPanel;

/*public class JNNotePanel {
	private JPanel notePanel;
	private JPanel noteContentPanel;// 日志内容面板,设置为卡片布局,方便阅读和编辑切换
	private Note note;

	public JNNotePanel(Note note) {
		this.note = note;
		notePanel = new JPanel(new BorderLayout());
		notePanel.add(this._getNoteToolsPanel(), BorderLayout.NORTH);
		//notePanel.add(this._getNoteContenPanel(this.note), BorderLayout.CENTER);
		notePanel.add(note.getwindow(), BorderLayout.CENTER);
	}

	/**
	 * 获取日志工具栏
	 * 
	 * @return
	 */
/*	private JComponent _getNoteToolsPanel() {
		JPanel noteToolsPanel = new JNEditorTool(this.note, this.getNotePanel())
				.getEditorToolPanel();
		noteToolsPanel.setName("noteToolsPanel");
		return noteToolsPanel;
	}

	/**
	 * 获取日志内容部分,初始打开为阅读面板,点击工具栏编辑按钮后,切换为编辑面板
	 * 
	 * @return
	 */
/*	private JComponent _getNoteContenPanel(Note note) {
		return JNReaderPanel.getReader(note);
	}
	

	public JPanel getNotePanel() {
		return notePanel;
	}

}*/
