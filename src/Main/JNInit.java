package Main;

import YuNote.CategoryDao;
import YuNote.NoteDao;
import YuNote.OptionDao;



public class JNInit {
	public static void main(String[] args) {
		OptionDao ddao = new OptionDao();
		ddao.create();
		
		NoteDao ndao = new NoteDao();
		ndao.create();
		
		CategoryDao cdao = new CategoryDao();
		cdao.create();
	}
}
