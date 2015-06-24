package YuNote;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import Database.DBFactory;



public class NoteDao {
	static Dao dao = new NutDao(DBFactory.getDataSource());

	/**
	 * 由Nutz创建表
	 */
	public static void create() {
		dao.create(Note.class, true);
	}

	public static Note getNote(Long id) {
		Note note = dao.fetch(Note.class, id);
		return note;
	}

	public static Note getNoteByUuid(String uuid) {
		Note note = dao.fetch(Note.class, Cnd.where("uuid", "=", uuid));
		return note;
	}

	public static void deleteNote(Long id) {
		dao.delete(Note.class, id);
	}

	public static void deleteNoteByUuid(String uuid) {
		dao.clear(Note.class, Cnd.where("uuid", "==", uuid));
	}

	public static List<Note> getAllNote() {
		List<Note> list = dao.query(Note.class, null);
		return list;
	}

	/**
	 * 查询分类ID下的笔记
	 * 
	 * @return
	 */
	public static List<Note> getNotesByCategoryId(int categoryId) {
		List<Note> list = null;
		if (categoryId < 0) {
			list = getAllNote();
		} else {
			list = dao.query(Note.class,
					Cnd.where("categoryId", "=", categoryId));
		}

		return list;
	}

	public static boolean saveNote(Note note) {
		dao.insert(note);
		return false;
	}

	public static boolean updateNote(Note note) {
		dao.update(note);
		return false;
	}

	public static void main(String[] args) {
		System.out.println(CategoryDao.getCategoryPath(5));
	}
}
