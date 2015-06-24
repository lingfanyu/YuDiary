package Segment;

import java.util.Date;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Dairy_Segment implements Serializable{

	//�ռǴ�������
	public Date date = new Date();
	//�ռ�����
	public String author;
	//�ռǵ�������
	public int zan_number;
	//�ռǱ���
	public String title;
	//�ռ����ݣ��Ժ���Ҫ�޸�
	public String text;
	//�����־
	public boolean public_flag;
	
	public int icon;
}
