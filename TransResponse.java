package bank;
public class TransResponse implements java.io.Serializable
{
	byte buf[];
	String msg;
	public TransResponse(){}
	public TransResponse(byte buf[])
	{
		this.buf=buf;
	}
}