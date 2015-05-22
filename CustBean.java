package bank;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.math.*;

public class CustBean
{
	String cardno,pass;
	int acno,actype;
	double bal1,bal2;	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	ResultSetMetaData rsmd;
	public CustBean()
	{		
	}
	public CustBean(String cardno,String pass)
	{
		this.cardno=cardno;
		this.pass=pass;
	}
	public void save()
	{
		try
		{
			con=DbCon.getCon();
			ps=con.prepareStatement("insert into customer values(?,?)");			
			String hashpass=new String(ProjUtil.hash(pass.getBytes()));
			ps.setString(1,cardno);
			ps.setString(2,hashpass);			
			if(ps.executeUpdate()==1)
			{
				ps=con.prepareStatement("insert into accts values(?,'C',1000)");
				ps.setString(1,cardno);
				ps.executeUpdate();
				ps=con.prepareStatement("insert into accts values(?,'S',10000)");
				ps.setString(1,cardno);
				ps.executeUpdate();
				BankMainForm.self.log("New Account with Card No "+cardno+" Created Sucessfully");
				//JOptionPane.showMessageDialog(null,"New Account Created Sucessfully");
			}
			con.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}