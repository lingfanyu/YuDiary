package LocalPanel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class JNContainerPanel {
	private JPanel containerPanel = null;
	private JSplitPane mainJsplit = null;
	private JSplitPane subJsplit = null;

	public JNContainerPanel(JNMainPanel jnMainPanel) {

		containerPanel = new JPanel();
		containerPanel.setLayout(new BorderLayout());
		containerPanel.setBorder(BorderFactory.createEmptyBorder());
		
		mainJsplit = new JSplitPane();// 包含 右侧tab文件编辑 和 左侧JSplitPanel
		subJsplit = new JSplitPane();// 包含 左侧分类树形 和 中心文件列表
		
		//设置为无边框
		mainJsplit.setBorder(BorderFactory.createEmptyBorder());
		subJsplit.setBorder(BorderFactory.createEmptyBorder());
		
		subJsplit.setLeftComponent(new JNLeftTreePanel().getLeftTreePanel());// 左侧分类树形
		subJsplit.setRightComponent(new JNCenterListPanel().getCenterListPanel());// 中心文件列表
		mainJsplit.setRightComponent(new JNRightTabPanel().getRightTabPanel());// 右侧tab文件编辑
		mainJsplit.setLeftComponent(subJsplit);

		
		containerPanel.add(mainJsplit, BorderLayout.CENTER);
	}

	public JPanel getContainerPanel() {
		return containerPanel;
	}

	public void setDividerLocation(){
		mainJsplit.setDividerLocation(0.4);
		subJsplit.setDividerLocation(0.25);
	}
	
	
}
