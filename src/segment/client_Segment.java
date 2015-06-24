package Segment;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class client_Segment implements Serializable{
	public int head;
    public User user = new User();
    public Diary_Segment dairy= new Diary_Segment();
    public ArrayList<String> friend_list = new ArrayList<String>();
}
