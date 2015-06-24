package YuNote;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import Database.DBFactory;



public class CategoryDao {
	static Dao dao = new NutDao(DBFactory.getDataSource());

	public static void create() {
		dao.create(Category.class, true);
		Category defaultCategory = new Category();
		defaultCategory.setCategoryName("默认分类");
		defaultCategory.setId(1);
		dao.insert(defaultCategory);

		Category dailyCategory = new Category();
		dailyCategory.setCategoryName("我的日记");
		dailyCategory.setId(2);
		dao.insert(dailyCategory);
	}

	public static Category save(Category category) {
		category = dao.insert(category);
		return category;
	}

	public static Category getCategory(int id) {
		Category category = dao.fetch(Category.class, id);
		return category;
	}

	public static List<Category> getAllCategory() {
		return dao.query(Category.class, null);
	}

	public static Category saveNote(Category category) {
		dao.create(Category.class, false);
		category = dao.insert(category);
		return category;
	}

	/**
	 * 默认初始化默认分类(id=1)，我的日记(id=2)
	 */
	public static void init() {
		Category c = new Category();
		c.setId(1);
		c.setParentId(0);
		c.setCategoryName("默认分类");
		dao.insert(c);

		c.setId(2);
		c.setCategoryName("我的日记");

		dao.insert(c);
	}

	public static String getCategoryPath(int categoryId) {
		Category c = new Category();
		String path = "";

		while (categoryId != 0) {
			c.setId(categoryId);
			c = get(c);
			path = c.getCategoryName() + "/" + path;
			categoryId = c.getParentId();
		}
		return path;
	}

	public static Category get(Category category) {
		category = dao.fetch(category);
		return category;
	}

	public static boolean delete(Integer id) {
		dao.delete(Category.class, id);
		return false;
	}

	public static Category update(Category category) {
		dao.update(category);
		return getCategory(category.getId());
	}

	public static Category query(Category category) {
		category = dao.fetch(
				Category.class,
				Cnd.where("categoryName", "=", category.getCategoryName()).and(
						"parentId", "=", category.getParentId()));
		return category;
	}

	public static int queryNote(String categoryName) {
		List<Category> c = dao.query(Category.class,
				Cnd.where("categoryName", "like", categoryName));
		return c == null ? 0 : c.size();
	}

	public static void main(String[] args) {
		CategoryDao d = new CategoryDao();
		System.out.println(d.getCategoryPath(6));
		;

	}
}
