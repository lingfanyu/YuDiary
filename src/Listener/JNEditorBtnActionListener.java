package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import LocalUI.WebBrowerUtil;
import YuNote.Note;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;


public class JNEditorBtnActionListener implements ActionListener {
	private Note note;
	private JTextField titleTextField;
	private JWebBrowser webBrowser;

	public JNEditorBtnActionListener(Note note, JWebBrowser webBrowser, JTextField titleTextField) {
		this.note = note;
		this.titleTextField = titleTextField;
		this.webBrowser = webBrowser;
	}

	public void actionPerformed(ActionEvent e) {
		JButton editBtn = (JButton) e.getSource();
		String editBtnText = editBtn.getText();

	/*	if ("编辑".equals(editBtnText)) {
			editBtn.setText("保存并阅读");
			WebBrowerUtil.setNoteWritePanel(webBrowser, note, null, titleTextField);
		} else {
			webBrowser.executeJavascript("save();");
			webBrowser.setHTMLContent("");//清空内容以便快速执行save()方法
			editBtn.setText("编辑");
			WebBrowerUtil.setNoteReadPanel(webBrowser, note, null, titleTextField);
		}
*/
	}

}
