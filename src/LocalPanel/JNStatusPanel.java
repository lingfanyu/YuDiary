package LocalPanel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jb2011.lnf.beautyeye.widget.N9ComponentFactory;

public class JNStatusPanel {
	private JPanel statusPanel;
	private static JLabel statusField = new JLabel();

	public JNStatusPanel(JNMainPanel jnMainPanel) {
		statusPanel = new JPanel(new BorderLayout());
		JLabel statusLabel = N9ComponentFactory.createLabel_style1("Tip");
		statusPanel.add(statusLabel, BorderLayout.WEST);
		statusPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));
		statusField.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		statusPanel.add(statusField, BorderLayout.CENTER);
	}

	public JPanel getStatusPanel() {
		return statusPanel;
	}

	public static void setStatusMessage(String message) {
		statusField.setText(message);
	}

}
