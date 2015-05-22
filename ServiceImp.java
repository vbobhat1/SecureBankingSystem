package bank;
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;
import java.sql.*;
public class ServiceImp extends UnicastRemoteObject implements ServiceInf
{
	TransRequest transreq;
	TransResponse transres;
	LoginRequest loginreq;
	BankBean bankbean;
	ArrayList<KeyRequest>atms=new ArrayList<KeyRequest>();
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	ResultSetMetaData rsmd;
	public ServiceImp()throws RemoteException
	{
		super();
		bankbean=new BankBean();
	}
	public KeyResponse requestKey(KeyRequest keyreq)throws RemoteException
	{
		KeyResponse keyres=null;
		try
		{
			BankMainForm.self.log("Public Key Request Received from ATM-"+keyreq.fromatm);
			Keys keys=new Keys(bankbean.keys.getG(),bankbean.keys.getN());
			byte buf[]=ProjUtil.serialize(keys);
			keyres=new KeyResponse(buf);			
			BankMainForm.self.log("Sending Public Key to  ATM-"+keyreq.fromatm);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return keyres;
	}
	
	public boolean login(LoginRequest loginreq)throws RemoteException
	{
		boolean valid=false;
		try
		{			
			valid=bankbean.process(loginreq);			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return valid;
	}
	public boolean logout(String cardno)throws RemoteException
	{
		return bankbean.logout(cardno);
	}
	public TransResponse doTrans(TransRequest transreq)throws RemoteException
	{
		TransResponse res=null;
		try
		{
			res=bankbean.process(transreq);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}
}