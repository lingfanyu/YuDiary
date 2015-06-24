package GroundUI;

//import diarysquare;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.Option;
import javax.swing.JOptionPane;

import Ground.JNTextArea;
import Segment.Diary_Segment;
import Segment.Rank;
import Segment.client_Segment;
import Segment.server_Segment;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class square2 extends JFrame{//16*15
	//广场对话框
	JDialog square_dialog  = new JDialog();//= null;
	
	private JPanel panelTitleBar = null;
	private JLabel jyu = new JLabel();
	private JLabel jsquare = new JLabel("非鱼广场");
	private JTextField jword = new JTextField("请输入您想查询的用户名~");
	private JButton jsearch = new JButton("");
	private JButton refresh = new JButton();
	//private JLabel refresh = new JLabel();
	//排行榜用户列表数据
	//排行榜表
	private ArrayList<Rank> ranks = new ArrayList<Rank>();
	private DefaultListModel<String> rank_listmodel = new DefaultListModel<String>();
	
	//排行榜用户列表
	private JList<String> jrank = new JList<String>(rank_listmodel);
	private JScrollPane jsrank = new JScrollPane(jrank);
	//热门日记列表
	private ArrayList<Diary_Segment> diary_list = new ArrayList<Diary_Segment>();
	private DefaultListModel<String> diary_listmodel = new DefaultListModel<String>();
	
	//private JList<String> jdiary = new JList<String>(diary_listmodel);
	//private JScrollPane jsdiary = new JScrollPane(jdiary);
	//搜索结果
	public ArrayList<Diary_Segment> result_list = new ArrayList<Diary_Segment>();
		
	private JPanel jmain;
	private JScrollPane jsmain;
	private JPanel user = new JPanel();
	private JLabel userbgup = new JLabel();
	private JLabel userbgup2 = new JLabel();
	//用户头像
	private JLabel userprofile = new JLabel();
	//用户姓名
	private JLabel username = new JLabel();
	
	private JLabel lrank = new JLabel();
	
//	private JButton[] rankuser = new JButton[10];
//	JScrollPane jsmain = new JScrollPane(jmain);
	private boolean wordTyped = false;
	usersquare usersquare = null;
    diarysquare diary_square = null; 
	int myicon=1;
	//int[] indices;
	/*
	int num = 5;
	private JPanel[] jpdiary=new JPanel[num];
	private JLabel[] jpicon=new JLabel[num];
	private JLabel[] jpname=new JLabel[num];
	private JLabel[] jptitle=new JLabel[num];
	private JTextArea[] jpcontent=new JTextArea[num];*/
	private JPanel[] jpdiary;
	private JLabel[] jpicon;
	private JLabel[] jpname;
	private JLabel[] jptitle;
	private JTextArea[] jpcontent;
	
	ImageIcon[] sicon = {new ImageIcon("icon/spic1.jpg"),new ImageIcon("icon/spic2.jpg"),new ImageIcon("icon/spic3.jpg"),
			new ImageIcon("icon/spic3.jpg"),new ImageIcon("icon/spic3.jpg"),new ImageIcon("icon/spic3.jpg"),
			new ImageIcon("icon/spic3.jpg"),new ImageIcon("icon/spic3.jpg")};
	int now;
	
	public square2(final Socket socket , int icon){
		setContentPane(
				new JPanel() {
					
					private static final long serialVersionUID = 1L;
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
					//	g.drawImage(img, 0, 0, 1360, 720, null);
					} 
				}
			);
			
		myicon=icon;
		ImageIcon searchicon = new ImageIcon("icon/search.png");
		ImageIcon yuicon = new ImageIcon("icon/yu.png");
		ImageIcon userbgicon = new ImageIcon("icon/userbg.png");
		ImageIcon userbgicon2 = new ImageIcon("icon/userbg2.png");
		ImageIcon refreshicon = new ImageIcon("icon/refresh.png");
		
		//用户头像，需要根据传递参数修改
		ImageIcon userpic = new ImageIcon("icon/pic"+myicon+".jpg");
		ImageIcon prank = new ImageIcon("icon/rank.png");
		
		
		Color chead = new Color(246,251,253);
		Color grey = new Color(165,165,165);
		Color blue2 = new Color(16, 92, 182);
		
		
		jyu.setBounds(25, 10, 42, 44);
		jyu.setIcon(yuicon);
		
		
		Font font1=new Font("微软雅黑",Font.PLAIN,30);
		Font font2=new Font("微软雅黑",Font.PLAIN,18);
		Font font3=new Font("微软雅黑",Font.BOLD,16);
		Font font4=new Font("楷体",Font.PLAIN,20);
		Font font5=new Font("微软雅黑",Font.PLAIN,16);
		Font font6=new Font("Verdana, Arial, Helvetica, sans-serif",Font.PLAIN,14);
		
		
		jsquare.setBounds(80,10,200,44);
		jsquare.setFont(font1);
		jsquare.setForeground(Color.white);
		jsearch.setBounds(775, 12, 107, 34);
		//jsearch.setBounds(1360-117, 12, 107, 34);
		jsearch.setBorder(null);
		jsearch.setIcon(searchicon);
		jsearch.setToolTipText("查询");
		
		refresh.setFont(font2);
        refresh.setBounds(1200, 12, 34, 34);
        refresh.setBorder(null);
        refresh.setIcon(refreshicon);
        refresh.addActionListener(
 				new ActionListener() {
 					public void actionPerformed(ActionEvent e) {
 						sendfresh(socket);			
 					}			
 				}
 			);
        
        
        
		//查找用户的按钮的响应事件
		jsearch.addActionListener(
 				new ActionListener() {
 					public void actionPerformed(ActionEvent e) {
 						searchuser(socket);			
 					}			
 				}
 			);
		
		jword.setBounds(280, 12, 497, 35);
		jword.setForeground(Color.gray); 
		//搜索用户的文本框的美化修饰效果，可忽略
		jword.addMouseListener(new MouseAdapter(){ 
			public void mousePressed(MouseEvent e){ 
				if (!wordTyped) {
					jword.setForeground(Color.black); 
					jword.setText(null);
					wordTyped = true;
				}
			} 
		});
		
		jword.addKeyListener(
			new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (!wordTyped) {
						jword.setForeground(Color.black); 
						jword.setText(null);
						wordTyped = true;
					}
				}
			}
		);
			
	/*	547*72 968*106
		145*85 256*125
		145*175 256*158
	*/
		
/*		user.setLayout(null);
		
		userprofile.setIcon(userpic);
		userprofile.setBounds(968+90, 106+10, 90, 90);
		

		//根据传递参数修改用户姓名
		String name="热带鱼";
		username.setText(name);
		username.setFont(font2);
		username.setBounds(970+100, 106+100, 90, 25);
		
		
		userbgup.setIcon(userbgicon);
		userbgup.setBounds(968, 106, 256, 62);
		
		userbgup2.setIcon(userbgicon2);
		userbgup2.setBounds(968, 168, 256, 62);
		
*/		
		
		int shift=60;
		user.setLayout(null);
		
		userprofile.setIcon(userpic);
		userprofile.setBounds(968+90, 55+10+shift, 90, 90);

		//根据传递参数修改用户姓名
		String name="热带鱼";
		username.setText(name);
		username.setFont(font2);
		username.setBounds(970+100, 55+100+shift, 90, 25);
		
		userbgup.setIcon(userbgicon);
		userbgup.setBounds(968, 55+shift, 256, 62);
		
		userbgup2.setIcon(userbgicon2);
		userbgup2.setBounds(968, 117+shift, 256, 62);
		
		
		//rank
		lrank.setIcon(prank);
		//lrank.setBounds(968, 116+62+20, 256, 44);
		lrank.setBounds(968, 116+62+20+shift, 256, 44);

		
		//rank
/*		lrank.setIcon(prank);
		lrank.setBounds(968, 168+62+20, 256, 44);
		
		jdiary.setOpaque(false);
		jdiary.setFixedCellHeight(30);
		jdiary.setFont(font3);
		((JComponent)jdiary.getCellRenderer()).setOpaque(false);
		jdiary.setForeground(Color.white);
		jsrank.setBounds(968, 168+62+20+44, 256, 360);
		jsrank.setBorder(null);
		jsrank.setOpaque(false);
		jsrank.getViewport().setOpaque(false);
*/		
		
		jrank.setFixedCellHeight(30);
		jrank.setFont(font4);
		jrank.setForeground(grey);
		//jsrank.setBounds(968, 172+70, 256, 360);
		jsrank.setBounds(968, 172+72+shift, 256, 360);
		jsrank.setBorder(null);
		jsrank.setOpaque(false);
		jsrank.getViewport().setOpaque(false);
		//add(jsrank);
		
		//热门日志的响应事件
		/*jrank.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						// TODO Auto-generated method stub
						int[] indices=jrank.getSelectedIndices();
							//选择位置：indices[i]
						String rankname = rank_listmodel.get(indices[0]);
						//searchuser(socket);	
						
				    	
					}
				});
		*/
		
		//list
		//热门日志的响应事件
	/*
		jdiary.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int[] indices=jdiary.getSelectedIndices();
					//选择位置：indices[i]
				if (diary_square != null && diary_square.isShowing()){
					diary_square.dispose();
					diary_square = null;
				}	
				
				if(!diary_list.isEmpty() && indices.length>0 ){
					//System.out.println(indices[0]);
					try {
						diary_square = new diarysquare(socket,diary_list.get(indices[0]));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		    	
			}
		});
		*/
/*		jsdiary.setBounds(280, 110, 600, 550);
		jsdiary.setBorder(null);
		jsdiary.setOpaque(false);
		jsdiary.getViewport().setOpaque(false);
		
		//设置背景图片
		
		jmain.setBounds(0, 54, 1360, 666);
		jmain.setLayout(null);
	*/
		
		
/*		add(jsmain);
	//	add(username);
		add(userprofile);
		add(userbgup);
		add(userbgup2);
		add(lrank);
		add(jsrank);
		setLayout(null);
		//add(panelTitleBar);
		//setBackground(chead); 
		setSize(1360,720);
 		//setResizable(false);
 		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		setLayout(null);
 	//	setLocationRelativeTo(null);
 		setVisible(true);
 		*/
	}
	public void setup(final Socket socket,server_Segment recvseg,String name) {
		clean(); 
		 int i;
		 for(i=0;i<recvseg.dairylist.size();i++){
			 diary_list.add(recvseg.dairylist.get(i));
		 }
		 for(i=0;i<recvseg.rank_list.size();i++){
			 ranks.add(recvseg.rank_list.get(i));
		 }
		 
		for(i=0;i<diary_list.size();i++){
			diary_listmodel.addElement(diary_list.get(i).title);
		}
		
		for(i=0;i<ranks.size();i++){
			 rank_listmodel.addElement(ranks.get(i).userName+": "+ranks.get(i).zan_number);
		 }
		int num = diary_list.size();
		jmain=new JPanel();
		jsmain = new JScrollPane(jmain);
		//jsmain.set
		jsmain.setBounds(0, 54, 1360, 666);
		jsmain.setOpaque(false);
		jsmain.getViewport().setOpaque(false);
		
		jmain.setBounds(0, 54, 1360, 3000);
		jmain.setPreferredSize(new Dimension(jsmain.getWidth() - 50,50+230*num)); 
		jmain.setLayout(null);
		jmain.setOpaque(false);
		
		jmain.setBorder(null);
		jsmain.setBorder(null);
		
		//square_dialog = new JDialog();
		Font font3=new Font("微软雅黑",Font.BOLD,16);
		Font font5=new Font("微软雅黑",Font.PLAIN,16);
		Font font6=new Font("Verdana, Arial, Helvetica, sans-serif",Font.PLAIN,14);
		Color chead = new Color(246,251,253);
		Color blue2 = new Color(16, 92, 182);
		jpdiary=new JPanel[num];
		jpicon=new JLabel[num];
		jpname=new JLabel[num];
		jptitle=new JLabel[num];
		jpcontent=new JTextArea[num];
		
		for(i=0;i<diary_list.size();i++) {
			jpdiary[i]=new JPanel();
			jpdiary[i].setLayout(null);
			jpdiary[i].setBounds(300, 50+230*i,500,200);
			jpdiary[i].setBackground(Color.white);
			jpicon[i]=new JLabel();
			jpicon[i].setBounds(10,10,50,50);
			//用户头像
			jpicon[i].setIcon(sicon[diary_list.get(i).icon-1]);		
			jpname[i]=new JLabel();
			jpname[i].setBounds(80,35,100,20);
			//用户名
			jpname[i].setText(diary_list.get(i).author);
			jpname[i].setFont(font3);
			jptitle[i]=new JLabel();
			jptitle[i].setBounds(10,70,300,20);
			//日记标题
			String title1=diary_list.get(i).title;
			String title2="<HTML><U>"+title1+"</U></HTML>";
			jptitle[i].setText(title2);
			jptitle[i].setFont(font5);
			jptitle[i].setForeground(blue2);
			final int p = i;
			jptitle[i].addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent e){
							if (diary_square != null && diary_square.isShowing()){
								diary_square.dispose();
								diary_square = null;
							}
							diary_square = new diarysquare(socket,diary_list.get(p));
						}
		 			});
			jpcontent[i]=new JTextArea();
			jpcontent[i].setBounds(10,100,450,190);
			jpcontent[i].setLineWrap(true);// 设置换行
			jpcontent[i].setEditable(false);
			//日记压缩内容
			jpcontent[i].setText(diary_list.get(i).text);
			jpcontent[i].setFont(font6);
			jpdiary[i].add(jpicon[i]);
			jpdiary[i].add(jpname[i]);
			jpdiary[i].add(jptitle[i]); 
			jpdiary[i].add(jpcontent[i]);	
			
			jmain.add(jpdiary[i]);

		}
		
		//Color chead = new Color(246,251,253);
		username.setText(name);
		square_dialog.setContentPane(
				new JPanel() {
					
					private static final long serialVersionUID = 1L;
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
					//	g.drawImage(img, 0, 0, 1360, 720, null);
					} 
				}
			);
		square_dialog.add(jyu);
		square_dialog.add(jsquare);
		square_dialog.add(jword);
		square_dialog.add(jsearch);
		square_dialog.add(refresh);
		square_dialog.getContentPane().setBackground(chead);
		square_dialog.add(jsmain);
		square_dialog.add(userprofile);
		square_dialog.add(username);
		square_dialog.add(userbgup);
		square_dialog.add(userbgup2);
		square_dialog.add(lrank);
		square_dialog.add(jsrank);
		//square_dialog.add(jsdiary);
	
		square_dialog.setLayout(null);
		//add(panelTitleBar);
		square_dialog.setBackground(chead); 
		square_dialog.setSize(1360,720);
		square_dialog.setResizable(false);
		//square_dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		square_dialog.setLayout(null);
 	//	setLocationRelativeTo(null);
		square_dialog.setVisible(true);
	}
	public void refresh(server_Segment recvseg){
		clean();
		 int i;
		 for(i=0;i<recvseg.dairylist.size();i++){
			 diary_list.add(recvseg.dairylist.get(i));
		 }
		 for(i=0;i<recvseg.rank_list.size();i++){
			 ranks.add(recvseg.rank_list.get(i));
		 }
		 
		for(i=0;i<diary_list.size();i++){
			diary_listmodel.addElement(diary_list.get(i).title);
		}
		
		for(i=0;i<ranks.size();i++){
			 rank_listmodel.addElement(ranks.get(i).userName+": "+ranks.get(i).zan_number);
		 }
		
		int num = diary_list.size();
		jmain=new JPanel();
		jsmain = new JScrollPane(jmain);
		//jsmain.set
		jsmain.setBounds(0, 54, 1360, 666);
		jsmain.setOpaque(false);
		jsmain.getViewport().setOpaque(false);
		
		jmain.setBounds(0, 54, 1360, 3000);
		jmain.setPreferredSize(new Dimension(jsmain.getWidth() - 50,50+230*num)); 
		jmain.setLayout(null);
		jmain.setOpaque(false);
		
		jmain.setBorder(null);
		jsmain.setBorder(null);
		
		//square_dialog = new JDialog();
		Font font3=new Font("微软雅黑",Font.BOLD,16);
		Font font5=new Font("微软雅黑",Font.PLAIN,16);
		Font font6=new Font("Verdana, Arial, Helvetica, sans-serif",Font.PLAIN,14);
		Color chead = new Color(246,251,253);
		Color blue2 = new Color(16, 92, 182);
		jpdiary=new JPanel[num];
		jpicon=new JLabel[num];
		jpname=new JLabel[num];
		jptitle=new JLabel[num];
		jpcontent=new JTextArea[num];
		
		for(i=0;i<diary_list.size();i++) {
			jpdiary[i]=new JPanel();
			jpdiary[i].setLayout(null);
			jpdiary[i].setBounds(300, 50+230*i,500,200);
			jpdiary[i].setBackground(Color.white);
			jpicon[i]=new JLabel();
			jpicon[i].setBounds(10,10,50,50);
			//用户头像
			jpicon[i].setIcon(sicon[1]);		
			jpname[i]=new JLabel();
			jpname[i].setBounds(80,35,100,20);
			//用户名
			jpname[i].setText(diary_list.get(i).author);
			jpname[i].setFont(font3);
			jptitle[i]=new JLabel();
			jptitle[i].setBounds(10,70,300,20);
			//日记标题
			final String title1=diary_list.get(i).title;
			String title2="<HTML><U>"+title1+"</U></HTML>";
			jptitle[i].setText(title2);
			jptitle[i].setFont(font5);
			jptitle[i].setForeground(blue2);
			jptitle[i].addMouseListener(
		 			new MouseAdapter() {
						public void mousePressed(MouseEvent e){
							if (diary_square != null && diary_square.isShowing()){
								diary_square.dispose();
								diary_square = null;
							}	
							//diary_square = new diarysquare(socket,);
					/*	public void  mouseReleased(MouseEvent e) {
							
						}
					*/
						}
		 			});
			
			jpcontent[i]=new JTextArea();
			jpcontent[i].setBounds(10,100,450,190);
			jpcontent[i].setLineWrap(true);// 设置换行
			jpcontent[i].setEditable(false);
			//日记压缩内容
			jpcontent[i].setText(diary_list.get(i).text);
			jpcontent[i].setFont(font6);
			jpdiary[i].add(jpicon[i]);
			jpdiary[i].add(jpname[i]);
			jpdiary[i].add(jptitle[i]); 
			jpdiary[i].add(jpcontent[i]);	
			
			jmain.add(jpdiary[i]);

		}
	}
	private void sendfresh(Socket socket){
		//发送用户登录信息
		//BufferedWriter bufout= new BufferedWriter( new OutputStreamWriter(toServer));
		try {
			client_Segment s= new client_Segment();
			s.head = 9;
			ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
	        send.writeObject(s);
	        send.flush();  
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	//搜索用户
	private void searchuser(Socket socket,String name ) {
		String author=name;
		JNTextArea.search_name = name;
		//发送用户登录信息
		//BufferedWriter bufout= new BufferedWriter( new OutputStreamWriter(toServer));
		try {
			client_Segment s= new client_Segment();
			s.head = 5;
			s.user.name=author;
			ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
	        send.writeObject(s);
	        send.flush();  
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	private void searchuser(Socket socket) {
		String author=jword.getText();
		JNTextArea.search_name = author;
		//发送用户登录信息
		//BufferedWriter bufout= new BufferedWriter( new OutputStreamWriter(toServer));
		try {
			client_Segment s= new client_Segment();
			s.head = 5;
			s.user.name=author;
			ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
	        send.writeObject(s);
	        send.flush();  
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void add_result(Diary_Segment d){
		result_list.add(d);
		//System.out.println(d.text);
	}
	
 public void showresult(Socket socket){
		if (usersquare != null && usersquare.isShowing())
    		usersquare.dispose();
    	usersquare = new usersquare(socket,result_list);
	}
	public void clean(){
		diary_list.clear();
		diary_listmodel.clear();
		ranks.clear();
		rank_listmodel.clear();
		result_list.clear();
	}
	public void seticon(int icon){
		myicon =icon;
		ImageIcon userpic = new ImageIcon("icon/pic"+myicon+".jpg");
		userprofile.setIcon(userpic);
	}
	public static void main(String[] args)  {
		new square2(null,5);	
	}
}
