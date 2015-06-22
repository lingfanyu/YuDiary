package TextArea;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class Text extends JTextPane{
	
	private Window window;
	
	//标志
	boolean changed=false;
	
	//撤销
	UndoManager um = new UndoManager();
	
	//字体
	String name = "黑体";
	int type = Font.PLAIN;
	int size = 20;
	
	//弹出菜单
	private final JPopupMenu popupMenu = new JPopupMenu();	
	private final JMenuItem menuItemCut = new JMenuItem("剪切");
	private final JMenuItem menuItemCopy = new JMenuItem("复制");
	private final JMenuItem menuItemPaste = new JMenuItem("粘贴");	
	
	Text(Window window) {
		this.window=window;
		
		inittextPane();
		initShortcut();
		initListener();
	}
	
	//初始化文本区
	private void inittextPane() {
		requestFocus(); //获得焦点
		setFont(new Font(name,type,size));	
		popupMenu.add(menuItemCut);
		popupMenu.add(menuItemCopy);
		popupMenu.add(menuItemPaste);
	}
	
	//初始化快捷键
	private void initShortcut() {			
		menuItemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		menuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menuItemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
	}
	
	//初始化监听器
	private void initListener() {		
		//撤销
		getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                um.addEdit(e.getEdit());
            }
		});
		
		//文本改变
		getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent arg0) {
				changed=true;
			}
			public void insertUpdate(DocumentEvent arg0) {
				changed=true;
			}
			public void changedUpdate(DocumentEvent arg0) {
				changed=true;
			}
		});
		
		//光标移动
        addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
            	window.statusbar.setLabelPos(e.getDot());
            	window.statusbar.setLabelChar();
            }
        });
		
		//弹出菜单
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		menuItemCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cut();
			}
		});	
		menuItemCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});	
		menuItemPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paste();
			}
		});
	}
	
}
