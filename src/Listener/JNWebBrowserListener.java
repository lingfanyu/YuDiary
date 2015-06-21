package Listener;

import java.awt.Image;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Database.JNNoteSaveService;
import LocalPanel.JNAttachmentPanel;
import LocalPanel.JNConstans;
import LocalUI.ClipboradUtil;
import LocalUI.WebBrowerUtil;
import YuNote.Attachment;
import YuNote.CategoryDao;
import YuNote.Note;

import jodd.io.FileUtil;
import jodd.util.StringUtil;
import jodd.util.SystemUtil;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.internal.INativeWebBrowser;



public class JNWebBrowserListener extends WebBrowserAdapter implements WebBrowserListener {
	private Note note;
	private JNAttachmentPanel attachment;
	private JTextField titleTextField;

	public JNWebBrowserListener(Note note, JNAttachmentPanel attachment,JTextField titleTextField) {
		this.titleTextField = titleTextField;
		this.note = note;
		this.attachment = attachment;
	}

	@Override
	public void commandReceived(WebBrowserCommandEvent e) {
		String command = e.getCommand();
		if ("paste".equals(command)) {
			INativeWebBrowser web = (INativeWebBrowser) e.getWebBrowser().getNativeComponent();

			try {
				Object o = ClipboradUtil.getClipboard();
				if (o instanceof String) {
					web.executeJavascript("paste(1,'')");
				} else if (o instanceof Image) {
					String fileName = System.currentTimeMillis() + ".jpg";
					String workFolder = SystemUtil.getWorkingFolder();
					workFolder = StringUtil.replace(workFolder, "\\", "/");
					
					String catetoryPath = CategoryDao.getCategoryPath(note.getCategoryId());
					String path = workFolder + "/data/"+catetoryPath+note.getUuid()+"/attachment/";
					FileUtil.mkdirs(path);
					String filePath = path + fileName;
					ImageIO.write((RenderedImage) o, "jpg", new File(filePath));
					String imgPath = workFolder+"/data/"+catetoryPath+note.getUuid()+"/attachment/" + fileName;
					
					String imageHtml = "<img src=\"" + imgPath + "\" />";
					imageHtml = jodd.util.HtmlEncoder.strict(imageHtml);
					web.executeJavascript("paste(2,\""+imageHtml+"\")");
				} else if (o instanceof List) {

				}
			} catch (UnsupportedFlavorException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		} else if ("save".equals(command)) {// 保存日志
			try {
				this.note = this._buildNote(e.getParameters());
//				JNNoteSaveService.saveNote(note, this._getAttList());
				JNNoteSaveService.saveNote(note, null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			new JNNoteTableListener().getNoteTable();// 更新选中的日志列表

		} else if ("attachment".equals(command)) {
			attachment.getAttachmentPanel().setVisible(true);

			// 选择文件到附件
			// JFileChooser jfc = new JFileChooser();
			// jfc.setFileSelectionMode(0);// 只能选择文件
			// int state = jfc.showOpenDialog(null);
			// if (state == 1) {
			// return;
			// } else {
			// File f = jfc.getSelectedFile();
			// DefaultListModel model = (DefaultListModel)
			// attachment.getList().getModel();
			// model.addElement(f);
			//
			// }

		} else if ("savAndRead".equals(command)) {//保存并阅读
			
			e.getWebBrowser().executeJavascript("save();");
			e.getWebBrowser().setHTMLContent("");//清空内容以便快速执行save()方法
			this.note = this._buildNote(e.getParameters());			
			WebBrowerUtil.setNoteReadPanel(e.getWebBrowser(), note, null, titleTextField);
		} else if ("edit".equals(command)) {//保存并阅读
			WebBrowerUtil.setNoteWritePanel(e.getWebBrowser(), note, null, titleTextField);
		}
	}

	/**
	 * 组装日志
	 * 
	 * @param parameters
	 * @return
	 */
	private Note _buildNote(Object[] parameters) {
		Note note = new Note();
		String uuid = (String) parameters[0];
		String content = (String) parameters[1];

		Integer categoryId = Integer.parseInt((String) parameters[2]);
		String editorType = (String) parameters[3];
		String title = (String) parameters[4];
		note.setUuid(uuid);
		note.setCategoryId(categoryId);
		//note.setContent(content);
		note.setTitle(title);
		return note;
	}

	/**
	 * 获取附件列表
	 * 
	 * @return
	 */
	private List<Attachment> _getAttList() {
		DefaultListModel model = (DefaultListModel) attachment.getList().getModel();
		List<Attachment> attList = new ArrayList<Attachment>();
		for (int i = 0; i < model.getSize(); i++) {
			File o = (File) model.get(i);
			String fromPath = o.getAbsolutePath();
			Attachment att = new Attachment();
			att.setNoteUuid(this.note.getUuid());
			att.setSourcePath(fromPath);
			att.setAttachmentName(o.getName());
			attList.add(att);
		}
		return attList;
	}

	/**
	 * 嵌入的浏览器页面加载完成后执行编辑器初始化
	 */
	@Override
	public void loadingProgressChanged(WebBrowserEvent e) {
		super.loadingProgressChanged(e);
		INativeWebBrowser nativeWebBrowser = (INativeWebBrowser) e.getWebBrowser().getNativeComponent();
		int loadingProgress = nativeWebBrowser.isNativePeerInitialized() ? nativeWebBrowser.getLoadingProgress() : 100;
		if (loadingProgress == 100) {
			new EditorThread(e.getWebBrowser()).start();
		}
	}

	class EditorThread extends Thread {
		private JWebBrowser webBrowser;

		public EditorThread(JWebBrowser webBrowser) {
			this.webBrowser = webBrowser;
		}

		@Override
		public void run() {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String uuid = note.getUuid();
					String title = note.getTitle();
					//String content = note.getContent();
					Integer categoryId = note.getCategoryId();
					if (categoryId == null || categoryId==-1) {
						categoryId = 1;
					}
					
					String categoryPath = CategoryDao.getCategoryPath(note.getCategoryId());
					
					String notePath = JNConstans.DATA_FILE_DIR + categoryPath+note.getUuid()+".jn";
					//if(StringUtil.isBlank(content)){
						notePath = "";
					//}
					
					//content = HtmlEncoder.strict(content);
					String editorType = note.getEditorType();
					//String js = "init_content('" + uuid + "', '" + title + "', '" + content + "', '" + categoryId
					//		+ "', '" + editorType + "');";

					//webBrowser.executeJavascript(js);
				}
			});
		}
	}
}
