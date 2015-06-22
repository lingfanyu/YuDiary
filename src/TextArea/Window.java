//主界面
package TextArea;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import YuNote.Note;


public class Window extends JPanel{//JFrame{
	
	final Text textPane= new Text(this);	
	final Menubar menubar ;//= new Menubar(this);
	final Toolbar toolbar ;//= new Toolbar(this);
	final Statusbar statusbar = new Statusbar(this);	
	
	Note note ;
	
	
/*	//运行程序
	public static void main(String[] args) {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	//创建窗口
	public Window(Note note) {
		this.note = note; 
		//textPane = new Text(this);
		//textPane.setStyledDocument(note.getdoc());
	
		System.out.println("!!!!!!!!!!!!!"+note.getUuid());
		menubar = new Menubar(this);
		toolbar = new Toolbar(this);
		initFrame();
		initListener();
	}
	
	//初始化窗口
	private void initFrame() {
		//setTitle("新建文本");		
		//setBounds(100, 100, 700, 500);
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		this.setLayout(new BorderLayout());
		//setResizable(false);
		setVisible(true);
		
		//setJMenuBar(menubar);
		
		
		JPanel bar = new JPanel();
		bar.setLayout(new BorderLayout());
		bar.add(menubar,BorderLayout.NORTH);
		bar.add(toolbar,BorderLayout.SOUTH);
		
		add(bar,BorderLayout.NORTH);
		
		add(statusbar,BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane(textPane);
		add(scrollPane,BorderLayout.CENTER);		
	}
	
	//初始化监听器
	private void initListener() {		
		//窗口关闭时提示保存文本
		//addWindowListener(new WindowAdapter() {
			//public void windowClosing(WindowEvent e) {
			//	if(menubar.fileChange() >= 0) System.exit(0);
			//}
		//});
	}

}
