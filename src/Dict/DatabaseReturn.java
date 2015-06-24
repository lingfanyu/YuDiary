package Dict;
public interface DatabaseReturn {
	public static int UNDEFINED = 0;
	public static int SQLERROR = 1;
	public static int ADDUSERSUCCEED = 2;
	public static int ADDUSEREXIST = 3;
	public static int PASSWORDCORRECT = 4;
	public static int USERNOTEXIST = 5;
	public static int PASSWORDERROR = 6;
	public static int FAVORSUCCEED = 7;
	public static int FAVORFAIL = 8;
	public static int ADDWORDEXISTS = 9;
	public static int ADDWORDSUCCEED = 10;
}
