package LocalPanel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Listener.JNWebBrowserListener;
import YuNote.Note;
import YuNote.Option;
import YuNote.OptionDao;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;



public class JNEditor {
	private JPanel editorPanel;
	private Note note;
	private JNAttachmentPanel attachment;
	private JTextField titleTextField;
	
	public JNEditor(Note note,JTextField titleTextField) {
		this.note = note;
		this.titleTextField = titleTextField;
		editorPanel = new JPanel(new BorderLayout());
		editorPanel.setName("editorPanel");
		JComponent editor = null;

		// 附件面板
		this.attachment = new JNAttachmentPanel(note);
		JPanel attachmentPanel = this.attachment.getAttachmentPanel();
		editorPanel.add(attachmentPanel, BorderLayout.SOUTH);

		Option o = OptionDao.get();
		int editorType = o.getEditorType();
		if (JNConstans.EDITORY_ADVANCED == editorType) {
			editor = getAdvancedEditor();
		} else if (JNConstans.EDITORY_MARKDOWN == editorType) {
			editor = getMarkdownEditor();
		} else {
			editor = getSimpleEditor();
		}

		editorPanel.add(editor, BorderLayout.CENTER);
	}

	public JComponent getMarkdownEditor() {

		final JWebBrowser webBrowser = new JWebBrowser();
		webBrowser.setBarsVisible(false);
		webBrowser.setStatusBarVisible(false);
		webBrowser.setBorder(BorderFactory.createEmptyBorder());
		webBrowser.addWebBrowserListener(new JNWebBrowserListener(note,
				this.attachment,this.titleTextField));
		try {
			int categoryId = JNConstans.CUR_SELECT_CATEGORY_ID;
			if (categoryId < 0) {
				categoryId = 1;
			}
			String pagedownPath = String.class.getResource(
					"/resources/markdown").getPath();
			pagedownPath = pagedownPath.substring(1);
			webBrowser.navigate(pagedownPath + "/index.html");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return webBrowser;
	}

	public JComponent getSimpleEditor() {

		final JWebBrowser webBrowser = new JWebBrowser();
		webBrowser.setBarsVisible(false);
		webBrowser.setStatusBarVisible(false);
		webBrowser.setBorder(BorderFactory.createEmptyBorder());
		webBrowser.addWebBrowserListener(new JNWebBrowserListener(note,
				this.attachment,this.titleTextField));
		try {
			int categoryId = JNConstans.CUR_SELECT_CATEGORY_ID;
			if (categoryId < 0) {
				categoryId = 1;
			}
			String pagedownPath = String.class.getResource(
					"/resources/wysihtml5").getPath();
			pagedownPath = pagedownPath.substring(1);
			webBrowser.navigate(pagedownPath + "/index.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webBrowser;
	}

	public JComponent getAdvancedEditor() {

		final JWebBrowser webBrowser = new JWebBrowser();
		webBrowser.setBarsVisible(false);
		webBrowser.setStatusBarVisible(false);
		webBrowser.setBorder(BorderFactory.createEmptyBorder());
		webBrowser.addWebBrowserListener(new JNWebBrowserListener(note,
				attachment,this.titleTextField));
		
		try {
			int categoryId = JNConstans.CUR_SELECT_CATEGORY_ID;
			if (categoryId < 0) {
				categoryId = 1;
			}
			String pagedownPath = String.class
					.getResource("/resources/ueditor").getPath();
			pagedownPath = pagedownPath.substring(1);
			webBrowser.navigate(pagedownPath + "/index.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webBrowser;
	}

	public JPanel getEditorPanel() {
		return editorPanel;
	}

}
