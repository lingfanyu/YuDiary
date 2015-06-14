package segment;

import java.util.Date;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Diary_Segment implements Serializable{

	//日记创建日期
	public Date date = new Date();
	//日记作者
	public String author;
	//日记点赞数量
	public int zan_number;
	//日记标题
	public String title;
	//日记内容，以后需要修改
	public String text;
	//发表标志
	public boolean public_flag;
}
