package bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.security.*;
import javax.crypto.*;


public class TransForm extends JFrame
{	
	JPanel sidepane=new JPanel();
	JPanel tb=new JPanel();
	JButton butChkbal=new JButton("Check Balance");
	JButton butWithdraw=new JButton("Withdraw");
	JButton butLogout=new JButton("Logout");
	JDesktopPane desktop=new JDesktopPane();
	GridLayout tblayout=new GridLayout(3,1);
	String cardno;
	SecretKey skey;
	Listener listener=new Listener();
	public TransForm(String cardno,SecretKey skey)
	{
		this.cardno=cardno;
		this.skey=skey;
		tb.setLayout(tblayout);
		tb.add(butChkbal);
		tb.add(butWithdraw);
		tb.add(butLogout);
		sidepane.add(tb);
		add(sidepane,"West");
		add(desktop,"Center");
		setSize(500,500);
		setVisible(true);
		setTitle("Transaction Form");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		butChkbal.addActionListener(listener);
		butWithdraw.addActionListener(listener);
		butLogout.addActionListener(listener);
	}
	public void chkBal()
	{
		try
		{			
			TransRequest req=new TransRequest(cardno,"B");
			TransResponse res=ATMainForm.self.inf.doTrans(req);
			System.out.println("TRANS RES:"+res);
			System.out.println("TRANS RES  BUF:"+res.buf.length);
			byte buf[]=ProjUtil.desdecrypt(res.buf,skey);
			String data=new String(buf).trim();
			String rep[]=data.split(";");
			String info=new String("Balance Info\n");
			for(int i=0;i<rep.length;i++)
				info+=rep[i]+"\n";
			info+="\n"+res.msg;
			JOptionPane.showMessageDialog(null,info);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void withdraw()
	{
		try
		{
			String actype=JOptionPane.showInputDialog("Enter Account Type (C-Check,S-Saving) from which to be Withdraw");
			if(actype==null||actype.trim().length()==0)
			{
				JOptionPane.showMessageDialog(null,"Account Type Not Entered");
				return;
			}
			if(!(actype.trim().equalsIgnoreCase("S")||actype.trim().equalsIgnoreCase("C")))
			{
				JOptionPane.showMessageDialog(null,"Invalid Account Type Entered");
				return;
			}
			String tamt=JOptionPane.showInputDialog("Enter Amount to be Withdraw");
			if(tamt==null||tamt.trim().length()==0)
			{
				JOptionPane.showMessageDialog(null,"Amount Not Entered");
				return;
			}
			double amt=new Double(tamt.trim());
			if(amt==0)
			{
				JOptionPane.showMessageDialog(null,"Entered Amount must be>0");
				return;
			}
			TransRequest req=new TransRequest(cardno,"W"+actype,amt);
			TransResponse res=ATMainForm.self.inf.doTrans(req);						
			JOptionPane.showMessageDialog(null,res.msg);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void logout()
	{
		try
		{
			ATMainForm.self.inf.logout(cardno);
			dispose();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			Object src=ae.getSource();
			if(src==butChkbal)
				chkBal();
			if(src==butWithdraw)
				withdraw();
			if(src==butLogout)
			{
				
				logout();
			}
		}
	}
}
