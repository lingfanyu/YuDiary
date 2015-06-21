package Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import javax.swing.text.StyledDocument;

import LocalPanel.JNConstans;
import Main.JNUtil;
import YuNote.Attachment;
import YuNote.AttachmentDao;
import YuNote.CategoryDao;
import YuNote.Note;
import YuNote.NoteDao;

import jodd.io.FileUtil;
import jodd.util.StringUtil;
import jodd.util.SystemUtil;



/**
 * note保存
 * 
 * @author zhaopeng
 * 
 */
public class JNNoteSaveService {

	public static void saveNote(Note note, List<Attachment> attList) throws IOException {
		_fetchNoteImage(note);
		_saveNote(note);
//		_saveAttachment(note, attList);//保存附件
	}

	/**
	 * 下载远程图片到本地
	 * 
	 * @param note
	 */
	private static void _fetchNoteImage(Note note) {
		/*try {
			String workFolder = SystemUtil.getWorkingFolder();
			workFolder = StringUtil.replace(workFolder, "\\", "/");
			String catetoryPath = CategoryDao.getCategoryPath(note.getCategoryId());
			String path = workFolder + "/data/"+catetoryPath+note.getUuid()+"/images/";
			FileUtil.mkdirs(path);
			NetUtil.fetchImagesSrc(note.getContent());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
*/
	}

	/**
	 * 保存日志
	 * 
	 * @param note
	 * @throws IOException
	 */
	private static void _saveNote(Note note) throws IOException {

		JNUtil.checkDataDir();

		// 保存日志到文件
		String fileName = note.getUuid() + ".jn";
		String categoryPath = CategoryDao.getCategoryPath(note.getCategoryId());
		String dataPath = SystemUtil.getWorkingFolder() + JNConstans.DATA_FILE_DIR + categoryPath;
		FileUtil.mkdirs(dataPath);
		String filePath = dataPath + fileName;
		
		System.out.println("==Filepath:"+filePath);
		
		//FileUtil.writeString(filePath, note.getContent(), "utf-8");
		
		ObjectOutputStream output=null;
    	try {	
    		StyledDocument doc = note.getdoc();
    		output = new ObjectOutputStream(new FileOutputStream(new File(filePath)));
    		
    		output.writeObject(doc);
    		output.flush();
    	} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (output != null) {
                try {
                	output.close();
                } catch (IOException e) {
					e.printStackTrace();
				}
            }
		}
		
		
		// 保存日志到数据库
		Note noteTemp = NoteDao.getNoteByUuid(note.getUuid());
		if (noteTemp == null) {
			note.setCreateDate(new Date());
			NoteDao.saveNote(note);
		} else {
			note.setId(noteTemp.getId());
			note.setChangeDate(new Date());
			NoteDao.updateNote(note);
		}

	}

	/**
	 * 保存附件
	 * 
	 * @param note
	 * @param attList
	 * @throws IOException
	 */
	private static void _saveAttachment(Note note, List<Attachment> attList) throws IOException {
		// 添加附件
		String categoryPath = CategoryDao.getCategoryPath(note.getCategoryId());
		String dataPath = SystemUtil.getWorkingFolder() + JNConstans.DATA_FILE_DIR + categoryPath;
		String attachmentPath = dataPath + note.getUuid() + "/";
		FileUtil.mkdirs(attachmentPath);

		for (int i = 0; i < attList.size(); i++) {
			Attachment attachment = attList.get(i);
			attachment.setNoteUuid(note.getUuid());
			attachment.setPath(attachmentPath);
			String fromPath = attachment.getSourcePath();
			String toPath = attachment.getPath();
			FileUtil.copy(fromPath, toPath);
			attList.set(i, attachment);
		}
		// 保存附件
		if (attList.size() > 0) {
			AttachmentDao.save(attList);
		}
	}
}
