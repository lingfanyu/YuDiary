package YuNote;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Next;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

@Table("jn_attachment")
public class Attachment {
	@Column
	@Id(auto = false)
	@Next(@SQL("SELECT MAX(id) FROM JN_ATTACHMENT"))
	private Long id;
	@Column
	private String attachmentName;
	@Column
	private String noteUuid;
	@Column
	private String sourcePath;// 源文件路径
	@Column
	private String path;// 数据文件保存路径

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getNoteUuid() {
		return noteUuid;
	}

	public void setNoteUuid(String noteUuid) {
		this.noteUuid = noteUuid;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", attachmentName=" + attachmentName
				+ ", noteUuid=" + noteUuid + ", sourcePath=" + sourcePath
				+ ", path=" + path + "]";
	}

}
