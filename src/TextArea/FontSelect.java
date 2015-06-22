//字体对话框
package TextArea;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FontSelect extends JDialog {
	/**
	 * 
	 */
	private JTextField textFieldFont;
	private JTextField textFieldStyle;
	private JTextField textFieldSize;
	private JTextArea textAreaSample;
	private JList<String> listFont;
	private JList<String> listStyle;
	private JList<String> listSize;
	private static Window window;

	/**
	 * Launch the application.
	 */
	public static void launch(Window window) {
		FontSelect.window = window;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FontSelect dialog = new FontSelect();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public FontSelect() {
		setResizable(false);
		setBounds(100, 100, 450, 395);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 324, 434, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						changeTextFont(textFieldFont.getText(),
								textFieldStyle.getText(),
								textFieldSize.getText());

						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 434, 2);
			getContentPane().add(scrollPane);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(10, 45, 162, 172);
			getContentPane().add(scrollPane);

			GraphicsEnvironment e = GraphicsEnvironment
					.getLocalGraphicsEnvironment();// 返回本地 GraphicsEnvironment
			String[] fontFamilys = e.getAvailableFontFamilyNames();// 取系统所有字体名称
			listFont = new JList<String>(fontFamilys);
			listFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listFont.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					textFieldFont.setText(listFont.getSelectedValue());
					fontSampleFresh();
				}
			});
			scrollPane.setViewportView(listFont);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(214, 43, 92, 172);
			getContentPane().add(scrollPane);
			{
				String style[] = { "粗体 ", "斜体", "普通" };
				listStyle = new JList<String>(style);
				listStyle.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						textFieldStyle.setText(listStyle.getSelectedValue());
						fontSampleFresh();
					}
				});
				scrollPane.setViewportView(listStyle);
			}
		}
		{
			JLabel lblNewLabel = new JLabel("\u5B57\u4F53");
			lblNewLabel
					.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 15));
			lblNewLabel.setBounds(10, 12, 54, 15);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel label = new JLabel("\u6837\u5F0F");
			label.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 15));
			label.setBounds(191, 12, 54, 15);
			getContentPane().add(label);
		}

		JPanel panel = new JPanel();
		panel.setBounds(32, 252, 376, 62);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		textAreaSample = new JTextArea();
		textAreaSample.setEditable(false);
		textAreaSample.setFont(new Font("黑体", Font.PLAIN, 20));
		panel.add(textAreaSample, BorderLayout.CENTER);
		textAreaSample.setText("FontSample--字体样例");

		JLabel label = new JLabel("\u6837\u4F8B");
		label.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 15));
		label.setBounds(32, 227, 54, 15);
		getContentPane().add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(342, 45, 92, 172);
		getContentPane().add(scrollPane);

		String[] size = new String[80];
		for (int i = 1; i <= 80; i++) {
			size[i - 1] = Integer.valueOf(i).toString();
		}
		listSize = new JList<String>(size);
		listSize.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				textFieldSize.setText((String) listSize.getSelectedValue());
				fontSampleFresh();
			}
		});
		scrollPane.setViewportView(listSize);
		{
			textFieldFont = new JTextField("黑体");
			textFieldFont.setEditable(false);
			textFieldFont.setBounds(63, 14, 92, 21);
			getContentPane().add(textFieldFont);
			textFieldFont.setColumns(10);
		}
		{
			textFieldStyle = new JTextField("普通");
			textFieldStyle.setEditable(false);
			textFieldStyle.setBounds(240, 12, 66, 21);
			getContentPane().add(textFieldStyle);
			textFieldStyle.setColumns(10);
		}
		{
			textFieldSize = new JTextField("20");
			textFieldSize.setEditable(false);
			textFieldSize.setBounds(368, 12, 66, 21);
			getContentPane().add(textFieldSize);
			textFieldSize.setColumns(6);
		}
		{
			JLabel label_1 = new JLabel("\u5B57\u53F7");
			label_1.setFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 15));
			label_1.setBounds(316, 11, 42, 17);
			getContentPane().add(label_1);
		}
	}

	private void fontSampleFresh() {
		int charStyle = Font.BOLD;
		String selected = textFieldStyle.getText();
		if (selected.equals("粗体"))
			charStyle = Font.BOLD;
		if (selected.equals("斜体"))
			charStyle = Font.ITALIC;
		if (selected.equals("普通"))
			charStyle = Font.PLAIN;
		this.textAreaSample.setFont(new Font(textFieldFont.getText(),
				charStyle, Integer.valueOf(textFieldSize.getText())));
	}

	// 改变文本框字体
	public void changeTextFont(String font, String style, String size1) {
		window.textPane.name = font;
		if (style.equals("粗体"))
			window.textPane.type = Font.BOLD;
		else if (style.equals("斜体"))
			window.textPane.type = Font.ITALIC;
		else
			window.textPane.type = Font.PLAIN;
		window.textPane.size = Integer.parseInt(size1);
		window.textPane.setFont(new Font(window.textPane.name, window.textPane.type, window.textPane.size));
	}
}
