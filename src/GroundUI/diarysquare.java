package GroundUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import javax.swing.text.StyledDocument;

import Segment.Diary_Segment;
import Segment.client_Segment;
import TextArea.Button;
import TextArea.Text;

@SuppressWarnings("serial")
public class diarysquare extends JFrame{
	private JTextPane jdiary = new JTextPane();
	private JScrollPane jsdiary = new JScrollPane(jdiary);
	private JLabel jtitle = new JLabel();
	private Button jlike = new Button(new ImageIcon("icon/like.png"),"点赞");
	Diary_Segment diary;
	public diarysquare(final Socket socket, final Diary_Segment d)
	{
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
		Font font2=new Font("微软雅黑",Font.PLAIN,15);
		
		//title
		String title=diary.title;
		jtitle.setBounds(100,50,600,50);
		jtitle.setText(title);
		jtitle.setFont(font1);
		jtitle.setForeground(Color.white);
		jtitle.setAlignmentX(CENTER_ALIGNMENT);
		add(jtitle);
		
		//diary
		String diary2 = diary.text;
		jsdiary.setBounds(150, 150, 500, 450);
		 
		
		
		jdiary.setText(diary2);
			
		
		
		//jdiary.setText(diary2);// setText()方法会将原来的内容清除
		//jdiary.append("JTextArea2");// append()方法会将设置的字符串接在原来JTextArea内容文字之后.
		//jdiary.setLineWrap(true);// 设置换行
		jdiary.setFont(font2);
		jdiary.setForeground(Color.white);
		jdiary.setEditable(false);
		jdiary.setOpaque(false);
		
		jsdiary.setBorder(null);
		jsdiary.setOpaque(false);
		jsdiary.getViewport().setOpaque(false);
		jsdiary.setWheelScrollingEnabled(true);
		add(jsdiary);
		
		//like
		jlike.setBounds(700, 500, 30, 30);
		//jlike.setOpaque(true);
		//jlike.setVisible(true);
		add(jlike);
		
		jlike.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						diary.zan_number++;
						//zan_button.setText("赞"+dairy_list.get(dairy_index).zan_number);
						//向服务器发送被点赞的日志
						client_Segment sendseg = new client_Segment();
						sendseg.head = 8;
						
						sendseg.dairy.author=diary.author;
						sendseg.dairy.zan_number =diary.zan_number;
						sendseg.dairy.date.setTime(diary.date.getTime());
						
						try {
							//发送到服务器
							ObjectOutputStream send =null;
							send = new ObjectOutputStream(socket.getOutputStream());
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