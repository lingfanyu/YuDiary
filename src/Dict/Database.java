package Dict;

import java.sql.*;
public class Database implements DatabaseReturn {
	//create a statement
	Connection connection = null;
	Statement statement = null;
	
	public Database() {
		//Load the JDBC driver
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			 Class.forName("org.sqlite.JDBC");   
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Driver loaded");
		
		//establish a connection
		try {
			//connection = DriverManager.getConnection("jdbc:mysql://localhost/dictionary", "aboboo", "cboboo");
			
			connection= DriverManager.getConnection("jdbc:sqlite:zie.db");
			
			// stat = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			
			
			statement = connection.createStatement();
			
			if (!table_existed("user"))
				create_user();
			if (!table_existed("favor"))
				create_favor();
		} catch (SQLException e) {
			e.printStackTrace();
		}//用户名：aboboo; 密码：cboboo
		System.out.println("database connected");		
	}
	
			//注册新用户
	public int adduser(String username, String password, int portrait) {
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery
					 ("select * from user where username = '" + username + "'");
			if(resultSet.next()) {
				System.out.println("error! username exists!");
				return ADDUSEREXIST;
			}
			statement.executeUpdate
			("create table " + username + "(word char(20), dictionary int, meaning char(255), primary key(word,dictionary))");
			statement.executeUpdate
				("insert into user values('" + username + "','" + password + "'," + portrait + ")");
			return ADDUSERSUCCEED;
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLERROR;
		}			
	}
	
	//用户登录
	public int checkPassword(String username, String password){
		try {
			ResultSet resultSet = statement.executeQuery
					("select password from user where username = '" + username + "'");			
			String pwd = null;
			
			if (resultSet.next()) {
				pwd = resultSet.getString(1);
				//System.out.println(pwd);
			}
			else return USERNOTEXIST;
			
			if (!password.equals(pwd))
				return PASSWORDERROR;
						
			return PASSWORDCORRECT;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLERROR;
		}			
	}
	
	public int getPortrait(String username) {
		int result = 0;
		try {
			ResultSet resultSet = statement.executeQuery
					("select portrait from user where username = '" + username + "'");
			if (resultSet.next())
				result = resultSet.getInt(1);			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return result;
	}
	
	//添加单词点赞数。baidu:0; youdao:1; bing:2;
	public int addfavor(String word, String user, int dictionary) {
		ResultSet resultSet = null;
		//String n = word.replaceAll("\'", "\\\'");
		String n = changeword(word);
		try {
			resultSet = statement.executeQuery
				//	 ("select * from favor where word = '" + word + "' && user = '" + user + "' && dictionary = '" + dictionary + "'");
					("select * from favor where word = '" + n + "' && user = '" + user + "' && dictionary = '" + dictionary + "'");
			if(!resultSet.next()) {
				statement.executeUpdate
				("insert into favor values('" + n + "','" + user + "'," + dictionary + ")");
				return FAVORSUCCEED;
			}
			else 
				return FAVORFAIL;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return SQLERROR;
	}
	private String changeword(String word) {
		String s = word;
		String n = "";
		for(int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if(ch == '\'') {
				n += "\\\'";
			}
			else n += ch;
		}
		
		return n;
	}
	//查询单词的不同网站点赞数。baidu:0; youdao:1; bing:2;
	public int[] searchword(String word) {
		String n = changeword(word);
		
		int[] count = new int[3];
		ResultSet resultSet = null;
		try {
			for (int i = 0; i < 3; i++) {
				resultSet = statement.executeQuery
						 ("select count(*) from favor where word = '" + n + "' && dictionary = " + i);	
				if(resultSet.next()) 
					count[i] = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void create_user() throws SQLException {
	/*	if(table_existed(statement, "user"))
			System.out.println("error!This table has existed!");
		else*/
		statement.executeUpdate
			("create table user(username char(20) primary key, password char(20), portrait int)");
	}
	
	/*
	public void drop_user() {
		if(!table_existed("user"))
			System.out.println("error!This table doesn't exist!");
		else
		try {
			statement.executeUpdate
				("drop table user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public void create_favor() throws SQLException {
	/*	if(table_existed(statement, "favor"))
			System.out.println("error!This table has existed!");
		else*/
		statement.executeUpdate
			("create table favor(word char(20), user char(20), dictionary int)");
	}
	
/*	public void drop_favor() {
		if(!table_existed("favor"))
			System.out.println("error!This table doesn't exist!");
		else
		try {
			statement.executeUpdate
				("drop table favor");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public boolean table_existed(String name) throws SQLException {
		//ResultSet resultSet = statement.executeQuery("show tables");
		//while(resultSet.next()) {
			//if (resultSet.getString(1).equals(name))
				//return true;
		//}
		//statement.executeUpdate("Drop table user;");
		return false;
	}
	
	public int addWord(String username, String word, int dictionary, String meaning) {
		ResultSet resultSet = null;
		String n = changeword(word);
		System.out.println(n);
		try {
			resultSet = statement.executeQuery
				//	 ("select * from " + username + " where word = '" + word + "' && dictionary = " + dictionary);
					("select * from " + username + " where word = '" + n + "' && dictionary = " + dictionary);
			if(resultSet.next()) {
				System.out.println("error! word in dictionary exists!");
				return ADDWORDEXISTS;
			}
			statement.executeUpdate
				("insert into " + username + " values('" + n + "', " + dictionary + ", '" + meaning + "')");
			return ADDWORDSUCCEED;
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLERROR;
		}			
	}
	
	public String[] getWords(String username) {
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery("select distinct word from " + username);
			int n = 0;
			while (resultSet.next()) 
				n++;
			String[] result = new String[n];
			int i = 0;
			resultSet.beforeFirst();
			while (resultSet.next()) 
				result[i++] = resultSet.getString(1);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getMeaning(String username, String word, int dictionary) {
		ResultSet resultSet = null;
		String n = changeword(word);
		System.out.println(n);
		try {
			resultSet = statement.executeQuery
					("select meaning from " + username + " where word = '" + n + "' && dictionary = '" + dictionary +"'");
			if (resultSet.next())
				return resultSet.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
