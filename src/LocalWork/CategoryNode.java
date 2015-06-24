package LocalWork;

import java.util.ArrayList;
import java.util.Collection;

public class CategoryNode {
	private Integer id;
	private String categoryName;
	private Integer parentId;
	private Collection<CategoryNode> children = new ArrayList<CategoryNode>();

	public CategoryNode() {
	}

	public CategoryNode(Integer id, String categoryName, Integer parentId) {
		super();
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

	public Collection<CategoryNode> getChildren() {
		return children;
	}

	public void setChildren(Collection<CategoryNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "CategoryNode [id=" + id + ", categoryName=" + categoryName + ", parentId=" + parentId + "]";
	}

}
