package Ground;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JButton;
import javax.swing.JComponent;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import LocalUI.GUIUtil;

public class JNGroundPanel  extends JPanel {
	private JFrame frame = null;
	
	JTextField TF = new JTextField();
	JTextArea TA = new JTextArea();
	
	// 默认窗体大小
	private static final int DEFAULT_PANEL_WIDTH = 700;
	private static final int DEFAULT_PANE_HEIGHT = 700;

	public JNGroundPanel(GraphicsConfiguration gc) {
		_initFrame(gc);

	}

	private void _initFrame(GraphicsConfiguration gc) {
		frame = new JFrame(gc);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANE_HEIGHT));
		frame.setTitle("Ground？？");

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel optionPanel = new JPanel(new GridLayout(2, 0));
		optionPanel.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH/2, DEFAULT_PANE_HEIGHT/4));
		
		optionPanel.add(this.getTestPanel1());
		
		TF.setText("o(*￣▽￣*)ブo(*￣▽￣*)ブo(*￣▽￣*)ブ");
		optionPanel.add(this.getTestPanel2());

		mainPanel.add(optionPanel, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();

		JButton cancelBtn = new JButton("关闭");

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		JButton confirmBtn = new JButton("确定？");
		confirmBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		confirmBtn.setForeground(Color.white);

		btnPanel.add(cancelBtn);
		btnPanel.add(confirmBtn);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(mainPanel);
		frame.pack();
		GUIUtil.setFrameCenter(frame);

		frame.setVisible(true);

	}
	
	
	public JComponent getTestPanel1() {
		final JPanel Turing = new JPanel(new BorderLayout());
		Turing.setSize(750, 550);
		
		Turing.setBorder(new TitledBorder("显示："));
		Turing.setLayout(new BorderLayout());
		
		JScrollPane TuringSP = new JScrollPane();
		TuringSP.setViewportView(TA);
		
		

		TA.setLineWrap(true);
		TA.setEditable(false);
		TA.setText("╮(╯-╰)╭ 	╮(╯-╰)╭	╮(╯-╰)╭");
		
		Turing.add(TuringSP, BorderLayout.CENTER);
		return Turing;
	}
	public JComponent getTestPanel2() {
		final JPanel Turing = new JPanel(new BorderLayout());
		Turing.setSize(750, 550);
		
		Turing.setBorder(new TitledBorder("打字："));
		Turing.setLayout(new BorderLayout());
		
		JScrollPane TuringSP = new JScrollPane();
		TuringSP.setViewportView(TF);

		
		Turing.add(TuringSP, BorderLayout.CENTER);
		return Turing;
	}
	
	
	
}
