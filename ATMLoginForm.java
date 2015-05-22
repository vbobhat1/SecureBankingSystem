package bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.math.*;

import java.security.*;
import javax.crypto.*;

public class ATMLoginForm extends JFrame
{	
	JPanel mainpane=new JPanel();
	JPanel ctrlpane=new JPanel();
	JPanel butpane=new JPanel();
	JPanel fieldpane=new JPanel();
	JButton butLogin=new JButton("Login");	
	JButton butExit=new JButton("Close");
	JLabel lblcard=new JLabel("Enter Card Number");
	JTextField txtcard=new JTextField(10);
	JLabel lblpin=new JLabel("Enter PIN");
	JTextField txtpin=new JPasswordField(10);
	GridLayout fieldpanelayout=new GridLayout(2,2);
	BoxLayout ctrlpanelayout=new BoxLayout(ctrlpane,BoxLayout.Y_AXIS);
	
	SecretKey skey;
	Listener listener=new Listener();
	
	public ATMLoginForm()
	{
		design();	
	}
	public void design()
	{
		fieldpanelayout.setHgap(10);
		fieldpanelayout.setVgap(10);
		fieldpane.setLayout(fieldpanelayout);		
		ctrlpane.setLayout(ctrlpanelayout);
		mainpane.add(ctrlpane);
		ctrlpane.add(fieldpane);
		ctrlpane.add(butpane);		
		butpane.add(butLogin);
		butpane.add(butExit);
		fieldpane.add(lblcard);
		fieldpane.add(txtcard);
		fieldpane.add(lblpin);
		fieldpane.add(txtpin);
		add(mainpane);
		pack();
		setVisible(true);
		setTitle("Client Login");
		setDefaultCloseOperation(2);
		butLogin.addActionListener(listener);
		butExit.addActionListener(listener);
	}
	public void login()
	{
		String cardno=txtcard.getText().trim();
		String pass=txtpin.getText().trim();
		if(cardno.length()==0)
		{
			JOptionPane.showMessageDialog(null,"ATM Card Number Not Entered");
			txtcard.requestFocus();
			return ;
		}
		if(pass.length()==0)
		{
				JOptionPane.showMessageDialog(null,"PIN Not Entered");
				txtpin.requestFocus();
				return;
		}
		if(pass.length()!=4)
		{
			JOptionPane.showMessageDialog(null,"Invalid PIN Length. Must be 4 digits only");
			txtpin.setText("");
			txtpin.requestFocus();
			return;
		}
		try
		{
			skey=ProjUtil.getDesKey();
			ATMBean atmbean=ATMainForm.self.atmbean;
			String data=cardno+"-"+pass;
			byte cbuf[]=ProjUtil.desencrypt(data.getBytes(),skey);
			//System.out.println("ATMainForm.self:"+ATMainForm.self);
			//System.out.println("ATMainForm.self.atmbean:"+ATMainForm.self.atmbean);
			//System.out.println("ATMainForm.self.atmbean.p-"+ATMainForm.self.atmbean.p);
			//System.out.println("SKEY:"+skey);
			BigInteger deskey=Paillier.Encryption(new BigInteger(skey.getEncoded()),atmbean.bankeys.getG(),atmbean.bankeys.getN());
			byte keybuf[]=deskey.toByteArray();
			LoginRequest loginreq=new LoginRequest(keybuf,cbuf);
			if(ATMainForm.self.inf.login(loginreq))
			{
				ATMainForm.self.log("Login Succcess");				
				new TransForm(cardno,skey);
				dispose();
			}
			else 
			{
				ATMainForm.self.log("Login Failed");
				JOptionPane.showMessageDialog(null,"Login Failed");
				txtcard.setText("");
				txtpin.setText("");
				txtcard.requestFocus();
			}
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
			if(src==butLogin)
				login();
			if(src==butExit)
				dispose();
		}
	}
	public static void main(String a[])
	{
		new ATMLoginForm();
	}
}
