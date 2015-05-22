package bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class CustomerForm extends JFrame
{
	JPanel mainpane=new JPanel();
	JPanel ctrlpane=new JPanel();
	JPanel butpane=new JPanel();
	JPanel fieldpane=new JPanel();
	JButton butSave=new JButton("Save");	
	JButton butExit=new JButton("Close");
	JLabel lblcard=new JLabel("Enter Card Number");
	JTextField txtcard=new JTextField(10);
	JLabel lblpin=new JLabel("Enter PIN");
	JTextField txtpin=new JPasswordField(10);
	GridLayout fieldpanelayout=new GridLayout(2,2);
	BoxLayout ctrlpanelayout=new BoxLayout(ctrlpane,BoxLayout.Y_AXIS);
	CustBean custbean;
	Listener listener=new Listener();
	public CustomerForm()
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
		butpane.add(butSave);
		butpane.add(butExit);
		fieldpane.add(lblcard);
		fieldpane.add(txtcard);
		fieldpane.add(lblpin);
		fieldpane.add(txtpin);
		add(mainpane);
		pack();
		setVisible(true);
		setTitle("Customer Entry Form");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		butSave.addActionListener(listener);
		butExit.addActionListener(listener);
	}
	public void save()
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
		custbean=new CustBean(cardno,pass);
		custbean.save();		
	}
	class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			Object src=ae.getSource();
			if(src==butSave)
				save();
			if(src==butExit)
			{
				setVisible(false);
				dispose();
			}
		}
	}
}