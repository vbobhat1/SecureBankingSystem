package bank;
public class TransRequest implements java.io.Serializable
{
	String cardno,type;
	double amt;
	public TransRequest(String cardno,String type)
	{
		this.cardno=cardno;
		this.type=type;
	}
	public TransRequest(String cardno,String type,double amt)
	{
		this.cardno=cardno;
		this.type=type;
		this.amt=amt;
	}
}