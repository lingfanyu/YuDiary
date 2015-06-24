package LocalWork;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Listener.JNNoteTableListener;
import Listener.JNTabListener;



/**
 *
 */
public class CloseTabPanel extends JPanel {
	private JLabel title;
	private CloseButton closebutton;
	private final JTabbedPane pane;

	public CloseTabPanel(String s, JTabbedPane pane) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		title = new JLabel(s);
		this.pane = pane;
		closebutton = new CloseButton();
		add(title);
		add(closebutton);
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setOpaque(false);
	}

	private class CloseButton extends JButton {
		private ImageIcon icon;

		public CloseButton() {
			icon = new ImageIcon("Icons/tab_close.png");
			setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
			setIcon(icon);
			setBorder(null);
			setBorderPainted(false);
			setFocusPainted(false);
			// setPressedIcon(new
			// ImageIcon(getClass().getResource("/Image/Button/close_pressed.png")));
			// setRolloverIcon(new
			// ImageIcon(getClass().getResource("/Image/Button/close_rollover.png")));
			addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = pane.indexOfTabComponent(CloseTabPanel.this);
					pane.remove(index);
					JNTabListener.removeTab(index);
					if (pane.getTabCount() == 0) {
//						pane.addTab("blank", null);
					}
					new JNNoteTableListener().getNoteTable();
				}
			});
		}
	}

}
