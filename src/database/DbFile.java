package Database;

import jodd.util.SystemUtil;

public class DbFile {

	public static String getDbfilepath() {
		return SystemUtil.getWorkingFolder() + "\\db\\jnote.db";
	}

}
