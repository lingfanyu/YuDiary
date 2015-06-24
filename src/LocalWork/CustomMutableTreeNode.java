package LocalWork;

import javax.swing.tree.DefaultMutableTreeNode;

public class CustomMutableTreeNode extends DefaultMutableTreeNode {
	private Object cusObject;

	public CustomMutableTreeNode(Object userObject) {
		super(userObject);
	}

	public Object getCusObject() {
		return cusObject;
	}

	public void setCusObject(Object cusObject) {
		this.cusObject = cusObject;
	}

}
