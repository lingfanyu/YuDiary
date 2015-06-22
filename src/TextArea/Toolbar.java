package TextArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import Ground.JNTextArea;

public class Toolbar extends JToolBar {

	private Window window;
	private Text textPane;
	private Menubar menubar;

	// 文件
	private final Button buttonNew = new Button(new ImageIcon("Icons/new.gif"),
			"新建");
	private final Button buttonOpen = new Button(
			new ImageIcon("Icons/open.gif"), "打开");
	private final Button buttonSave = new Button(
			new ImageIcon("Icons/save.gif"), "保存");
	private final Button buttonSaveAs = new Button(new ImageIcon(
			"Icons/saveas.gif"), "另存为");

	// 编辑
	private final Button buttonCut = new Button(new ImageIcon("Icons/cut.gif"),
			"剪切");
	private final Button up = new Button(new ImageIcon("Icons/print.GIF"),
			"准备上传");
	
	private final Button buttonCopy = new Button(
			new ImageIcon("Icons/copy.gif"), "复制");
	private final Button buttonPaste = new Button(new ImageIcon(
			"Icons/paste.gif"), "粘贴");
	private final Button buttonFind = new Button(
			new ImageIcon("Icons/find.png"), "查找");
	private final Button buttonUndo = new Button(
			new ImageIcon("Icons/undo.gif"), "撤销");
	private final Button buttonRedo = new Button(
			new ImageIcon("Icons/redo.gif"), "重做");

	// 插入
	private final Button buttonDate = new Button(
			new ImageIcon("Icons/time.gif"), "日期");

	// 格式
	private final Button buttonFont = new Button(
			new ImageIcon("Icons/font.gif"), "字体");
	private final Button buttonBold = new Button(
			new ImageIcon("Icons/Bold.gif"), "粗体");
	private final Button buttonItalic = new Button(new ImageIcon(
			"Icons/Italic.gif"), "斜体");
	private final Button buttonFontColor = new Button(new ImageIcon(
			"Icons/FgColor.gif"), "字体颜色");
	private final Button buttonBackColor = new Button(new ImageIcon(
			"Icons/BgColor.gif"), "背景颜色");

	Toolbar(Window window) {
		this.window = window;
		this.textPane = window.textPane;
		this.menubar = window.menubar;

		initToolbar();
		initDisIcon();
		initListener();
	}

	// 初始化工具栏
	private void initToolbar() {
		add(buttonNew);
		add(buttonOpen);
		add(buttonSave);
		add(buttonSaveAs);
		add(buttonCut);
		add(up);
		
		add(buttonCopy);
		add(buttonPaste);
		add(buttonFind);
		add(buttonUndo);
		add(buttonRedo);
		add(buttonDate);
		add(buttonFont);
		add(buttonBold);
		add(buttonItalic);
		add(buttonFontColor);
		add(buttonBackColor);
	}

	// 初始化
	private void initDisIcon() {
		buttonUndo.setDisabledIcon(new ImageIcon("Icons/undo1.gif"));
		buttonRedo.setDisabledIcon(new ImageIcon("Icons/redo1.gif"));
		buttonCut.setDisabledIcon(new ImageIcon("Icons/cut1.gif"));
		buttonCopy.setDisabledIcon(new ImageIcon("Icons/copy1.gif"));
		buttonPaste.setDisabledIcon(new ImageIcon("Icons/paste1.gif"));
	}

	// 初始化监听器
	private void initListener() {
		// 文件
		buttonNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menubar.fileNew();
			}
		});
		buttonOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menubar.fileOpen();
			}
		});
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menubar.fileSave();
			}
		});
		buttonSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menubar.fileSaveAs();
			}
		});

		// 编辑
		buttonCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.cut();
			}
		});
		// up
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JNTextArea.text = (textPane.getText());
				JNTextArea.name = window.note.getTitle();
				JNTextArea.have = 1;
			}
		});
		
		buttonCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.copy();
			}
		});
		buttonPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.paste();
			}
		});
		buttonFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menubar.select();
			}
		});
		buttonUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textPane.um.undo();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "没有可撤销的操作！");
				}
			}
		});
		buttonRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					textPane.um.redo();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "没有可恢复的操作！");
				}
			}
		});

		// 插入
		buttonDate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Document document = textPane.getDocument();
				try {
					document.insertString(textPane.getCaretPosition(),
							new Date().toString(), null);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});

		// 格式
		buttonFont.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				menubar.FontSelect();
			}
		});
		buttonBold.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (textPane.getFont().isBold())
					textPane.type = Font.PLAIN;
				else
					textPane.type = Font.BOLD;
				textPane.setFont(new Font(textPane.name, textPane.type,
						textPane.size));
			}
		});
		buttonItalic.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (textPane.getFont().isItalic())
					textPane.type = Font.PLAIN;
				else
					textPane.type = Font.ITALIC;
				textPane.setFont(new Font(textPane.name, textPane.type,
						textPane.size));
			}
		});
		buttonFontColor.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub			
				Color color = JColorChooser.showDialog(null, "请选择字体颜色", textPane.getSelectedTextColor());
				if (color == null) {
					color = Color.BLACK;
					System.out.println("Color is null");
				}
				textPane.setForeground(color);
				
			}
		});
		buttonBackColor.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Color color = JColorChooser.showDialog(null, "请选择背景颜色", textPane.getBackground());
				if (color == null) {
					color = textPane.getBackground();
				}
				textPane.setBackground(color);
			}
		});
	}

}
