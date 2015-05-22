package bank;
public class KeyResponse implements java.io.Serializable
{
	byte buf[];
	public KeyResponse(){}
	public KeyResponse(byte buf[])
	{
		this.buf=buf;
	}
}