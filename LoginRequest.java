package bank;
public class LoginRequest implements java.io.Serializable
{
	byte keybuf[],databuf[];
	public LoginRequest(){}
	public LoginRequest(byte keybuf[],byte databuf[])
	{
		this.keybuf=keybuf;
		this.databuf=databuf;
	}
}