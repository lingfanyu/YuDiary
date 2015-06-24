package LocalUI;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GUIUtil {

	/**
	 * 设置JFrame到屏幕中心
	 * 
	 * @param frame
	 */
	public static void setFrameCenter(JFrame frame) {
		Rectangle screenRect = frame.getGraphicsConfiguration().getBounds();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
				frame.getGraphicsConfiguration());

		// Make sure we don't place the demo off the screen.
		int centerWidth = screenRect.width < frame.getSize().width ? screenRect.x
				: screenRect.x + screenRect.width / 2 - frame.getSize().width
						/ 2;
		int centerHeight = screenRect.height < frame.getSize().height ? screenRect.y
				: screenRect.y + screenRect.height / 2 - frame.getSize().height
						/ 2;

		centerHeight = centerHeight < screenInsets.top ? screenInsets.top
				: centerHeight;

		frame.setLocation(centerWidth, centerHeight);
		
	}
	/**
	 * 获取内容
	 * @return
	 */
	public static String getResourceString(String key){
		return "文件";
	}
	
}
