package bank;
import java.io.*;
import javax.swing.*;
public class ATMBean
{
	public static int cnt;
	Keys keys,bankeys;
	Paillier p;
	int id;
	public ATMBean()
	{
		id=cnt;
		cnt++;
		p=new Paillier();
		keys=p.getKeys();
		storeKeys();
	}
	public void storeKeys()
	{
		try
		{
			byte buf[]=ProjUtil.serialize(keys);
			FileOutputStream fout=new FileOutputStream(new File("atmkeys/ATM-"+id+".ser"));
			fout.write(buf);
			fout.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void readKeys()
	{
			try
			{
				String aid=JOptionPane.showInputDialog("Enter ATM ID for which Keys to be recovered");
				if(aid==null||aid.trim().length()==0)
				{
					JOptionPane.showMessageDialog(null,"No ATM id Entered");
					return;
				}
				id=new Integer(aid);
				File f=new File("atmkeys/ATM-"+id+".ser");
				if(!f.exists())
				{
					JOptionPane.showMessageDialog(null,"Invalid ATM ID Entered");
					return;
				}
				FileInputStream fin=new FileInputStream(f);
				byte buf[]=new byte[fin.available()];
				fin.read(buf);
				fin.close();
				Object ob=ProjUtil.deserialize(buf);
				if(ob instanceof Keys)
					keys=(Keys)ob;
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	}
}