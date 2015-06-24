package YuNote;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Next;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

@Table("jn_option")
public class Option {

	@Column
	@Id(auto = false)
	@Next(@SQL("SELECT MAX(id) FROM jn_option"))
	private Integer id;
	@Column
	private int editorType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getEditorType() {
		return editorType;
	}

	public void setEditorType(int editorType) {
		this.editorType = editorType;
	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", editorType=" + editorType + "]";
	}

}
