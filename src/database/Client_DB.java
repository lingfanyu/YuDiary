package database;
import segment.Diary_Segment;
import segment.Rank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client_DB {
private Connection connection;
	
	//constructor for connecting database
	public Client_DB()
	{
		//load driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			connection = DriverManager.getConnection
			        ("jdbc:mysql://localhost/javabook", "scott", "tiger");
			System.out.println("Database connected");
		} catch (Exception ex) {
		      ex.printStackTrace();
		}
	}
	//methods for table Account
	//to tell whether an account exists
	public boolean findAccount(String userName) throws SQLException
	{
		PreparedStatement findStatement = connection.prepareStatement("select * from Account_table where userName = ?");
		findStatement.setString(1, userName);
		ResultSet rs = findStatement.executeQuery();
		
		rs.last();
		if(rs.getRow() == 0)
			return false;
		else
			return true;
	}
	public boolean findAccount(String userName, String pass) throws SQLException
	{
		PreparedStatement findStatement = connection.prepareStatement("select * from Account_table where userName = ? and pass = ?");
		findStatement.setString(1, userName);
		findStatement.setString(2, pass);
		ResultSet rs = findStatement.executeQuery();
		
		rs.last();
		if(rs.getRow() == 0)
			return false;
		else
			return true;
	}
	//to add an account
	public void addAccount(String userName, String pass,int icon) throws SQLException
	{
			PreparedStatement addStatement = connection.prepareStatement("insert into Account_table (userName, pass, zan_number,icon)"
					+ "values (?, ?, ?, ?)");
			addStatement.setString(1, userName);
			addStatement.setString(2, pass);
			addStatement.setInt(3, 0);
			addStatement.setInt(4, icon);
			addStatement.executeUpdate();
	}
	public int geticon(String userName) throws SQLException
	{
			PreparedStatement getStatement = connection.prepareStatement("select icon from Account_table where userName = ?");
			getStatement.setString(1, userName);
			ResultSet rs = getStatement.executeQuery();
			int icon = 1;
			while(rs.next()){
				icon = rs.getInt(1);
			}
			return icon;
	}
	public ArrayList<Rank> getallrank() throws SQLException
	{
		PreparedStatement getStatement = connection.prepareStatement("select userName,zan_number from Account_table");
		ResultSet rs = getStatement.executeQuery();
		ArrayList<Rank> result_list= new ArrayList<Rank>();
		
		while(rs.next()){
			Rank temp = new Rank();
			temp.userName = rs.getString(1);
			temp.zan_number = rs.getInt(2);
			result_list.add(temp);
		}
		return result_list;
	}

	//to tell whether a user is online
	public boolean findOnlineUser(String userName) throws SQLException
	{
		PreparedStatement findStatement = connection.prepareStatement("select * from OnlineUser where userName = ?");
		findStatement.setString(1, userName);
		ResultSet rs = findStatement.executeQuery();
		
		rs.last();
		if(rs.getRow() == 0)
			return false;
		else
			return true;
	}
	//to tell whether a user is online
	public boolean findOnlineUser(int connfd) throws SQLException
	{
		PreparedStatement findStatement = connection.prepareStatement("select * from OnlineUser where connfd = ?");
		findStatement.setInt(1, connfd);
		ResultSet rs = findStatement.executeQuery();
		
		rs.last();
		if(rs.getRow() == 0)
			return false;
		else
			return true;
	}
	public int getconnfd(String username) throws SQLException
	{
		PreparedStatement getStatement = connection.prepareStatement("select connfd from OnlineUser where userName = ?");
		getStatement.setString(1, username);
		ResultSet rs = getStatement.executeQuery();
		int connfd=-1;
		while(rs.next()){
			connfd=rs.getInt(1);
		}
		return connfd;
	}
	public String getmyonline_username(int connfd) throws SQLException
	{
		PreparedStatement getStatement = connection.prepareStatement("select userName from OnlineUser where connfd = ?");
		getStatement.setInt(1, connfd);
		ResultSet rs = getStatement.executeQuery();
		String name=null;
		while(rs.next()){
			name=rs.getString(1);
		}
		return name;
	}
	public ArrayList<Integer> getallconnfd() throws SQLException
	{
		PreparedStatement getStatement = connection.prepareStatement("select connfd from OnlineUser");
		ResultSet rs = getStatement.executeQuery();
		ArrayList<Integer> connfd_list= new ArrayList<Integer>();
		
		while(rs.next()){
			connfd_list.add(rs.getInt(1));
		}
		return connfd_list;
	}
	//add an online user
	public void addOnlineUser(String userName,int connfd) throws SQLException
	{
		PreparedStatement addStatement = connection.prepareStatement("insert into OnlineUser (userName,connfd) values (?,?)");
		addStatement.setString(1,  userName);
		addStatement.setInt(2, connfd);
		addStatement.executeUpdate();
		
	}
	//delete an online user
	public void deleteOnlineUser(String userName) throws SQLException
	{
		PreparedStatement deleteStatement = connection.prepareStatement("delete from OnlineUser where userName = ?");
		deleteStatement.setString(1, userName);
		deleteStatement.executeUpdate();
	}
	//delete an online user
	public void deleteOnlineUser(int connfd) throws SQLException
	{
		PreparedStatement deleteStatement = connection.prepareStatement("delete from OnlineUser where connfd = ?");
		deleteStatement.setInt(1, connfd);
		deleteStatement.executeUpdate();
	}
	public ArrayList<String> getAllonLineuser() throws SQLException
	{
		PreparedStatement getStatement = connection.prepareStatement("select userName from OnlineUser");
		ResultSet rs = getStatement.executeQuery();
		ArrayList<String> users_list = new ArrayList<String>();
		while(rs.next())
			users_list.add(rs.getString(1));
		
		return users_list;
	}
	//to add an account
	public void adddairy(String author, String title, int zan_number, String text, long date) throws SQLException
	{
			PreparedStatement addStatement = connection.prepareStatement("insert into dairy_table (author, title, zan_number, text, date)"
					+ "values (?, ?, ?, ?, ?)");
			addStatement.setString(1, author);
			addStatement.setString(2, title);
			addStatement.setInt(3, zan_number);
			addStatement.setString(4, text);
			addStatement.setLong(5, date);
			addStatement.executeUpdate();
	}
	public ArrayList<Diary_Segment> getdairys(String author) throws SQLException
	{
		PreparedStatement getStatement = connection.prepareStatement("select * from dairy_table where author = ?");
		getStatement.setString(1, author);
		ResultSet rs = getStatement.executeQuery();
		
        ArrayList<Diary_Segment> dairy_list = new ArrayList<Diary_Segment>();
		
		while(rs.next()){
			//System.out.(rs)
			Diary_Segment temp=new Diary_Segment();
			temp.author = rs.getString(1);
			temp.title = rs.getString(2);
			temp.zan_number = rs.getInt(3);
			temp.text = rs.getString(4);
			temp.date = new Date(rs.getLong(5));
			dairy_list.add(temp);
		}
		
		return dairy_list;
	}
	
	public ArrayList<Diary_Segment> getalldairys() throws SQLException
	{
		PreparedStatement getStatement = connection.prepareStatement("select * from dairy_table");
		ResultSet rs = getStatement.executeQuery();
		
		ArrayList<Diary_Segment> dairy_list = new ArrayList<Diary_Segment>();
		
		while(rs.next()){
			Diary_Segment temp=new Diary_Segment();
			temp.author = rs.getString(1);
			temp.title = rs.getString(2);
			temp.zan_number = rs.getInt(3);
			temp.text = rs.getString(4);
			temp.date = new Date(rs.getLong(5));
			dairy_list.add(temp);
		}
		
		return dairy_list;
	}
	//to update dairyZan
	public void updateZan(String author,long date) throws SQLException
	{
		PreparedStatement updateStatement = connection.prepareStatement("update dairy_table set zan_number = zan_number + 1 where author = ? and date = ?");
		updateStatement.setString(1, author);
		updateStatement.setLong(2, date);
		updateStatement.executeUpdate();
	}
	//to update userzan
	public void updateuserZan(String userName) throws SQLException
	{
		PreparedStatement updateStatement = connection.prepareStatement("update account_table set zan_number = zan_number + 1 where userName = ?");
		updateStatement.setString(1, userName);
		updateStatement.executeUpdate();
	}
}
