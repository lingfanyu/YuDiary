package LocalPanel;

public class JNConstans {
	// 窗体风格
	public static final String UI_METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public static final String UI_WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public static final String UI_GTK = "org.jb2011.lnf.windows2.Windows2LookAndFeel";
	public static String CURRENTLOOKANDFEEL = UI_METAL;

	// 记录当前table选择的UUID
	public static String CUR_SELECT_UUID = null;
	// 记录当前tab选择的INDEX
	public static int CUR_SELECT_TAB_INDEX = -1;

	// 记录当前选择的树形分类的ID 默认是默认分类
	public static int CUR_SELECT_CATEGORY_ID = 1;

	public static String DATA_FILE_DIR = "/data/";

	// 编辑器类型
	public static int EDITORY_SIMPLE = 0;
	public static int EDITORY_ADVANCED = 1;
	public static int EDITORY_MARKDOWN = 2;

	// 阅读或者编辑日志
	public static int EDITOR_TYPE_READ = 0;// 阅读
	public static int EDITOR_TYPE_WRITE = 1;// 编辑

}
