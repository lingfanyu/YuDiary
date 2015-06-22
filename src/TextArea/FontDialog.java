package TextArea;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**************************************************************** 
 * 
 *       类FontDialog用来设置字体 
 * 
 ********************************************************/

public class FontDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private String font[];

	private JLabel fontLabel, fontTypeLabel, fontSizeLabel, fontShowLabel;

	private JList fontList, fontTypeList, fontSizeList;

	private JTextField fontField, fontTypeField, fontSizeField, fontShowField;

	private JScrollPane fontScroll, fontTypeScroll, fontSizeScroll;

	public static JButton sureButton, cancelButton;

	public static Font myfont;

	private Container container = getContentPane();

	public static boolean sure = false;
	
    public static String font_name = "宋体"; 
    
    public static int font_size = 20, font_type = Font.PLAIN; 
    
    public static boolean bold,italic;

	public FontDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		container.setLayout(null);
		setLocation(frame.getX() + 50, frame.getY() + 150);
		setResizable(false);
		setSize(420, 300);
		fontLabel = new JLabel("字体(F):");
		fontField = new JTextField(font_name);
		GraphicsEnvironment g = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		font = g.getAvailableFontFamilyNames();//获得系统支持的所有字体列表 
		fontList = new JList(font);
		fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设为单选模式 
		fontScroll = new JScrollPane(fontList);
		/////////////////////////////////////////////////////////////////////// 
		fontTypeLabel = new JLabel("字形(Y):");
		String[] data = { "常规", "斜体", "粗体", "粗斜体" };
		fontTypeList = new JList(data);
		if (font_type == 0)
			fontTypeField = new JTextField("常规");
		else if (font_type == 1)
			fontTypeField = new JTextField("粗体");
		else if (font_type == 2)
			fontTypeField = new JTextField("斜体");
		else
			fontTypeField = new JTextField("粗斜体");
		fontTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设为单选模式 
		fontTypeScroll = new JScrollPane(fontTypeList);
		/////////////////////////////////////////////////////////////// 
		fontSizeLabel = new JLabel("大小(S):");
		String size[] = new String[100];
		for (int i = 10; i <= 100; i++) {
			size[i - 10] = String.valueOf(i);
		}
		fontSizeList = new JList(size);
		fontSizeField = new JTextField("" + font_size);
		fontSizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 设为单选模式 
		fontSizeScroll = new JScrollPane(fontSizeList);
		///////////////////////////////////////////////////////////////// 
		sureButton = new JButton("确定");
		cancelButton = new JButton("取消");
		fontShowLabel = new JLabel("示例");
		fontShowField = new JTextField("字体AaBbYy");
		Border inside = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		fontShowField.setBorder(BorderFactory.createTitledBorder(inside));
		fontShowField.setEditable(false);
		fontShowField.setHorizontalAlignment(JTextField.CENTER);
		fontShowField.setFont(myfont);
		fontLabel.setBounds(10, 5, 162, 20);
		fontField.setBounds(10, 25, 162, 20);
		fontScroll.setBounds(10, 45, 162, 140);
		fontTypeLabel.setBounds(177, 5, 100, 20);
		fontTypeField.setBounds(177, 25, 100, 20);
		fontTypeScroll.setBounds(177, 45, 100, 140);
		fontSizeLabel.setBounds(282, 5, 50, 20);
		fontSizeField.setBounds(282, 25, 50, 20);
		fontSizeScroll.setBounds(282, 45, 50, 140);
		sureButton.setBounds(337, 25, 60, 20);
		cancelButton.setBounds(337, 50, 60, 20);
		fontShowLabel.setBounds(177, 195, 100, 20);
		fontShowField.setBounds(177, 215, 200, 45);
		///////////////////////////////////////////////////////////// 
		container.add(fontLabel);
		container.add(fontField);
		container.add(fontScroll);
		container.add(fontTypeLabel);
		container.add(fontTypeField);
		container.add(fontTypeScroll);
		container.add(fontSizeLabel);
		container.add(fontSizeField);
		container.add(fontSizeScroll);
		container.add(sureButton);
		container.add(cancelButton);
		container.add(fontShowLabel);
		container.add(fontShowField);
		//////////////////////////////////////////////////////////////// 
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mouseClick(e);
			}
		};
		fontList.addMouseListener(mouseListener);
		fontTypeList.addMouseListener(mouseListener);
		fontSizeList.addMouseListener(mouseListener);
		sureButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setVisible(true);
	}

	public void mouseClick(MouseEvent e) {
		if (e.getSource() == fontList) {
			font_name = fontList.getSelectedValue().toString();
			fontField.setText(font_name);
		} else if (e.getSource() == fontTypeList)
			fontTypeField.setText(fontTypeList.getSelectedValue().toString());
		else if (e.getSource() == fontSizeList) {
			fontSizeField.setText(fontSizeList.getSelectedValue().toString());
			font_size = Integer.parseInt(fontSizeField.getText());
		}
		show_Font();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sureButton) {
			sure = true;
			setVisible(false);
		} else if (e.getSource() == cancelButton) {
			sure = false;
			setVisible(false);
		}
	}

	public void show_Font() {
		if (fontTypeField.getText().equals("常规")) {
			font_type = 0;
			bold = false;
			italic = false;
		} else if (fontTypeField.getText().equals("粗体")) {
			font_type = 1;
			bold = true;
			italic = false;
		} else if (fontTypeField.getText().equals("斜体")) {
			font_type = 2;
			bold = false;
			italic = true;
		} else if (fontTypeField.getText().equals("粗斜体")){
			font_type = 3;
			bold = true;
			italic = true;
		}
		myfont = new Font(font_name, font_type, font_size);
		fontShowField.setFont(myfont);
	}
}
