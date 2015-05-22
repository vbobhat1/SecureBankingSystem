package bank;
public class KeyRequest implements java.io.Serializable
{
		byte buf[];
		int fromatm;
		public KeyRequest(){}
		public KeyRequest(int atmid,byte keybuf[])
		{
			fromatm=atmid;
			buf=keybuf;
		}
}