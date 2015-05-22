package bank;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.math.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class BankBean
{
	Keys keys;
	Paillier p;
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	ResultSetMetaData rsmd;
	ArrayList<LoginRequest>logins=new ArrayList<LoginRequest>();
	ArrayList<String>cards=new ArrayList<String>();
	ArrayList<SecretKey>skeys=new ArrayList<SecretKey>();
	public BankBean()
	{
		p=new Paillier();
		keys=p.getKeys();
	}
	public BankBean(boolean val)
	{	
	}
	public boolean process(LoginRequest req)throws Exception
	{
		boolean found=false;
		BigInteger key=p.Decryption(new BigInteger(req.keybuf));
		SecretKey skey=ProjUtil.getDesKey(key.toByteArray());
		byte buf[]=ProjUtil.desdecrypt(req.databuf,skey);
		String data[]=new String(buf).split("-");
		String card=data[0];
		String pass=data[1];
		byte hash[]=ProjUtil.hash(pass.getBytes());
		con=DbCon.getCon();
		ps=con.prepareStatement("select *from customer where cardno=? and pass=?");
		ps.setString(1,card);
		ps.setString(2,new String(hash));
		rs=ps.executeQuery();
		if(rs.next())
		{
			found=true;
			logins.add(req);
			cards.add(card);
			skeys.add(skey);
		}
		con.close();
		return found;
	}
	public TransResponse process(TransRequest req)throws Exception
	{
		String info=new String();
		int selidx=-1;
		TransResponse transres=null;
		if(req.type.equalsIgnoreCase("B"))
		{
			con=DbCon.getCon();
			if(cards.contains(req.cardno))
			{
				selidx=cards.indexOf(req.cardno);
				ps=con.prepareStatement("select *from accts where cardno=?");
				ps.setString(1,req.cardno);
				rs=ps.executeQuery();
				while(rs.next())
				{
					info+=rs.getString(2)+"-"+rs.getString(3)+";";
				}
				if(info.trim().length()>0)
				{
					System.out.println("INFO:"+info);
					byte cbuf[]=ProjUtil.desencrypt(info.getBytes(),skeys.get(selidx));
					transres=new TransResponse(cbuf);
					transres.msg="Transaction Successful";
					System.out.println("TRANSRES :"+transres);
				}				
					
			}
			con.close();
		}
		if(req.type.equalsIgnoreCase("WC")||req.type.equalsIgnoreCase("WS"))
		{
			String ttype=req.type.charAt(1)+"";
			con=DbCon.getCon();
			if(cards.contains(req.cardno))
			{
				selidx=cards.indexOf(req.cardno);
				ps=con.prepareStatement("select curbal from accts where cardno=? and acctype=?");
				ps.setString(1,req.cardno);
				ps.setString(2,ttype);
				rs=ps.executeQuery();
				if(rs.next())
				{
						double curbal=rs.getDouble(1);
						if(curbal>=req.amt)
						{
							ps=con.prepareStatement("update accts set curbal=curbal-? where cardno=? and acctype=?");
							ps.setDouble(1,req.amt);
							ps.setString(2,req.cardno);
							ps.setString(3,ttype);
							if(ps.executeUpdate()==1)
							{
								ps=con.prepareStatement("insert into trans values(?,?,?,?)");
								java.text.DateFormat fmt=java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT);							
								ps.setString(1,req.cardno);
								ps.setString(2,ttype);
								ps.setDouble(3,req.amt);
								ps.setString(4,fmt.format(new java.util.Date()));
								if(ps.executeUpdate()==1)
								{
									transres=new TransResponse();
									transres.msg="Withdraw Successful";
								}
							}
						}
						else
						{
							transres=new TransResponse();
							transres.msg="No Enough Balance in Given Type-"+ttype;
						}	
				}
				else
				{
					transres=new TransResponse();
					transres.msg="Invalid Card No";
				}
					
			}
			con.close();
		}
		return transres;
	}
	public boolean logout(String cardno)
	{
		boolean done=false;
		if(cards.contains(cardno))
		{
			int idx=cards.indexOf(cardno);
			cards.remove(idx);
			skeys.remove(idx);
			logins.remove(idx);
			done=true;
		}
		return done;
	}
}