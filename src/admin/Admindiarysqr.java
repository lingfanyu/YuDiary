package admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import Segment.Diary_Segment;
import Segment.client_Segment;

public class Admindiarysqr extends JFrame{
	private JTextArea jdiary = new JTextArea();
	private JScrollPane jsdiary = new JScrollPane(jdiary);
	private JLabel jtitle = new JLabel();
	private JButton jdelete = new JButton();
	Diary_Segment diary;
	private Socket socket1=null;
	public Admindiarysqr(Socket socket, Diary_Segment d) {
		this.socket1=socket;
		diary = d;
		setContentPane(
				new JPanel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 3L;
					ImageIcon icon;
					Image img;
					{ 
						icon=new ImageIcon("icon/seabg.png" );
						img=icon.getImage();
					} 
					public void paintComponent(Graphics g)
					{ 
						super.paintComponent(g);
						g.drawImage(img,0,0,null);
					} 
				}
			);
		
		ImageIcon likeicon = new ImageIcon("icon/like.png");
		Font font1=new Font("微软雅黑",Font.BOLD,36);
		Font font2=new Font("微软雅黑",Font.PLAIN,20);
		
		//title
		String title=diary.title;
		jtitle.setBounds(100,50,600,50);
		jtitle.setText(title);
		jtitle.setFont(font1);
		jtitle.setForeground(Color.white);
		jtitle.setAlignmentX(CENTER_ALIGNMENT);
		add(jtitle);
		
		//diary
		String diary2=diary.text;
		jsdiary.setBounds(150, 150, 500, 450);
		jdiary.setText(diary2);// setText()方法会将原来的内容清除
		//jdiary.append("JTextArea2");// append()方法会将设置的字符串接在原来JTextArea内容文字之后.
		jdiary.setLineWrap(true);// 设置换行
		jdiary.setFont(font2);
		jdiary.setForeground(Color.white);
		jdiary.setEditable(false);
		jdiary.setOpaque(false);
		
		jsdiary.setBorder(null);
		jsdiary.setOpaque(false);
		jsdiary.getViewport().setOpaque(false);
		add(jsdiary);
		
		
		//like
		jdelete.setIcon(new ImageIcon("icon/delete.png"));
		jdelete.setBounds(700, 500, 50, 50);
		//jlike.setOpaque(true);
		//jlike.setVisible(true);
		add(jdelete);
		
		jdelete.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//zan_button.setText("赞"+dairy_list.get(dairy_index).zan_number);
						//向服务器发送被点赞的日志
						client_Segment sendseg = new client_Segment();
						sendseg.head = 999;
						
						sendseg.dairy.author=diary.author;
						sendseg.dairy.date.setTime(diary.date.getTime());
						
						try {
							//发送到服务器
							ObjectOutputStream send=new ObjectOutputStream(socket1.getOutputStream());
							send.writeObject(sendseg);
					        send.flush();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
			);
		
		
		setLayout(null);
		setSize(800,600);
 		setResizable(false);
 		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		setLayout(null);
 		setLocationRelativeTo(null);
 		setVisible(true);
	}
	
/*	public static void main(String[] args)  {
		new diarysquare();	
	}
*/
}