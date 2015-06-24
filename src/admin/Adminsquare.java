package admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.Option;
import javax.swing.JOptionPane;

import Segment.Diary_Segment;
import Segment.Rank;
import Segment.client_Segment;
import Segment.server_Segment;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Adminsquare{//16*15
	//广场对话框
	JDialog square_dialog = new JDialog();
	int i = 0;
	private JPanel panelTitleBar = null;
	private JLabel jyu = new JLabel();
	private JLabel jsquare = new JLabel("非鱼广场");
	private JTextField jword = new JTextField("请输入您想查询的用户名~");
	private JButton jsearch = new JButton("");
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
	
	private JList<String> jdiary = new JList<String>(diary_listmodel);
	private JScrollPane jsdiary = new JScrollPane(jdiary);
	//搜索结果
	private ArrayList<Diary_Segment> result_list = new ArrayList<Diary_Segment>();
		
	private JPanel jmain;
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
	Adminusersqr usersquare = null;
	Admindiarysqr diary_square = null; 
	int myicon=1;
	Socket socket1=null;
	public Adminsquare(Socket socket , int icon){
		socket1=socket;
		myicon=icon;
		ImageIcon searchicon = new ImageIcon("icon/search.png");
		ImageIcon yuicon = new ImageIcon("icon/yu.png");
		ImageIcon userbgicon = new ImageIcon("icon/userbg.png");
		ImageIcon userbgicon2 = new ImageIcon("icon/userbg2.png");
		
		//用户头像，需要根据传递参数修改
		ImageIcon userpic = new ImageIcon("icon/pic"+myicon+".jpg");
		ImageIcon prank = new ImageIcon("icon/rank.png");
		
		
		jyu.setBounds(25, 10, 42, 44);
		jyu.setIcon(yuicon);
		
		jsquare.setBounds(80,10,200,44);
		
		Font font1=new Font("微软雅黑",Font.PLAIN,28);
		Font font2=new Font("微软雅黑",Font.PLAIN,18);
		jsquare.setFont(font1); 
		jsearch.setBounds(775, 12, 107, 34);
		jsearch.setBorder(null);
		jsearch.setIcon(searchicon);
		jsearch.setToolTipText("查询");
		jsearch.setIcon(searchicon);
		
		//查找用户的按钮的响应事件
		jsearch.addActionListener(
 				new ActionListener() {
 					public void actionPerformed(ActionEvent e) {
 						searchuser(socket1);			
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
		
		user.setLayout(null);
		
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
		
		
		//rank
		lrank.setIcon(prank);
		lrank.setBounds(968, 168+62+20, 256, 44);
		
		jsrank.setBounds(968, 168+62+20+44, 256, 360);
		jsrank.setBorder(null);
		jsrank.setOpaque(false);
		jsrank.getViewport().setOpaque(false);
		
		
		//list
		//热门日志的响应事件
		jdiary.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(i==0){
				// TODO Auto-generated method stub
				int[] indices=jdiary.getSelectedIndices();
					//选择位置：indices[i]
				//if (diary_square != null && diary_square.isShowing())
		    		//diary_square.dispose();
				System.out.println("Jlist");
				if(!diary_list.isEmpty() && indices.length>0){
					if (diary_square != null && diary_square.isShowing())
			    		diary_square.dispose();
					
					diary_square = new Admindiarysqr(socket1,diary_list.get(indices[0]));
				}
				i++;
				}else i--;
			}
		});
		jsdiary.setBounds(280, 110, 600, 550);
		jsdiary.setBorder(null);
		jsdiary.setOpaque(false);
		jsdiary.getViewport().setOpaque(false);
	
		//设置背景图片
		jmain = new JPanel() {
			private static final long serialVersionUID = 1L;
			{
			 		setOpaque(false);
			 		setLayout(new BorderLayout());
		 		}
				public void paintComponent(Graphics g) {
					g.drawImage(new ImageIcon("icon/seabg.png").getImage(), 0, 0, this);
					super.paintComponents(g);
				}
			};
		jmain.setBounds(0, 54, 1360, 666);
		jmain.setLayout(null);
	}
	public void setup(server_Segment recvseg,String name) {
		
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
		//square_dialog = new JDialog();
		Color chead = new Color(246,251,253);
		username.setText(name);
		square_dialog.add(jyu);
		square_dialog.add(jsquare);
		square_dialog.add(jword);
		square_dialog.add(jsearch);
		square_dialog.getContentPane().setBackground(chead);
		square_dialog.add(userprofile);
		square_dialog.add(username);
		square_dialog.add(userbgup);
		square_dialog.add(userbgup2);
		square_dialog.add(lrank);
		square_dialog.add(jsrank);
		square_dialog.add(jsdiary);
		square_dialog.add(jmain);
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
	//搜索用户
	private void searchuser(Socket socket) {
		String author=jword.getText();
		//发送用户登录信息
		//BufferedWriter bufout= new BufferedWriter( new OutputStreamWriter(toServer));
		try {
			client_Segment s= new client_Segment();
			s.head = 5;
			s.user.name=author;
			ObjectOutputStream send=new ObjectOutputStream(socket.getOutputStream());
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
    	usersquare = new Adminusersqr(socket,result_list);
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
}
