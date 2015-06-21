package Listener;

import java.util.ArrayList;
import java.util.List;

public class JNTabListener {
	private String uuid;
	private int index;
	private static List<String> tabIdex = new ArrayList<String>();

	public static void addTab(String uuid) {
		tabIdex.add(uuid);
	}

	public static void removeTab(int index) {
		tabIdex.remove(index);
	}

	/**
	 * 判断是否已经打开tab，返回打开的index
	 * 
	 * @param uuid
	 * @return
	 */
	public static Integer isOpenTab(String uuid) {
		
		for (int i = 0; i < tabIdex.size(); i++) {
			if(uuid.equals(tabIdex.get(i))){
				return i;
			}
		}
		return -1;
	}
}
