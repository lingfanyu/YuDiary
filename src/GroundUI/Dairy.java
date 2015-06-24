package GroundUI;

import Ground.JNTextArea;
import Segment.User;
import Segment.client_Segment;
import Segment.server_Segment;
import Segment.Diary_Segment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//主界面
@SuppressWarnings("serial")
public class Dairy extends JPanel{
	
	//登陆按钮
	private JButton login_button = new JButton("登陆");
	//注册按钮
	private JButton register_button = new JButton("注册");
	//注销按钮
	private JButton logout_button = new JButton("注销");
	//广场按钮
	private JButton square_button = new JButton("广场");
	//收到按钮
    private JButton recv_button = new JButton("信箱");
	//上传日记按钮
	private JButton up_button = new JButton("上传日记");
	//分享日记按钮
	private JButton share_button = new JButton("分享日记");
	
	//登陆后的欢迎标签
	JLabel welcome_label = new JLabel(); 
	//与服务器有交互的按钮面板——网络面板
	JPanel netpanel = new JPanel(); 
	//临时显示区，以后需要删掉
	//private JTextArea jta = new JTextArea(); 
	
	//登陆小界面
	LoginFrame diary_login;
	
	//注册界面
	RegisterFrame diary_register;
	
	//登陆广场界面
	square2 users_square;
	//用户资料
	String username;
	int icon;
	//在线用户信息
	public ArrayList<String> users_list = new ArrayList<String>();
	
	//用户接收到的日志
	private ArrayList<Diary_Segment> dairy_recv = new ArrayList<Diary_Segment>();
	
	//ObjectOutputStream send;
	//ObjectInputStream recv;
	public Dairy(final Socket socket){
		//设置网络面板
		netpanel.setLayout(new FlowLayout(0,4,1));
	    netpanel.add(login_button);
	    netpanel.add(register_button);
	    netpanel.add(welcome_label);
	    netpanel.add(logout_button);
	    netpanel.add(square_button);
        netpanel.add(up_button);
        netpanel.add(share_button);
        netpanel.add(recv_button);
        
	    //隐藏登陆状态的标签
	    welcome_label.setVisible(false);
	    logout_button.setVisible(false);
	    square_button.setVisible(false);
	    up_button.setVisible(false);
	    share_button.setVisible(false);
	    recv_button.setVisible(false);
	    
	    //设置主面板
	    setLayout(new BorderLayout());
	    add(netpanel, BorderLayout.NORTH);
	    //add(new JScrollPane(jta), BorderLayout.CENTER);

	   // setTitle("Client");
	    setSize(500, 300);
	    setVisible(true);
	    setLocation(100,100);
		//setResizable(false);
	   // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     // It is necessary to show the frame here!
	    //u.setSize(720, 550);
		
		//u.setVisible(true);
		//u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //添加监听
	    
	    //登陆按钮监听
	    //dairy_login = new Login_Dialog(socket);
	    //dairy_register = new register_Dialog(socket);
  		login_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  				diary_login = new LoginFrame(socket);
  			}
  		});
  		register_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  				diary_register = new RegisterFrame(socket);
  			}
  		});
  		//上传日志监听
  		up_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  				 if(JNTextArea.have == 1)
			    	{
  					up_dairy(socket);
			    		JNTextArea.have = 0;
			    	}
				else JOptionPane.showMessageDialog(null,"(●'◡'●)请先上载日志", "System Info", JOptionPane.ERROR_MESSAGE);

  			}
  		});
  		//
  		square_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  				if (users_square == null)users_square = new square2(socket,icon);
  			    up_square(socket);
  			}
  		});
  		//注销按钮监听
  		logout_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  			    up_logout(socket);
  			}
  		});
  		//分享按钮监听
  		share_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  			    if(JNTextArea.have == 1)
  			    	{
  			    		up_share(socket);
  			    		JNTextArea.have = 0;
  			    	}
  				else JOptionPane.showMessageDialog(null,"(●'◡'●)请先上载日志", "System Info", JOptionPane.ERROR_MESSAGE);

  			}
  		});
  		recv_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  				if(!dairy_recv.isEmpty()){
  					new diarysquare(socket,dairy_recv.get(0));
  					dairy_recv.clear();
  				}
  			    
  			}
  		});
  		//创建监听线程
  		client_listen_pthread task=new client_listen_pthread(socket);
		new Thread(task).start();
	}
	public void up_share(Socket socket){
		//向服务器发送要分享的日志
		new Sharediary(socket); 
	}
	public void up_logout(Socket socket){
		//向服务器发送注销
		welcome_label.setVisible(false);
	    logout_button.setVisible(false);
	    square_button.setVisible(false);
	    up_button.setVisible(false);
	    share_button.setVisible(false);
		login_button.setVisible(true);
		register_button.setVisible(true);
		client_Segment sendseg = new client_Segment();
		sendseg.head = 7;
		sendseg.user.name = username;
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
	public void up_square(Socket socket){
		//向服务器请求数据
		client_Segment sendseg = new client_Segment();
		sendseg.head = 4;
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
	//上传日志
	public void up_dairy(Socket socket){
		String text = JNTextArea.text;
		//设置报文
		client_Segment sendseg = new client_Segment();
		sendseg.head = 3;
		sendseg.dairy.text=text;
		sendseg.dairy.author= username ;//username;
		sendseg.dairy.public_flag = true;
		sendseg.dairy.title = username +" "+ JNTextArea.name;
		sendseg.dairy.zan_number =0;
		sendseg.dairy.date.setTime(new Date().getTime());
		try {
			//发送到服务器
			ObjectOutputStream send =null;
			send = new ObjectOutputStream(socket.getOutputStream());
			send.writeObject(sendseg);
	        send.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
	}
	//客户端监听线程
	class client_listen_pthread implements Runnable {
		Socket socket; // A connected socket
	  
	    /** Construct a thread */
	    public client_listen_pthread(Socket socket) {
	      this.socket = socket;
	    }
	    
	    public void run() {
	      try {
	    	  
	    	  ObjectInputStream recv =null;
	        //BufferedReader br=new BufferedReader(new InputStreamReader(fromServer));
	        // Continuously serve the client
	        while (true) {
	        	recv = new ObjectInputStream(socket.getInputStream());
		    	server_Segment recvseg=null;
		        try {
					recvseg = (server_Segment)recv.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          // Receive from server
	        	 switch(recvseg.head){
	        	 //登陆成功
	        	 case 1:{
	        		 username = recvseg.user.name;
	        		 icon = recvseg.user.icon;
	        		 int i=0;
	        		 for(;i<recvseg.users_list.size();i++){
	        			 users_list.add(recvseg.users_list.get(i));
	        		 }
	        		 for(i=0;i<users_list.size();i++){
	        			// jta.append(users_list.get(i)+"\n");
	        		 }
	        		 JOptionPane.showMessageDialog(null, "登陆成功");
	        	     diary_login.setVisible(false);
	        	    //隐藏主界面的登陆按钮
	 				login_button.setVisible(false);
	 				register_button.setVisible(false);
	 				welcome_label.setText("welcome： "+username);
	 				//显示主界面的按钮
	 				welcome_label.setVisible(true);
	 				logout_button.setVisible(true);
	 				square_button.setVisible(true);
	 				up_button.setVisible(true);
	 				share_button.setVisible(true);
	 				recv_button.setVisible(true);
	        	 }break;
	        	 //登陆失败
	        	 case 2:{
	        		 if(recvseg.logfault == 1){
	        			 JOptionPane.showMessageDialog(null, "登陆失败！不存在该用户名！");
	        		 }else if(recvseg.logfault == 2){
	        			 JOptionPane.showMessageDialog(null, "登陆失败！密码错误！");
	        		 }else if(recvseg.logfault == 3){
	        			 JOptionPane.showMessageDialog(null, "该账号已经登陆！");
	        		 }else{
	        			 System.out.println("transfor error");
	        		 }
	        	 }break;
	        	 //广场登陆反馈
	        	 case 3:{
	        		 users_square.seticon(icon);
	        		 users_square.setup(socket, recvseg,username);
	        	 }break;
	        	 //注册成功
	        	 case 4:{
	        		 username = recvseg.user.name;
	        		 icon = recvseg.user.icon;
	        		 int i=0;
	        		 for(;i<recvseg.users_list.size();i++){
	        			 users_list.add(recvseg.users_list.get(i));
	        		 }
	        		 for(i=0;i<users_list.size();i++){
	        			// jta.append(users_list.get(i)+"\n");
	        		 }
	        		 diary_register.setVisible(false);
	        		 //隐藏主界面的登陆按钮
	 				login_button.setVisible(false);
	 				register_button.setVisible(false);
	 				welcome_label.setText("welcome： "+username);
	 				//显示主界面的按钮
	 				welcome_label.setVisible(true);
	 				logout_button.setVisible(true);
	 				square_button.setVisible(true);
	 				up_button.setVisible(true);
	 				share_button.setVisible(true);
	 				recv_button.setVisible(true);
	        	 }break;
	        	 //注册失败
	        	 case 5:{
	        		 JOptionPane.showMessageDialog(null, "该账号已经被注册！");
	        	 }break;
	        	 //广场搜索响应
	        	 case 6:{
	        		 int i=0;
	        		 users_square.result_list.clear();
	        		 for(;i<recvseg.dairylist.size();i++){
	        			 users_square.add_result(recvseg.dairylist.get(i));
	        		 }
	        		 users_square.showresult(socket);
	        	 }break;
	        	 //更新在线用户列表
	        	 case 7:{
	        		 users_list.add(recvseg.user.name);
	        		// jta.append(recvseg.user.name+"\n");
	        	 }break;
	        	 //收到其他客户分享的日志
	        	 case 8:{
	        		 int i=0;
	        		 for(;i<recvseg.dairylist.size();i++){
	        			 dairy_recv.add(recvseg.dairylist.get(i));
	        		 }
	        		// jta.append(dairy_recv.get(0).text+"\n");
	        	 }break;
	        	 //有用户下线
	        	 case 9:{
	        		 //先在users_list中找到它
	        		 int i=0;
	        		 for(;i<users_list.size();i++){
	        			 if(users_list.get(i).equals(recvseg.user.name)){
	        				 break;
	        			 }
	        		 }
	        		 users_list.remove(i);
	        		// jta.setText("");
	        		 for(i=0;i<users_list.size();i++){
	        			// jta.append(users_list.get(i)+"\n");
	        		 }
	        	 }break;
	        	 case 10:{//广场更新响应
	        		 users_square.refresh(recvseg);
	        	 }break;
	        	 default:break;
	        	 }	 
	          }
	      }
	      catch(IOException e) {
	        System.err.println(e);
	        System.out.println("server has some error!");
	      }
	    }
    } 
	class Sharediary
	{
		JDialog shareWordCardDialog;
		
		JList<String> onlineUserList;
		JList<String> chosenUserList;
		
		//存储已选择用户
		String[] chosenUser;
		//已选择用户数量
		int count = 0;
		
		public Sharediary(final Socket socket)
		{
			shareWordCardDialog = new JDialog();
			shareWordCardDialog.setLayout(new BorderLayout());
			
			JPanel listPanel = new JPanel(new GridLayout(1,2));
			//left list
			final DefaultListModel<String> modelLeft = new DefaultListModel<String>();
			onlineUserList = new JList<String>(modelLeft);
			JScrollPane scrollPaneLeft = new JScrollPane(onlineUserList);
			onlineUserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//添加所有用户
			for(int i=0;i<users_list.size();i++){
				modelLeft.addElement(users_list.get(i));
			}
			scrollPaneLeft.setBorder(new TitledBorder("请选择在线用户"));
			//right list
			final DefaultListModel<String> modelRight = new DefaultListModel<String>();
			chosenUserList = new JList<String>(modelRight);
			JScrollPane scrollPaneRight = new JScrollPane(chosenUserList);
			chosenUserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPaneRight.setBorder(new TitledBorder("已选择用户，单击取消选择"));
			
			listPanel.add(scrollPaneLeft);
			listPanel.add(scrollPaneRight);
			
			JButton sendButton = new JButton("发送");
			
			chosenUser = new String[100];
			//left list listener
			onlineUserList.addListSelectionListener(new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
				{
					int index = onlineUserList.getSelectedIndex();
					if(e.getValueIsAdjusting() == false && index != -1)
					{
						
						count++;
			
						//查找用户是否已经选择
						int j;
						for(j= 0; chosenUser[j] != null; j ++)
							if(chosenUser[j].compareTo((String) modelLeft.getElementAt(index)) == 0)
								break;
						//用户未选择
						if(j == count-1)
						{
							//将新选择的用户添加到chosenUser
							chosenUser[count-1] = (String) modelLeft.getElementAt(index);
							//显示新选择的用户
							modelRight.removeAllElements();
								for(int i = 0; i < count; i++)
									modelRight.addElement(chosenUser[i]);
						}
						
						//用户已选择
						else
						{
							JOptionPane.showMessageDialog(null, "已经选择过该用户！", "注意", JOptionPane.INFORMATION_MESSAGE);
							count--;
						}
					}
				}	
			});
			//right list listener
			chosenUserList.addListSelectionListener(new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
				{
					int index = chosenUserList.getSelectedIndex();
					if(index != -1)
					{
						int m = index;
						for( ; chosenUser[m] != null; m++)
							chosenUser[m] = chosenUser[m+1];
						chosenUser[m] = null;
						count--;
						
						
						modelRight.removeAllElements();
						for(int i = 0; i < count; i++)
							modelRight.addElement(chosenUser[i]);
						
						JOptionPane.showMessageDialog(null, "已取消选择！", "注意", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			//button listener
			sendButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					
					
					if(chosenUser[0] == null)
						JOptionPane.showMessageDialog(null, "没有选择用户！", "注意", JOptionPane.INFORMATION_MESSAGE);
					else{
						//向服务器发送要分享的日志
						client_Segment sendseg = new client_Segment();
						sendseg.head = 6;
						//设置要发送的好友，在这里默认为所有
						int i;
						for(i=0;i<modelRight.size();i++){
							 if(!modelRight.get(i).equals(username)){
								 sendseg.friend_list.add(modelRight.get(i));
							 }
						}
						sendseg.dairy.text=  JNTextArea.text;//jta.getText();
						sendseg.dairy.author=username;
						sendseg.dairy.public_flag = true;
						sendseg.dairy.title =  username+" "+JNTextArea.name;
						sendseg.dairy.zan_number =0;
						sendseg.dairy.date.setTime(new Date().getTime());
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
			});
			shareWordCardDialog.add(listPanel, BorderLayout.CENTER);
			shareWordCardDialog.add(sendButton, BorderLayout.SOUTH);
			shareWordCardDialog.setSize(400, 300);
			shareWordCardDialog.setLocation(400, 200);
			shareWordCardDialog.setVisible(true);
			
		}	
	}
	//点击登陆后输入账户和密码的小对话框
	public class Login_Dialog{
		
		//主对话框
		JDialog login_dialog = null;
		//用户名标签
		private JLabel username_label = new JLabel("用户名:");
		//密码标签
		private JLabel key_label = new JLabel("密   码:");
		//注册按钮
		private JButton register_button = new JButton("注册");
		//登陆按钮
		private JButton login_button2 = new JButton("登陆");
		//用户名填入区域
		private JTextField username_text = new JTextField(20);
		//密码填入区域
		private JPasswordField key_text = new JPasswordField(20);
		//用户名面板
		private JPanel username_panel = new JPanel(new FlowLayout());
		//密码面板
		private JPanel key_panel = new JPanel(new FlowLayout());
		//存放登陆、注册按钮的面板
		private JPanel button_panel = new JPanel(new FlowLayout());
		//存放上面三个面板的主面板
		private JPanel main_panel = new JPanel();
		/*按钮图片设置，以后美化时候需要增添
		private ImageIcon logicon = new ImageIcon("images/登陆banner.png");
		private ImageIcon icon2 = new ImageIcon("images/登陆按键前.png");
		private ImageIcon icon3 = new ImageIcon("images/登陆按键后.png");
		private ImageIcon icon4 = new ImageIcon("images/注册按键前.png");
		private ImageIcon icon5 = new ImageIcon("images/注册按键后.png");
		*/
		public Login_Dialog(final Socket socket){
			
			//设置登陆按钮图片，以后增添
			/*loginbutton.setIcon(icon2);
			loginbutton.setRolloverIcon(icon3);
			loginbutton.setBorder(null);
			//设置注册按钮图片
			newuser.setIcon(icon4);
			newuser.setRolloverIcon(icon5);
			newuser.setBorder(null);*/
			
			username_panel.add(username_label,FlowLayout.LEFT);
			username_panel.add(username_text,FlowLayout.CENTER);
			key_panel.add(key_label,FlowLayout.LEFT);
			key_panel.add(key_text,FlowLayout.CENTER);
			button_panel.add(login_button2,FlowLayout.LEFT);
			button_panel.add(register_button,FlowLayout.CENTER);
			
			//将面板按照Y轴排放
			BoxLayout box1 = new BoxLayout(main_panel,BoxLayout.Y_AXIS);
			main_panel.setLayout(box1);
			main_panel.add(username_panel);
			main_panel.add(key_panel);
			main_panel.add(button_panel);
			
			//给登陆按钮添加监听
			login_button2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String uname=username_text.getText();
					String key=String.valueOf(key_text.getPassword());
					//发送用户登录信息
					//BufferedWriter bufout= new BufferedWriter( new OutputStreamWriter(toServer));
					try {
						
						client_Segment s= new client_Segment();
						s.user.name = uname;
						s.user.password = key;
						s.head = 1;
						ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
				        send.writeObject(s);
				        send.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			//给注册按钮添加监听
			register_button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					login_dialog.dispose();
					login_dialog=null;
					//dairy_register.setup();
					
					//reg.setup(toServer);
				}
			}
			);
		}
		public void setup(){
			login_dialog = new JDialog();

			//设置登陆界面的图片
			//login_dialog.add(new JLabel(logicon),BorderLayout.NORTH);
			login_dialog.add(main_panel,BorderLayout.CENTER);
			
			login_dialog.setSize(290,230);
			login_dialog.setLocation(400,200);
			login_dialog.setResizable(false);
			login_dialog.setVisible(true);
		}
		public void setdown(){
			JOptionPane.showMessageDialog(null, "登陆成功");
			//关闭登陆对话框
			login_dialog.dispose();
			login_dialog=null;
			//隐藏主界面的登陆按钮
			login_button.setVisible(false);
			welcome_label.setText("welcome： "+username);
			//显示主界面的按钮
			welcome_label.setVisible(true);
			logout_button.setVisible(true);
			square_button.setVisible(true);
			up_button.setVisible(true);
			share_button.setVisible(true);
			
			//其他显示的设置，以后需要修改
			/*user.setVisible(true);
			beforlog.setVisible(false);
			panel7.setVisible(false);
			afterlog.setVisible(true);
			panel8.add(afterlog,BorderLayout.CENTER);
			panel8.add(user,BorderLayout.WEST);
			*/
		}
	}
	//点击登陆后输入账户和密码的小对话框
		public class register_Dialog{
			
			//主对话框
			JDialog login_dialog = null;
			//用户名标签
			private JLabel username_label = new JLabel("用户名:");
			//密码标签
			private JLabel key_label = new JLabel("密   码:");
			//注册按钮
			private JButton register_button = new JButton("退出");
			//登陆按钮
			private JButton login_button2 = new JButton("注册");
			//用户名填入区域
			private JTextField username_text = new JTextField(20);
			//密码填入区域
			private JPasswordField key_text = new JPasswordField(20);
			//用户名面板
			private JPanel username_panel = new JPanel(new FlowLayout());
			//密码面板
			private JPanel key_panel = new JPanel(new FlowLayout());
			//存放登陆、注册按钮的面板
			private JPanel button_panel = new JPanel(new FlowLayout());
			//存放上面三个面板的主面板
			private JPanel main_panel = new JPanel();
			/*按钮图片设置，以后美化时候需要增添
			private ImageIcon logicon = new ImageIcon("images/登陆banner.png");
			private ImageIcon icon2 = new ImageIcon("images/登陆按键前.png");
			private ImageIcon icon3 = new ImageIcon("images/登陆按键后.png");
			private ImageIcon icon4 = new ImageIcon("images/注册按键前.png");
			private ImageIcon icon5 = new ImageIcon("images/注册按键后.png");
			*/
			public register_Dialog(final Socket socket){
				
				//设置登陆按钮图片，以后增添
				/*loginbutton.setIcon(icon2);
				loginbutton.setRolloverIcon(icon3);
				loginbutton.setBorder(null);
				//设置注册按钮图片
				newuser.setIcon(icon4);
				newuser.setRolloverIcon(icon5);
				newuser.setBorder(null);*/
				
				username_panel.add(username_label,FlowLayout.LEFT);
				username_panel.add(username_text,FlowLayout.CENTER);
				key_panel.add(key_label,FlowLayout.LEFT);
				key_panel.add(key_text,FlowLayout.CENTER);
				button_panel.add(login_button2,FlowLayout.LEFT);
				button_panel.add(register_button,FlowLayout.CENTER);
				
				//将面板按照Y轴排放
				BoxLayout box1 = new BoxLayout(main_panel,BoxLayout.Y_AXIS);
				main_panel.setLayout(box1);
				main_panel.add(username_panel);
				main_panel.add(key_panel);
				main_panel.add(button_panel);
				
				//给登陆按钮添加监听
				login_button2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String uname=username_text.getText();
						String key=String.valueOf(key_text.getPassword());
						//发送用户登录信息
						//BufferedWriter bufout= new BufferedWriter( new OutputStreamWriter(toServer));
						try {
							
							client_Segment s= new client_Segment();
							s.user.name = uname;
							s.user.password = key;
							s.head = 2;
							ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
					        send.writeObject(s);
					        send.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
				//给注册按钮添加监听
				register_button.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						login_dialog.dispose();
						login_dialog=null;
						//reg.setup(toServer);
					}
				}
				);
			}
			public void setup(){
				login_dialog = new JDialog();

				//设置登陆界面的图片
				//login_dialog.add(new JLabel(logicon),BorderLayout.NORTH);
				login_dialog.add(main_panel,BorderLayout.CENTER);
				
				login_dialog.setSize(290,230);
				login_dialog.setLocation(400,200);
				login_dialog.setResizable(false);
				login_dialog.setVisible(true);
			}
			public void setdown(){
				JOptionPane.showMessageDialog(null, "注册成功");
				//关闭登陆对话框
				login_dialog.dispose();
				login_dialog=null;
				//隐藏主界面的登陆按钮
				login_button.setVisible(false);
				welcome_label.setText("welcome： "+username);
				//显示主界面的按钮
				welcome_label.setVisible(true);
				logout_button.setVisible(true);
				square_button.setVisible(true);
				up_button.setVisible(true);
				share_button.setVisible(true);
				recv_button.setVisible(true);
				//其他显示的设置，以后需要修改
				/*user.setVisible(true);
				beforlog.setVisible(false);
				panel7.setVisible(false);
				afterlog.setVisible(true);
				panel8.add(afterlog,BorderLayout.CENTER);
				panel8.add(user,BorderLayout.WEST);
				*/
			}
		}
}
