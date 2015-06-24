package Segment;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class server_Segment implements Serializable{

	//����ͷ��
	public int head;
	//�û���Ϣ
	public User user = new User();
	
	//��½ʧ�ܱ�־
	public int logfault;
	//�ռ���Ϣ
	public ArrayList<Diary_Segment> dairylist = new ArrayList<Diary_Segment>();
	
	//�����û���Ϣ
	public ArrayList<String> users_list = new ArrayList<String>();
	
	//���а���Ϣ
	public ArrayList<Rank> rank_list = new ArrayList<Rank>();
}
