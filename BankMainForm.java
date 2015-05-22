package bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

import java.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BankMainForm extends JFrame
{
	JPanel tb=new JPanel();
	JButton butCust=new JButton("Customer Entry");
	JButton butStart=new JButton("Start Server Process");
	JButton butExit=new JButton("Exit");
	Listener listener=new Listener();
	JTextArea ta=new JTextArea();
	JScrollPane jsp=new JScrollPane(ta);
	public static final int SERVER_PORT=1234;
	public static final String HOST="localhost";
	Registry reg;
	ServiceInf inf;
	ServiceImp imp;
	public static BankMainForm self;
	public BankMainForm()
	{
		self=this;
		tb.add(butCust);
		tb.add(butStart);
		tb.add(butExit);
		add(tb,"North");
		add(jsp,"Center");
		setSize(500,500);
		setVisible(true);
		setTitle("Bank Server");
		setDefaultCloseOperation(2);
		butCust.addActionListener(listener);
		butStart.addActionListener(listener);
		butExit.addActionListener(listener);
		try
		{
			Security.addProvider(new BouncyCastleProvider()); 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void log(String val)
	{
		ta.append(val+"\n");
	}
	public void startServer()
	{
		try
		{
				imp=new ServiceImp();
				ServiceInf inf=imp;
				reg=LocateRegistry.createRegistry(SERVER_PORT);
				reg.rebind("bankservice",inf);
				log("Server Process Started Sucessfully at Port:"+SERVER_PORT);
				butStart.setEnabled(false);
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
			if(src==butCust)
			{
				new CustomerForm();
			}
			if(src==butStart)
				startServer();
			if(src==butExit)
				System.exit(0);
		}
	}
	public static void main(String a[])
	{
		new BankMainForm();
	}
}