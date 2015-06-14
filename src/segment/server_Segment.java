package segment;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class server_Segment implements Serializable{

	//报文头部
	public int head;
	//用户信息
	public User user = new User();
	
	//登陆失败标志
	public int logfault;
	//日记信息
	public ArrayList<Diary_Segment> dairylist = new ArrayList<Diary_Segment>();
	
	//在线用户信息
	public ArrayList<String> users_list = new ArrayList<String>();
	
	//排行榜信息
	public ArrayList<Rank> rank_list = new ArrayList<Rank>();
}
