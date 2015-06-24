package YuNote;

import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.text.StyledDocument;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Next;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

import TextArea.TextStyle;
import TextArea.Window;

@Table("jn_note")
public class Note {
	
	@Column
	@Id(auto = false)
	@Next(@SQL("SELECT MAX(id) FROM JN_NOTE"))
	private Long id;
	@Column
	private String uuid;
	@Column
	private String title;
	
	@Column
	private String content;
	//private StyledDocument content;
	//@Column
	private StyledDocument doc;
	private TextStyle style;
	
	public TextStyle getstyle() {
		return style;
	}

	public void setstyle(TextStyle style) {
		this.style = style;
	}

	@Column
	@Default("1")
	private Integer categoryId; // 默认1作为默认分类
	@Column
	private Date createDate;
	@Column
	private Date changeDate;

	private Window window ;//= new Window();
	
	JTextArea ta;
	
	
	@Column
	@Default("html")
	private String editorType;// 默认 html格式
	
	public JTextArea getJTextArea() {
		return ta;
	}

	public void setJTextArea(JTextArea ta) {
		this.ta = ta;
	}
	
	
	public Window getwindow() {
		return window;
	}
	
	
	
	public void setwindow(Window swindow) {
		this.window = swindow;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public StyledDocument getdoc() {
		return doc;
	}
	public void setdoc(StyledDocument doc) {
		this.doc = doc;
	}
	
	
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getEditorType() {
		return editorType;
	}

	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", uuid=" + uuid + ", title=" + title + ", content=" + content + ", categoryId="
				+ categoryId + ", createDate=" + createDate + ", changeDate=" + changeDate + ", editorType="
				+ editorType + "]";
	}

}
