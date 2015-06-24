package LocalPanel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import LocalUI.WebBrowerUtil;
import YuNote.Note;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;


public class JNNoteEditorPanel {
	private static final Logger log = LoggerFactory.getLogger(JNNoteEditorPanel.class);

	private Note note;
	private int editorType;

	private JTextField titleTextField;
	private JWebBrowser webBrowser;
	private JPanel editorPanel;
	private JPanel notePanel;

	public JNNoteEditorPanel(Note note, int editorType) {
		this.note = note;
		this.editorType = editorType;
		this.notePanel = new JPanel(new BorderLayout());
	}

	public JPanel getEditorPanel() {
		this._getWebBrowser();
		editorPanel = new JPanel(new BorderLayout());
		editorPanel.add(this._getEditorNotePanel(), BorderLayout.CENTER);
		return editorPanel;
	}

	/**
	 * 编辑器panel
	 * 
	 * @return
	 */
	private JComponent _getEditorNotePanel() {
		/*if (editorType == JNConstans.EDITOR_TYPE_READ) {
			this.webBrowser = WebBrowerUtil.setNoteReadPanel(webBrowser, note, null, titleTextField);
		} else {
			this.webBrowser = WebBrowerUtil.setNoteWritePanel(webBrowser, note, null, titleTextField);
		}*/
		this.notePanel.add(this.webBrowser, BorderLayout.CENTER);
		return this.notePanel;
	}

	private void _getWebBrowser() {
		this.webBrowser = new JWebBrowser();
		this.webBrowser.setName("webReader");
		this.webBrowser.setBarsVisible(false);
		this.webBrowser.setStatusBarVisible(false);
		this.webBrowser.setBorder(BorderFactory.createEmptyBorder());
		//this.webBrowser.addWebBrowserListener(new JNWebBrowserListener(note, null, titleTextField));
	}

	/**
	 * 编辑器meta Panel,包含工具栏
	 * 
	 * @return
	 */

	/**
	 * 编辑器工具栏
	 * 
	 * @return
	 */
}
