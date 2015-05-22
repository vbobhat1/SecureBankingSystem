package bank;
import java.sql.*;
public class DbCon
{
	public static Connection getCon()throws Exception
	{
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:./db/bank.db");
		//System.out.println("Con ok");
		return con;
	}
        public static Connection getCon(String db)throws Exception
	{
		Class.forName("org.sqlite.JDBC");
		Connection con = DriverManager.getConnection("jdbc:sqlite:"+db);
		//System.out.println("Con ok");
		return con;
	}
}