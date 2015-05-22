package bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;

import java.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ATMainForm extends JFrame
{
	
	JPanel sidepane=new JPanel();
	JPanel tb=new JPanel();
	JButton butCon=new JButton("Connect To Server");
	JButton butLogin=new JButton("Login");
	JButton butExit=new JButton("Exit");
	JTextArea ta=new JTextArea();
	JScrollPane jsp=new JScrollPane(ta);
	Listener listener=new Listener();

	public static final int SERVER_PORT=1234;
	public static final String HOST="localhost";
	Registry reg;
	ServiceInf inf;
	public static ATMainForm self;
	KeyResponse keyres;
	KeyRequest keyreq;
	ATMBean atmbean;
	
	public ATMainForm()
	{
		try
		{
			self=this;
			Security.addProvider(new BouncyCastleProvider()); 
			atmbean=new ATMBean();
			design();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void log(String val)
	{
		ta.append(val+"\n");
	}
	public void design()
	{
		add(tb,"North");
		add(jsp,"Center");
		tb.add(butCon);
		tb.add(butLogin);
		tb.add(butExit);
		butCon.addActionListener(listener);
		butLogin.addActionListener(listener);
		butExit.addActionListener(listener);
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(2);
		setTitle("ATM Main Form:-"+atmbean.id);
		butLogin.setEnabled(false);
	}
	public void connect()
	{
		try
		{
			reg=LocateRegistry.getRegistry(HOST,SERVER_PORT);
			inf=(ServiceInf)reg.lookup("bankservice");
			log("Connected to Server");
			Keys keys=new Keys(atmbean.keys.getG(),atmbean.keys.getN());
			byte keybuf[]=ProjUtil.serialize(keys);
			keyreq=new KeyRequest(atmbean.id,keybuf);
			keyres=inf.requestKey(keyreq);		
			atmbean.bankeys=(Keys)ProjUtil.deserialize(keyres.buf);
			log("Got Public Key Response from Bank Server");
			log("Services are Ready to Use");			
			butCon.setEnabled(false);
			butLogin.setEnabled(true);
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
			if(src==butCon)
			{
				connect();
			}			
			if(src==butLogin)
				new ATMLoginForm();
			if(src==butExit)
				System.exit(0);
		}
	}
	public static void main(String a[])
	{
		new ATMainForm();
	}
}
