package LocalPanel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import LocalUI.ComponentUtil;



public class JNRightTabPanel {
	private JPanel rightTabPanel;
	
	public JNRightTabPanel() {
		rightTabPanel = new JPanel();
		rightTabPanel.setBorder(BorderFactory.createEmptyBorder());
		rightTabPanel.setLayout(new BorderLayout());
		rightTabPanel.add(createContent(), BorderLayout.CENTER);
	}

	public JPanel getRightTabPanel() {
		return rightTabPanel;
	}

	public JComponent createContent() {
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		ComponentUtil.setComponent("tabbedPane", tabbedPane);
		//tabbedPane.addTab("Startup page", new JNEditor("").getEditorPanel());
		return tabbedPane;
	}
}
