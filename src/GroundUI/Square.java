package GroundUI;

import Segment.client_Segment;
import Segment.Diary_Segment;
import Segment.Rank;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;

public class Square {

	//广场对话框
	JDialog square_dialog = null;
	
	//排行榜表
	private ArrayList<Rank> ranks = new ArrayList<Rank>();
	
	private DefaultListModel<String> rank_listmodel = new DefaultListModel<String>();
	
    private JList<String> rank_list = new JList<String>(rank_listmodel);	
	
	private JScrollPane rank_panel = new JScrollPane(rank_list);
	
	//日记列表
	private ArrayList<Diary_Segment> dairy_list = new ArrayList<Diary_Segment>();
	
	//当前显示的日记位置
	int dairy_index=0;
	//临时日记显示区
	private JTextArea jta = new JTextArea(); 
	
	private JScrollPane dairy_JSP = new JScrollPane(jta);
	
	JPanel dairy_panel = new JPanel();
	
	//日记下一篇按钮
	private JButton previous_button = new JButton("上一篇");
	private JButton next_button = new JButton("下一篇");
	private JButton zan_button = new JButton("点赞");
	
	//搜索框
	private JTextField search_text = new JTextField(20);
	
	//搜索键
	private JButton search_button = new JButton("搜索");
	
	JPanel search_panel = new JPanel();
	
	//搜索结果
	private ArrayList<Diary_Segment> result_list = new ArrayList<Diary_Segment>();
	
	//结果位置标记
	int result_index=0;
	
	//从搜索状态返回浏览状态的按钮
	private JButton back_button = new JButton("返回");
	
	//显示框的状态，0表示浏览状态，1表示搜索状态
	int state =0;
	
	public Square(final Socket socket){
		dairy_panel.setLayout(new BorderLayout());
		dairy_panel.add(previous_button,BorderLayout.NORTH);
		dairy_panel.add(dairy_JSP,BorderLayout.CENTER);
		dairy_panel.add(next_button,BorderLayout.SOUTH);
		dairy_panel.add(zan_button,BorderLayout.WEST);
		
		search_panel.setLayout(new BorderLayout());
		search_panel.add(back_button,BorderLayout.WEST);
		search_panel.add(search_text,BorderLayout.CENTER);
		search_panel.add(search_button,BorderLayout.EAST);
		back_button.setVisible(false);
		
		//向前翻页
		previous_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				switch(state){
				case 0:{
					if(dairy_index >0){
						dairy_index--;
						jta.setText(dairy_list.get(dairy_index).title+"\n"+dairy_list.get(dairy_index).text);
						zan_button.setText("赞"+dairy_list.get(dairy_index).zan_number);
					}
				}break;
				case 1:{
					if(result_index >0){
						result_index--;
						jta.setText(result_list.get(result_index).title+"\n"+result_list.get(result_index).text);
						zan_button.setText("赞"+result_list.get(result_index).zan_number);
					}
				}break;
				default:break;
				}
				
				//login_dialog.dispose();
				//login_dialog=null;
				//reg.setup(toServer);
			}
		}
		);
		//向后翻页
		next_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				switch(state){
				case 0:{
					if(dairy_index <dairy_list.size()-1){
						dairy_index++;
						jta.setText(dairy_list.get(dairy_index).title+"\n"+dairy_list.get(dairy_index).text);
						zan_button.setText("赞"+dairy_list.get(dairy_index).zan_number);
					}
				}break;
				case 1:{
					if(result_index <result_list.size()-1){
						result_index++;
						jta.setText(result_list.get(result_index).title+"\n"+result_list.get(result_index).text);
						zan_button.setText("赞"+result_list.get(result_index).zan_number);
					}
				}break;
				default:break;
				}
				
				//reg.setup(toServer);
			}
		}
		);
		//搜索日志
		search_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String author=search_text.getText();
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
		});
		back_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				back_button.setVisible(false);
				//返回浏览状态
				state=0;
				result_list.clear();
				
				jta.setText(dairy_list.get(dairy_index).title+"\n"+dairy_list.get(dairy_index).text);
				zan_button.setText("赞"+dairy_list.get(dairy_index).zan_number);
				//reg.setup(toServer);		
			}
		}
		);
		zan_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				switch(state){
				case 0:{
					dairy_list.get(dairy_index).zan_number++;
					zan_button.setText("赞"+dairy_list.get(dairy_index).zan_number);
					//向服务器发送被点赞的日志
					client_Segment sendseg = new client_Segment();
					sendseg.head = 8;
					
					sendseg.dairy.author=dairy_list.get(dairy_index).author;
					sendseg.dairy.zan_number =dairy_list.get(dairy_index).zan_number;
					sendseg.dairy.date.setTime(dairy_list.get(dairy_index).date.getTime());
					
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
				}break;
				case 1:{
					result_list.get(result_index).zan_number++;
					zan_button.setText("赞"+result_list.get(result_index).zan_number);
					//向服务器发送被点赞的日志
					client_Segment sendseg = new client_Segment();
					sendseg.head = 8;
					
					sendseg.dairy.author=result_list.get(result_index).author;
					sendseg.dairy.zan_number =result_list.get(result_index).zan_number;
					sendseg.dairy.date.setTime(result_list.get(result_index).date.getTime());
					
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
				}break;
				default:break;
				}	
			}
		}
		);
	}
	public void setup(){
		square_dialog = new JDialog();
		square_dialog.setLayout(new BorderLayout());
		square_dialog.add(rank_panel,BorderLayout.WEST);
		square_dialog.add(dairy_panel,BorderLayout.CENTER);
		square_dialog.add(search_panel,BorderLayout.NORTH);
		
		square_dialog.setSize(500,400);
		square_dialog.setLocation(400,200);
		square_dialog.setResizable(true);
		square_dialog.setVisible(true);	
		
		//状态切换为浏览状态，同时设置显示情况
		state=0;
		dairy_index=0;
		
		//显示浏览结果
		jta.setText(dairy_list.get(dairy_index).title+"\n"+dairy_list.get(dairy_index).text);
		zan_button.setText("赞"+dairy_list.get(dairy_index).zan_number);
		int i;
		for(i=0;i<ranks.size();i++){
			rank_listmodel.addElement(ranks.get(i).userName+": "+ranks.get(i).zan_number);
		}
	}
	public void add_dairy(Diary_Segment d){
		dairy_list.add(d);
		//System.out.println(d.text);
	}
	public void add_result(Diary_Segment d){
		result_list.add(d);
		//System.out.println(d.text);
	}
	public void add_rank(Rank r){
		ranks.add(r);
	}
	public void clean(){
		dairy_list.clear();
		result_list.clear();
		ranks.clear();
		rank_listmodel.clear();
	}
	public void showresut(){
		//状态切换到搜索状态
        back_button.setVisible(true);
        result_index =0;
        state =1;
        
        //显示搜素结果
        jta.setText(result_list.get(result_index).title+"\n"+result_list.get(result_index).text);
        zan_button.setText("赞"+result_list.get(result_index).zan_number);
	}
}
