package UI;

import segment.User;
import segment.client_Segment;
import segment.server_Segment;
import segment.Diary_Segment;

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

//������
@SuppressWarnings("serial")
public class Diary extends JFrame{
	
	//��½��ť
	private JButton login_button = new JButton("��½");
	//ע�ᰴť
	private JButton register_button = new JButton("ע��");
	//ע����ť
	private JButton logout_button = new JButton("ע��");
	//�㳡��ť
	private JButton square_button = new JButton("�㳡");
	//�յ���ť
    private JButton recv_button = new JButton("����");
	//�ϴ��ռǰ�ť
	private JButton up_button = new JButton("�ϴ��ռ�");
	//�����ռǰ�ť
	private JButton share_button = new JButton("�����ռ�");
	
	//��½��Ļ�ӭ��ǩ
	JLabel welcome_label = new JLabel(); 
	//��������н����İ�ť��塪���������
	JPanel netpanel = new JPanel(); 
	//��ʱ��ʾ�����Ժ���Ҫɾ��
	private JTextArea jta = new JTextArea(); 
	
	//��½С����
	LoginFrame diary_login;
	
	//ע�����
	RegisterFrame diary_register;
	
	//��½�㳡����
	square2 users_square;
	//�û�����
	String username;
	int icon;
	//�����û���Ϣ
	public ArrayList<String> users_list = new ArrayList<String>();
	
	//�û����յ�����־
	private ArrayList<Diary_Segment> dairy_recv = new ArrayList<Diary_Segment>();
	
	//ObjectOutputStream send;
	//ObjectInputStream recv;
	public Diary(Socket socket){
		//�����������
		netpanel.setLayout(new FlowLayout(0,4,1));
	    netpanel.add(login_button);
	    netpanel.add(register_button);
	    netpanel.add(welcome_label);
	    netpanel.add(logout_button);
	    netpanel.add(square_button);
        netpanel.add(up_button);
        netpanel.add(share_button);
        netpanel.add(recv_button);
        
	    //���ص�½״̬�ı�ǩ
	    welcome_label.setVisible(false);
	    logout_button.setVisible(false);
	    square_button.setVisible(false);
	    up_button.setVisible(false);
	    share_button.setVisible(false);
	    recv_button.setVisible(false);
	    
	    //���������
	    setLayout(new BorderLayout());
	    add(netpanel, BorderLayout.NORTH);
	    add(new JScrollPane(jta), BorderLayout.CENTER);

	    setTitle("Client");
	    setSize(500, 300);
	    setVisible(true);
	    setLocation(100,100);
		setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     // It is necessary to show the frame here!
	    //u.setSize(720, 550);
		
		//u.setVisible(true);
		//u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //��Ӽ���
	    
	    //��½��ť����
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
  		//�ϴ���־����
  		up_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  				up_dairy(socket);
  			}
  		});
  		users_square = new square2(socket,icon);
  		square_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  			    up_square(socket);
  			}
  		});
  		//ע����ť����
  		logout_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  			    up_logout(socket);
  			}
  		});
  		//����ť����
  		share_button.addActionListener(new ActionListener(){
  			public void actionPerformed(ActionEvent e){
  			    up_share(socket);
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
  		//���������߳�
  		client_listen_pthread task=new client_listen_pthread(socket);
		new Thread(task).start();
	}
	public void up_share(Socket socket){
		//�����������Ҫ�������־
		new Sharediary(socket); 
	}
	public void up_logout(Socket socket){
		//�����������ע��
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
			//���͵�������
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
		//���������������
		client_Segment sendseg = new client_Segment();
		sendseg.head = 4;
		try {
			//���͵�������
			ObjectOutputStream send =null;
			send = new ObjectOutputStream(socket.getOutputStream());
			send.writeObject(sendseg);
	        send.flush();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
	}
	//�ϴ���־
	public void up_dairy(Socket socket){
		String text = jta.getText();
		//���ñ���
		client_Segment sendseg = new client_Segment();
		sendseg.head = 3;
		sendseg.dairy.text=text;
		sendseg.dairy.author=username;
		sendseg.dairy.public_flag = true;
		sendseg.dairy.title = "123";
		sendseg.dairy.zan_number =0;
		sendseg.dairy.date.setTime(new Date().getTime());
		try {
			//���͵�������
			ObjectOutputStream send =null;
			send = new ObjectOutputStream(socket.getOutputStream());
			send.writeObject(sendseg);
	        send.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
	}
	//�ͻ��˼����߳�
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
	        	 //��½�ɹ�
	        	 case 1:{
	        		 username = recvseg.user.name;
	        		 icon = recvseg.user.icon;
	        		 int i=0;
	        		 for(;i<recvseg.users_list.size();i++){
	        			 users_list.add(recvseg.users_list.get(i));
	        		 }
	        		 for(i=0;i<users_list.size();i++){
	        			 jta.append(users_list.get(i)+"\n");
	        		 }
	        		 JOptionPane.showMessageDialog(null, "��½�ɹ�");
	        	     diary_login.setVisible(false);
	        	    //����������ĵ�½��ť
	 				login_button.setVisible(false);
	 				register_button.setVisible(false);
	 				welcome_label.setText("welcome�� "+username);
	 				//��ʾ������İ�ť
	 				welcome_label.setVisible(true);
	 				logout_button.setVisible(true);
	 				square_button.setVisible(true);
	 				up_button.setVisible(true);
	 				share_button.setVisible(true);
	 				recv_button.setVisible(true);
	        	 }break;
	        	 //��½ʧ��
	        	 case 2:{
	        		 if(recvseg.logfault == 1){
	        			 JOptionPane.showMessageDialog(null, "��½ʧ�ܣ������ڸ��û�����");
	        		 }else if(recvseg.logfault == 2){
	        			 JOptionPane.showMessageDialog(null, "��½ʧ�ܣ��������");
	        		 }else if(recvseg.logfault == 3){
	        			 JOptionPane.showMessageDialog(null, "���˺��Ѿ���½��");
	        		 }else{
	        			 System.out.println("transfor error");
	        		 }
	        	 }break;
	        	 //�㳡��½����
	        	 case 3:{
	        		 users_square.seticon(icon);
	        		 users_square.setup(socket, recvseg,username);
	        	 }break;
	        	 //ע��ɹ�
	        	 case 4:{
	        		 username = recvseg.user.name;
	        		 icon = recvseg.user.icon;
	        		 int i=0;
	        		 for(;i<recvseg.users_list.size();i++){
	        			 users_list.add(recvseg.users_list.get(i));
	        		 }
	        		 for(i=0;i<users_list.size();i++){
	        			 jta.append(users_list.get(i)+"\n");
	        		 }
	        		 diary_register.setVisible(false);
	        		 //����������ĵ�½��ť
	 				login_button.setVisible(false);
	 				register_button.setVisible(false);
	 				welcome_label.setText("welcome�� "+username);
	 				//��ʾ������İ�ť
	 				welcome_label.setVisible(true);
	 				logout_button.setVisible(true);
	 				square_button.setVisible(true);
	 				up_button.setVisible(true);
	 				share_button.setVisible(true);
	 				recv_button.setVisible(true);
	        	 }break;
	        	 //ע��ʧ��
	        	 case 5:{
	        		 JOptionPane.showMessageDialog(null, "���˺��Ѿ���ע�ᣡ");
	        	 }break;
	        	 //�㳡������Ӧ
	        	 case 6:{
	        		 int i=0;
	        		 for(;i<recvseg.dairylist.size();i++){
	        			 users_square.add_result(recvseg.dairylist.get(i));
	        		 }
	        		 users_square.showresult(socket);
	        	 }break;
	        	 //���������û��б�
	        	 case 7:{
	        		 users_list.add(recvseg.user.name);
	        		 jta.append(recvseg.user.name+"\n");
	        	 }break;
	        	 //�յ������ͻ��������־
	        	 case 8:{
	        		 int i=0;
	        		 for(;i<recvseg.dairylist.size();i++){
	        			 dairy_recv.add(recvseg.dairylist.get(i));
	        		 }
	        		 jta.append(dairy_recv.get(0).text+"\n");
	        	 }break;
	        	 //���û�����
	        	 case 9:{
	        		 //����users_list���ҵ���
	        		 int i=0;
	        		 for(;i<users_list.size();i++){
	        			 if(users_list.get(i).equals(recvseg.user.name)){
	        				 break;
	        			 }
	        		 }
	        		 users_list.remove(i);
	        		 jta.setText("");
	        		 for(i=0;i<users_list.size();i++){
	        			 jta.append(users_list.get(i)+"\n");
	        		 }
	        	 }break;
	        	 case 10:{//�㳡������Ӧ
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
		
		//�洢��ѡ���û�
		String[] chosenUser;
		//��ѡ���û�����
		int count = 0;
		
		public Sharediary(Socket socket)
		{
			shareWordCardDialog = new JDialog(Diary.this,"�����ʿ�");
			shareWordCardDialog.setLayout(new BorderLayout());
			
			JPanel listPanel = new JPanel(new GridLayout(1,2));
			//left list
			DefaultListModel<String> modelLeft = new DefaultListModel<String>();
			onlineUserList = new JList<String>(modelLeft);
			JScrollPane scrollPaneLeft = new JScrollPane(onlineUserList);
			onlineUserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//��������û�
			for(int i=0;i<users_list.size();i++){
				modelLeft.addElement(users_list.get(i));
			}
			scrollPaneLeft.setBorder(new TitledBorder("��ѡ�������û�"));
			//right list
			DefaultListModel<String> modelRight = new DefaultListModel<String>();
			chosenUserList = new JList<String>(modelRight);
			JScrollPane scrollPaneRight = new JScrollPane(chosenUserList);
			chosenUserList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPaneRight.setBorder(new TitledBorder("��ѡ���û�������ȡ��ѡ��"));
			
			listPanel.add(scrollPaneLeft);
			listPanel.add(scrollPaneRight);
			
			JButton sendButton = new JButton("����");
			
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
			
						//�����û��Ƿ��Ѿ�ѡ��
						int j;
						for(j= 0; chosenUser[j] != null; j ++)
							if(chosenUser[j].compareTo((String) modelLeft.getElementAt(index)) == 0)
								break;
						//�û�δѡ��
						if(j == count-1)
						{
							//����ѡ����û���ӵ�chosenUser
							chosenUser[count-1] = (String) modelLeft.getElementAt(index);
							//��ʾ��ѡ����û�
							modelRight.removeAllElements();
								for(int i = 0; i < count; i++)
									modelRight.addElement(chosenUser[i]);
						}
						
						//�û���ѡ��
						else
						{
							JOptionPane.showMessageDialog(null, "�Ѿ�ѡ������û���", "ע��", JOptionPane.INFORMATION_MESSAGE);
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
						
						JOptionPane.showMessageDialog(null, "��ȡ��ѡ��", "ע��", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
			//button listener
			sendButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(chosenUser[0] == null)
						JOptionPane.showMessageDialog(null, "û��ѡ���û���", "ע��", JOptionPane.INFORMATION_MESSAGE);
					else{
						//�����������Ҫ�������־
						client_Segment sendseg = new client_Segment();
						sendseg.head = 6;
						//����Ҫ���͵ĺ��ѣ�������Ĭ��Ϊ����
						int i;
						for(i=0;i<modelRight.size();i++){
							 if(!modelRight.get(i).equals(username)){
								 sendseg.friend_list.add(modelRight.get(i));
							 }
						}
						sendseg.dairy.text=jta.getText();
						sendseg.dairy.author=username;
						sendseg.dairy.public_flag = true;
						sendseg.dairy.title = "123";
						sendseg.dairy.zan_number =0;
						sendseg.dairy.date.setTime(new Date().getTime());
						try {
							//���͵�������
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
	//�����½�������˻��������С�Ի���
	public class Login_Dialog{
		
		//���Ի���
		JDialog login_dialog = null;
		//�û�����ǩ
		private JLabel username_label = new JLabel("�û���:");
		//�����ǩ
		private JLabel key_label = new JLabel("��   ��:");
		//ע�ᰴť
		private JButton register_button = new JButton("ע��");
		//��½��ť
		private JButton login_button2 = new JButton("��½");
		//�û�����������
		private JTextField username_text = new JTextField(20);
		//������������
		private JPasswordField key_text = new JPasswordField(20);
		//�û������
		private JPanel username_panel = new JPanel(new FlowLayout());
		//�������
		private JPanel key_panel = new JPanel(new FlowLayout());
		//��ŵ�½��ע�ᰴť�����
		private JPanel button_panel = new JPanel(new FlowLayout());
		//��������������������
		private JPanel main_panel = new JPanel();
		/*��ťͼƬ���ã��Ժ�����ʱ����Ҫ����
		private ImageIcon logicon = new ImageIcon("images/��½banner.png");
		private ImageIcon icon2 = new ImageIcon("images/��½����ǰ.png");
		private ImageIcon icon3 = new ImageIcon("images/��½������.png");
		private ImageIcon icon4 = new ImageIcon("images/ע�ᰴ��ǰ.png");
		private ImageIcon icon5 = new ImageIcon("images/ע�ᰴ����.png");
		*/
		public Login_Dialog(Socket socket){
			
			//���õ�½��ťͼƬ���Ժ�����
			/*loginbutton.setIcon(icon2);
			loginbutton.setRolloverIcon(icon3);
			loginbutton.setBorder(null);
			//����ע�ᰴťͼƬ
			newuser.setIcon(icon4);
			newuser.setRolloverIcon(icon5);
			newuser.setBorder(null);*/
			
			username_panel.add(username_label,FlowLayout.LEFT);
			username_panel.add(username_text,FlowLayout.CENTER);
			key_panel.add(key_label,FlowLayout.LEFT);
			key_panel.add(key_text,FlowLayout.CENTER);
			button_panel.add(login_button2,FlowLayout.LEFT);
			button_panel.add(register_button,FlowLayout.CENTER);
			
			//����尴��Y���ŷ�
			BoxLayout box1 = new BoxLayout(main_panel,BoxLayout.Y_AXIS);
			main_panel.setLayout(box1);
			main_panel.add(username_panel);
			main_panel.add(key_panel);
			main_panel.add(button_panel);
			
			//����½��ť��Ӽ���
			login_button2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String uname=username_text.getText();
					String key=String.valueOf(key_text.getPassword());
					//�����û���¼��Ϣ
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
			
			//��ע�ᰴť��Ӽ���
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
			login_dialog = new JDialog(Diary.this,"��½",true);

			//���õ�½�����ͼƬ
			//login_dialog.add(new JLabel(logicon),BorderLayout.NORTH);
			login_dialog.add(main_panel,BorderLayout.CENTER);
			
			login_dialog.setSize(290,230);
			login_dialog.setLocation(400,200);
			login_dialog.setResizable(false);
			login_dialog.setVisible(true);
		}
		public void setdown(){
			JOptionPane.showMessageDialog(null, "��½�ɹ�");
			//�رյ�½�Ի���
			login_dialog.dispose();
			login_dialog=null;
			//����������ĵ�½��ť
			login_button.setVisible(false);
			welcome_label.setText("welcome�� "+username);
			//��ʾ������İ�ť
			welcome_label.setVisible(true);
			logout_button.setVisible(true);
			square_button.setVisible(true);
			up_button.setVisible(true);
			share_button.setVisible(true);
			
			//������ʾ�����ã��Ժ���Ҫ�޸�
			/*user.setVisible(true);
			beforlog.setVisible(false);
			panel7.setVisible(false);
			afterlog.setVisible(true);
			panel8.add(afterlog,BorderLayout.CENTER);
			panel8.add(user,BorderLayout.WEST);
			*/
		}
	}
	//�����½�������˻��������С�Ի���
		public class register_Dialog{
			
			//���Ի���
			JDialog login_dialog = null;
			//�û�����ǩ
			private JLabel username_label = new JLabel("�û���:");
			//�����ǩ
			private JLabel key_label = new JLabel("��   ��:");
			//ע�ᰴť
			private JButton register_button = new JButton("�˳�");
			//��½��ť
			private JButton login_button2 = new JButton("ע��");
			//�û�����������
			private JTextField username_text = new JTextField(20);
			//������������
			private JPasswordField key_text = new JPasswordField(20);
			//�û������
			private JPanel username_panel = new JPanel(new FlowLayout());
			//�������
			private JPanel key_panel = new JPanel(new FlowLayout());
			//��ŵ�½��ע�ᰴť�����
			private JPanel button_panel = new JPanel(new FlowLayout());
			//��������������������
			private JPanel main_panel = new JPanel();
			/*��ťͼƬ���ã��Ժ�����ʱ����Ҫ����
			private ImageIcon logicon = new ImageIcon("images/��½banner.png");
			private ImageIcon icon2 = new ImageIcon("images/��½����ǰ.png");
			private ImageIcon icon3 = new ImageIcon("images/��½������.png");
			private ImageIcon icon4 = new ImageIcon("images/ע�ᰴ��ǰ.png");
			private ImageIcon icon5 = new ImageIcon("images/ע�ᰴ����.png");
			*/
			public register_Dialog(Socket socket){
				
				//���õ�½��ťͼƬ���Ժ�����
				/*loginbutton.setIcon(icon2);
				loginbutton.setRolloverIcon(icon3);
				loginbutton.setBorder(null);
				//����ע�ᰴťͼƬ
				newuser.setIcon(icon4);
				newuser.setRolloverIcon(icon5);
				newuser.setBorder(null);*/
				
				username_panel.add(username_label,FlowLayout.LEFT);
				username_panel.add(username_text,FlowLayout.CENTER);
				key_panel.add(key_label,FlowLayout.LEFT);
				key_panel.add(key_text,FlowLayout.CENTER);
				button_panel.add(login_button2,FlowLayout.LEFT);
				button_panel.add(register_button,FlowLayout.CENTER);
				
				//����尴��Y���ŷ�
				BoxLayout box1 = new BoxLayout(main_panel,BoxLayout.Y_AXIS);
				main_panel.setLayout(box1);
				main_panel.add(username_panel);
				main_panel.add(key_panel);
				main_panel.add(button_panel);
				
				//����½��ť��Ӽ���
				login_button2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String uname=username_text.getText();
						String key=String.valueOf(key_text.getPassword());
						//�����û���¼��Ϣ
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
				
				//��ע�ᰴť��Ӽ���
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
				login_dialog = new JDialog(Diary.this,"ע��",true);

				//���õ�½�����ͼƬ
				//login_dialog.add(new JLabel(logicon),BorderLayout.NORTH);
				login_dialog.add(main_panel,BorderLayout.CENTER);
				
				login_dialog.setSize(290,230);
				login_dialog.setLocation(400,200);
				login_dialog.setResizable(false);
				login_dialog.setVisible(true);
			}
			public void setdown(){
				JOptionPane.showMessageDialog(null, "ע��ɹ�");
				//�رյ�½�Ի���
				login_dialog.dispose();
				login_dialog=null;
				//����������ĵ�½��ť
				login_button.setVisible(false);
				welcome_label.setText("welcome�� "+username);
				//��ʾ������İ�ť
				welcome_label.setVisible(true);
				logout_button.setVisible(true);
				square_button.setVisible(true);
				up_button.setVisible(true);
				share_button.setVisible(true);
				recv_button.setVisible(true);
				//������ʾ�����ã��Ժ���Ҫ�޸�
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
