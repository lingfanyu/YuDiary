package LocalPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import LocalUI.ComponentUtil;
import LocalUI.GUIUtil;
import TextArea.Menubar;
import TextArea.Window;



public class JNMainPanel extends JPanel {
	private JFrame frame = null;
	//private Window window = new Window();
	
	
	// 默认窗体大小
	private static final int DEFAULT_PANEL_WIDTH = 1000;
	private static final int DEFAULT_PANE_HEIGHT = 700;

	public JNMainPanel(GraphicsConfiguration gc) throws UnknownHostException, IOException {
		_initFrame(gc);
		_initMenu();

	}

	/**
	 * 初始化窗体设置
	 * 
	 * @param gc
	 */
	private void _initFrame(GraphicsConfiguration gc) {	
		frame = new JFrame(gc);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(100, 100));
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 设置为最大化
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANE_HEIGHT));
		frame.setTitle("YuDiary");
		
		ComponentUtil.setComponent("mainpanel", this);
		
		frame.getContentPane().add(this, BorderLayout.CENTER);
		frame.pack();
		GUIUtil.setFrameCenter(frame);
		JNStatusPanel.setStatusMessage("请点击‘新建日记’o(*￣▽￣*)ブ开启您的记录生活");
		new JNTray(frame);
	}

	private void _initMenu() throws UnknownHostException, IOException {
		// menu pan
		// JNMenuBar menuBar = new JNMenuBar(this);
		// frame.setJMenuBar(menuBar.getMenuBar());
		// menubar 
		//Menubar menubar = new Menubar(window);
		//frame.setJMenuBar(menubar);
		// toolbar 
		JNToolBar toolBarPanel = new JNToolBar();
		add(toolBarPanel.getToolbar(), BorderLayout.NORTH);

		// // container panel
		JNContainerPanel containerPanel = new JNContainerPanel(this);
		add(containerPanel.getContainerPanel(), BorderLayout.CENTER);

		// status panel
		JNStatusPanel statusPanel = new JNStatusPanel(this);
		add(statusPanel.getStatusPanel(), BorderLayout.SOUTH);
		_showFrame();// 显示窗体
		containerPanel.setDividerLocation();

	}

	/**
	 * 显示窗体
	 */
	private void _showFrame() {
		getFrame().setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

}
