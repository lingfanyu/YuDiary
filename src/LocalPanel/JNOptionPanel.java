package LocalPanel;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.border.TitledBorder;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import LocalUI.GUIUtil;
import YuNote.OptionDao;



public class JNOptionPanel extends JPanel {
	private JFrame frame = null;
	private JRadioButton simpleEditorRadioButton = new JRadioButton("简单html编辑器");
	private JRadioButton advancedEditorRadioButton = new JRadioButton("高级html编辑器");
	private JRadioButton markdownEditorRadioButton = new JRadioButton("markdown编辑器");

	// 默认窗体大小
	private static final int DEFAULT_PANEL_WIDTH = 1000;
	private static final int DEFAULT_PANE_HEIGHT = 700;

	public JNOptionPanel(GraphicsConfiguration gc) {
		_initFrame(gc);

	}

	private void _initFrame(GraphicsConfiguration gc) {
		frame = new JFrame(gc);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANE_HEIGHT));
		frame.setTitle("jnote");

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel optionPanel = new JPanel(new GridLayout(2, 1));

		optionPanel.add(this.getEditorOptionPanel());
		optionPanel.add(this.getDatapathOptionPanel());

		mainPanel.add(optionPanel, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();

		JButton cancelBtn = new JButton("取消");

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		JButton confirmBtn = new JButton("确定");
		confirmBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		confirmBtn.setForeground(Color.white);

		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionDao d = new OptionDao();
				if (simpleEditorRadioButton.isSelected()) {
					d.update(JNConstans.EDITORY_SIMPLE);
				} else if (markdownEditorRadioButton.isSelected()) {
					d.update(JNConstans.EDITORY_MARKDOWN);
				} else {
					d.update(JNConstans.EDITORY_ADVANCED);
				}
				frame.dispose();
			}
		});

		btnPanel.add(cancelBtn);
		btnPanel.add(confirmBtn);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(mainPanel);
		frame.pack();
		GUIUtil.setFrameCenter(frame);

		frame.setVisible(true);

	}

	public JComponent getEditorOptionPanel() {
		JPanel editorPanel = new JPanel(new BorderLayout());

		editorPanel.setBorder(new TitledBorder("编辑器选项"));
		editorPanel.setLayout(new BorderLayout());

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(simpleEditorRadioButton);
		buttonGroup.add(advancedEditorRadioButton);
		buttonGroup.add(markdownEditorRadioButton);

		editorPanel.add(simpleEditorRadioButton, BorderLayout.WEST);
		editorPanel.add(advancedEditorRadioButton, BorderLayout.CENTER);
		editorPanel.add(markdownEditorRadioButton, BorderLayout.EAST);

		OptionDao dao = new OptionDao();
		int editorType = dao.get().getEditorType();
		if (JNConstans.EDITORY_ADVANCED == editorType) {
			advancedEditorRadioButton.setSelected(true);
		} else if (JNConstans.EDITORY_MARKDOWN == editorType) {
			markdownEditorRadioButton.setSelected(true);
		} else {
			simpleEditorRadioButton.setSelected(true);
		}
		return editorPanel;
	}

	public JComponent getDatapathOptionPanel() {
		final JPanel datapathPanel = new JPanel(new BorderLayout());

		datapathPanel.setBorder(new TitledBorder("数据文件夹位置"));
		datapathPanel.setLayout(new BorderLayout());
		final JTextField datapath = new JTextField();
		datapath.setSize(200, 30);
		JButton btnSelect = new JButton("选择");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int intRetVal = fc.showOpenDialog(datapathPanel);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					datapath.setText(fc.getSelectedFile().getPath());
				}

			}
		});
		datapathPanel.add(datapath, BorderLayout.CENTER);
		datapathPanel.add(btnSelect, BorderLayout.EAST);
		datapathPanel.add(new JLabel("*重启后生效"), BorderLayout.SOUTH);
		return datapathPanel;
	}

}
