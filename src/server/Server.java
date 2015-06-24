package Server;

import Segment.Diary_Segment;
import Segment.client_Segment;
import Segment.server_Segment;
import Segment.Rank;
import Database.Client_DB;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.awt.*;

import javax.swing.*;

public class Server extends JFrame {
  //连接数据库
  Client_DB db = new Client_DB();
	
  //客户间通信的消息盒子
  static final int maxclient=100;
  server_tcb [] tcb_pool = new server_tcb[maxclient];
  
  //服务器状态信息显示区
  private JTextArea jta = new JTextArea();
  
  //当前在线客户端数量
  int client_number=0;
  
  public static void main(String[] args) {
    new Server();
  }

  public Server() {
    // Place text area on the frame
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);

    setTitle("Server");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    //初始化tcb池
    for(int i=0;i<maxclient;i++)
    	tcb_pool[i]=null;
    
    clean();
    try {
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8000);
      jta.append("Server started at " + new Date() + '\n');

      // Create data input and output streams
      //ObjectInputStream recv = new ObjectInputStream(socket.getInputStream());
      //ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());

	  while (true) {
	    // Listen for a new connection request
	    Socket socket = serverSocket.accept();
	    
	    //已经使用的tcb是否超过数量限制
	    if(client_number>=maxclient){
	    	break;
	    }
	    
	    //寻找可以使用的tcb
	    int i=0;
	    for(;i<maxclient;i++){	
	    	if(tcb_pool[i]==null){
	    		break;
	    	}
	    }
	    
	    //给该用户分配tcb
	    tcb_pool[i]=new server_tcb();
	    tcb_pool[i].lock = new ReentrantLock();
	    tcb_pool[i].client_condition = tcb_pool[i].lock.newCondition();
	    tcb_pool[i].socket = socket;
	    
	    // Find the client's host name, and IP address
	    InetAddress inetAddress = socket.getInetAddress();
	    jta.append("Client " + i + "'s host name is "
	      + inetAddress.getHostName() + "\n");
	    jta.append("Client " + i + "'s IP Address is "
	      + inetAddress.getHostAddress() + "\n");
	
	    // Display the client number
	    jta.append("Starting thread for client " + i +
	      " at " + new Date() + '\n');
	    
	    // Create a new thread for the connection
	    HandleAClient task = new HandleAClient(i);   
	    // Start the new thread
	    new Thread(task).start();
	    // Increment client_number
	    client_number++;
	  }
	  serverSocket.close();
    }
    catch(IOException ex) {
      System.err.println(ex);
      
    }
  }
  //重启后，清理数据库中的在线用户
  public void clean(){
	  try {
		    ArrayList<String> allon=db.getAllonLineuser();
			for(int i=0;i<allon.size();i++){
				db.deleteOnlineUser(allon.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  //面向客户请求的监听线程
  class HandleAClient implements Runnable {
	  
    //客户端识别标志
    int client_tag; 
    Socket socket;
    /** Construct a thread */
    public HandleAClient(int number) {
      client_tag=number;
      socket = tcb_pool[client_tag].socket;
    }

    /** Run a thread */

	public void run() {
      try {
        // Create data input and output streams
    	ObjectInputStream recv=null;
    	ObjectOutputStream send=null;
    	
        // Continuously serve the client
        while (true) {
        	//接收来自客户端的数据报文
        	recv = new ObjectInputStream(socket.getInputStream());
	        client_Segment recvseg = null;
			try {			
				recvseg = (client_Segment)recv.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(recvseg.head);
			//判断请求类型
          switch(recvseg.head){
          //登陆请求
          case 1:{
        	  try {
        		  server_Segment sendseg = new server_Segment();
        		  //首先检查该账户是否存在
        		  //再检查密码是否正确
        		  //再查找该账户是否在线
        		  
        		  if(!db.findAccount(recvseg.user.name)){
        			  //先判断是否存在用户名
        			  //如果不存在
        			  sendseg.head = 2;
        			  sendseg.logfault =1;
        			  
        		  }else if(!db.findAccount(recvseg.user.name, recvseg.user.password)){
        			  //再检查密码是否正确
        			  sendseg.head = 2;
        			  sendseg.logfault =2;
        		  }else if(db.findOnlineUser(recvseg.user.name)){
        			  
        			  //再检查是否已经在线
        			  //如果已经在线
        			  sendseg.head = 2;
        			  sendseg.logfault =3;
        			  
        		  }else{
        			  //如果登陆成功，加入在线用户列表
        			  db.addOnlineUser(recvseg.user.name,client_tag);
        			  
        			  sendseg.user.name = recvseg.user.name;
        			  //更新其他在线用户的在线用户列表
        			  int i=0;
        			  for(;i<maxclient;i++){
        				  if(tcb_pool[i] != null && i!= client_tag){
        					  sendseg.head = 7;
        					  send = new ObjectOutputStream(tcb_pool[i].socket.getOutputStream());
        	            	  send.writeObject(sendseg);
        	            	  send.flush();
        				  }
        			  }
        			  //给自己返回登陆反馈
            		  sendseg.head = 1;
        			  sendseg.user.name = recvseg.user.name;
        			  sendseg.user.icon = db.geticon(recvseg.user.name);
        			  //返回所有在线用户信息
        			  sendseg.users_list = db.getAllonLineuser();
        		  }  		    			  
        		  send = new ObjectOutputStream(socket.getOutputStream());
            	  send.writeObject(sendseg);
            	  send.flush();
  				} catch (SQLException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
          }break;
          //注册请求
          case 2:{
        	  
        	  try {
        		  server_Segment sendseg = new server_Segment();
        		  //首先检查该账户是否存在
        		  
        		  if(db.findAccount(recvseg.user.name)){
        			  //先判断是否存在用户名
        			  //如果不存在
        			  sendseg.head = 5;
        			  
        		  }else{
        			  db.addAccount(recvseg.user.name, recvseg.user.password, recvseg.user.icon);
        			  //加入在线用户列表
        			  db.addOnlineUser(recvseg.user.name,client_tag);
        			  
        			  sendseg.user.name = recvseg.user.name;
        			  //更新其他在线用户的在线用户列表
        			  int i=0;
        			  for(;i<maxclient;i++){
        				  if(tcb_pool[i] != null && i!= client_tag){
        					  sendseg.head = 7;
        					  send = new ObjectOutputStream(tcb_pool[i].socket.getOutputStream());
        	            	  send.writeObject(sendseg);
        	            	  send.flush();
        				  }
        			  }
        			  //给自己返回登陆反馈
            		  sendseg.head = 4;
        			  sendseg.user.name = recvseg.user.name;
        			  //返回所有在线用户信息
        			  sendseg.users_list = db.getAllonLineuser();
                      sendseg.user.icon = db.geticon(recvseg.user.name);
        		  }  		    			  
        		  send = new ObjectOutputStream(socket.getOutputStream());
            	  send.writeObject(sendseg);
            	  send.flush();
  				} catch (SQLException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
          }break;
          //上传日志请求
          case 3:{
        	  try {
				db.adddairy(recvseg.dairy.author, recvseg.dairy.title, recvseg.dairy.zan_number, 
						  recvseg.dairy.text, recvseg.dairy.date.getTime());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          }break;
          //登陆日记广场请求
          case 4:{
        	    try {
        	    	ArrayList<Diary_Segment> dairys = db.getalldairys();
        	    	dairys.sort(new DairyComparator());
        	    	for(int j=0;j<dairys.size();j++){
        	    		int icon = db.geticon(dairys.get(j).author);
        	    		dairys.get(j).icon = icon;
        	    	}
        	    	server_Segment sendseg = new server_Segment();
        	    	int i;
        	    	for(i=0;i<dairys.size();i++){
        	    		sendseg.dairylist.add(dairys.get(i));
        	    	}
        	    	
        	    	ArrayList<Rank> rank_list = db.getallrank();
        	    	rank_list.sort(new RankComparator());
        	    	
        	    	for(i=0;i<rank_list.size();i++){
        	    		sendseg.rank_list.add(rank_list.get(i));
        	    	}
        	    	
        	    	sendseg.head=3;
        	    	send = new ObjectOutputStream(socket.getOutputStream());
              	    send.writeObject(sendseg);
              	    send.flush();
  				} catch (SQLException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
          }break;
          //广场搜索请求
          case 5:{
        	  try {
      	    	ArrayList<Diary_Segment> dairys = db.getdairys(recvseg.user.name);
      	    	server_Segment sendseg = new server_Segment();
      	    	int i=0;
      	    	for(;i<dairys.size();i++){
      	    		sendseg.dairylist.add(dairys.get(i));
      	    	}
      	    	sendseg.head=6;
      	    	send = new ObjectOutputStream(socket.getOutputStream());
            	send.writeObject(sendseg);
            	send.flush();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          }break;
          //分享日志请求
          case 6:{  
        	  try {
        		  
            	  //设置转发报文
	        	  Diary_Segment dairy_seg= new Diary_Segment();
	        	  dairy_seg.author=recvseg.dairy.author;
	        	  dairy_seg.title=recvseg.dairy.title;
	        	  dairy_seg.text=recvseg.dairy.text;
	        	  dairy_seg.zan_number=recvseg.dairy.zan_number;
	        	  dairy_seg.public_flag=recvseg.dairy.public_flag;
	        	  
	        	  server_Segment sendseg = new server_Segment();
	        	  sendseg.dairylist.add(dairy_seg);
	        	  sendseg.head=8;
	        	  
	        	  //转发给其他客户
	        	  int i;
	        	  for(i=0;i<recvseg.friend_list.size();i++){
	        		  //找到对应的tcb
					 int connfd=db.getconnfd(recvseg.friend_list.get(i));
					 if(connfd != client_tag){
				      	 send = new ObjectOutputStream(tcb_pool[connfd].socket.getOutputStream());
				         send.writeObject(sendseg);
				         send.flush();	  
					 }
	        	  }   	
        	  } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          }break;
          //客户注销请求
          case 7:{
        	  try {
        		  //从在线用户中删去
        		  db.deleteOnlineUser(recvseg.user.name);
        		  //通知其他用户删去在线用户信息
        		  server_Segment sendseg = new server_Segment();
	        	  sendseg.head=9;
	        	  sendseg.user.name = recvseg.user.name;
	        	  
	        	  ArrayList<Integer> connfd_list = db.getallconnfd();
	        	  //转发给其他客户
	        	  int i;
	        	  for(i=0;i<connfd_list.size();i++){
			      	 send = new ObjectOutputStream(tcb_pool[connfd_list.get(i)].socket.getOutputStream());
			         send.writeObject(sendseg);
			         send.flush();	  
	        	  }
  				} catch (SQLException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
          }break;
          //用户对日记点赞
          case 8:{
        	  try {
        		  db.updateZan(recvseg.dairy.author, recvseg.dairy.date.getTime());
        		  db.updateuserZan(recvseg.dairy.author);
        		  
  				} catch (SQLException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
          }break;
          //广场更新
          case 9:{
        	  try {
      	    	ArrayList<Diary_Segment> dairys = db.getalldairys();
      	    	dairys.sort(new DairyComparator());
      	    	for(int j=0;j<dairys.size();j++){
    	    		int icon = db.geticon(dairys.get(j).author);
    	    		dairys.get(j).icon = icon;
    	    	}
      	    	
      	    	server_Segment sendseg = new server_Segment();
      	    	int i;
      	    	for(i=0;i<dairys.size();i++){
      	    		sendseg.dairylist.add(dairys.get(i));
      	    	}
      	    	
      	    	ArrayList<Rank> rank_list = db.getallrank();
      	    	rank_list.sort(new RankComparator());
      	    	
      	    	for(i=0;i<rank_list.size();i++){
      	    		sendseg.rank_list.add(rank_list.get(i));
      	    	}
      	    	
      	    	sendseg.head=10;
      	    	send = new ObjectOutputStream(socket.getOutputStream());
            	    send.writeObject(sendseg);
            	    send.flush();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
          };
          case 999:
        	  try {
				db.deleteDiary(recvseg.dairy.author, recvseg.dairy.date.getTime());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	  try {
      	    	ArrayList<Diary_Segment> dairys = db.getalldairys();
      	    	dairys.sort(new DairyComparator());
      	    	
      	    	server_Segment sendseg = new server_Segment();
      	    	int i;
      	    	for(i=0;i<dairys.size();i++){
      	    		sendseg.dairylist.add(dairys.get(i));
      	    	}
      	    	
      	    	ArrayList<Rank> rank_list = db.getallrank();
      	    	rank_list.sort(new RankComparator());
      	    	
      	    	for(i=0;i<rank_list.size();i++){
      	    		sendseg.rank_list.add(rank_list.get(i));
      	    	}
      	    	
      	    	sendseg.head=3;
      	    	send = new ObjectOutputStream(socket.getOutputStream());
            	    send.writeObject(sendseg);
            	    send.flush();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	  break;
          
          
          default:break;
          }
        }
        
       }catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			//如果客户离开就释放对应的tcb
			tcb_pool[client_tag]=null;
			System.out.println("client "+ client_tag+" is logout!");
			try {
				String name = db.getmyonline_username(client_tag);
				if(name != null){
					
				  db.deleteOnlineUser(client_tag);
	        	  ArrayList<Integer> connfd_list = db.getallconnfd();
	        	  ObjectOutputStream send=null;
	        	  server_Segment sendseg = new server_Segment();
	        	  sendseg.head=9;
	        	  sendseg.user.name = name;
	        	  
	        	  //转发给其他客户
	        	  int i;
	        	  for(i=0;i<connfd_list.size();i++){
			      	 try {
						send = new ObjectOutputStream(tcb_pool[connfd_list.get(i)].socket.getOutputStream());
						send.writeObject(sendseg);
				        send.flush();	 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	  }
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
      }
  }
  class DairyComparator implements Comparator<Diary_Segment>  
  {  
      public int compare(Diary_Segment o1, Diary_Segment o2)  
      {  
          if (o1.zan_number > o2.zan_number)  
          {  
              return -1;  
          }  
          else if (o1.zan_number < o2.zan_number)  
          {  
              return 1;  
          }  
          else  
          {  
              return 0;  
          }  
      }  
  } 
  class RankComparator implements Comparator<Rank>  
  {  
      public int compare(Rank o1, Rank o2)  
      {  
          if (o1.zan_number > o2.zan_number)  
          {  
              return -1;  
          }  
          else if (o1.zan_number < o2.zan_number)  
          {  
              return 1;  
          }  
          else  
          {  
              return 0;  
          }  
      }  
  }
}


