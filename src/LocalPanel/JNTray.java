package LocalPanel;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class JNTray {
	private Image icon;// 托盘图标
	private TrayIcon trayIcon;
	private SystemTray systemTray;// 系统托盘
	private PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
	private MenuItem exit = new MenuItem("exit");
	private MenuItem show = new MenuItem("show");

	private JFrame mainWindow;

	public JNTray(final JFrame mainWindow) {
		this.mainWindow = mainWindow;
		// 托盘图标部分结束
		// icon初始化
		//icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon/menu.png"));// 托盘图标显示的图片
		icon = Toolkit.getDefaultToolkit().createImage("icon/menu.png");//("icon/menu.png");// 托盘图标显示的图片

		if (SystemTray.isSupported()) {
			systemTray = SystemTray.getSystemTray();// 获得系统托盘的实例
			trayIcon = new TrayIcon(icon, "YuDiary", pop);
			// wasw100
			pop.add(show);
			pop.add(exit);

			try {
				systemTray.add(trayIcon); // 设置托盘的图标
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			mainWindow.addWindowListener(new WindowAdapter() {
				public void windowIconified(WindowEvent e) {
					mainWindow.dispose();// 窗口最小化时dispose该窗口
				}
			});

			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1
							&& e.getButton() != MouseEvent.BUTTON3) {// 左击击托盘窗口再现，如果是双击就是e.getClickCount()
																		// == 2
						mainWindow.setVisible(true);
						mainWindow.setExtendedState(JFrame.NORMAL);// 设置此 frame
																	// 的状态。
					}
				}
			});

			show.addActionListener(new ActionListener() { // 点击"显示窗口"菜单后将窗口显示出来

				public void actionPerformed(ActionEvent e) {
					// systemTray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
					mainWindow.setVisible(true); // 显示窗口
					mainWindow.setExtendedState(JFrame.NORMAL);
				}
			});
			exit.addActionListener(new ActionListener() { // 点击“退出演示”菜单后推出程序

				public void actionPerformed(ActionEvent e) {
					// 这里可以写执行退出时执行的操作
					System.exit(0); // 退出程序
				}
			});
		}// 托盘图标部分结束
	}
}
