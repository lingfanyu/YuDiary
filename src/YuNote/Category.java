package YuNote;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Next;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

@Table("jn_category")
public class Category {
	@Column
	@Id(auto = false)
	@Next(@SQL("SELECT MAX(id) FROM jn_category"))
	private Integer id;
	@Column
	private String categoryName;
	@Column
	@Default("0")
	private Integer parentId;

	public Category() {
	}

	public Category(Integer id, String categoryName, Integer parentId) {
		this.id = id;
		this.categoryName = categoryName;
		this.parentId = parentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", parentId=" + parentId + "]";
	}

}
