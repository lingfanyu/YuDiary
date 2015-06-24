package LocalPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import LocalUI.GUIUtil;



public class JNAboutPanel extends JPanel {
	private JFrame frame = null;

	public JNAboutPanel(GraphicsConfiguration gc) {
		_initFrame(gc);

	}

	private void _initFrame(GraphicsConfiguration gc) {
		frame = new JFrame(gc);
		setLayout(new BorderLayout());
		
		frame.setTitle("YuDiary-关于");

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(new JLabel("  "), BorderLayout.WEST);
		JLabel aboutLable = new JLabel();
		aboutLable.setBounds(130,120,100,30);
		aboutLable.setText("<html>Yuote<br />作者:group24 <br /></html>");
		mainPanel.add(aboutLable, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();

		JButton cancelBtn = new JButton("关闭");

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});


		btnPanel.add(cancelBtn);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(mainPanel);
		frame.setPreferredSize(new Dimension(180, 160));
		frame.pack();
		GUIUtil.setFrameCenter(frame);
		frame.setVisible(true);

	}

	private Component getAboutPanel() {
		JLabel label = new JLabel("<html><center><font color=red size=3>RED</font></center></html>");
		return label;
	}


}
