package bank;
import java.rmi.*;
public interface ServiceInf extends Remote
{
	public KeyResponse requestKey(KeyRequest keyreq)throws RemoteException;
	public boolean login(LoginRequest loginreq)throws RemoteException;
	public TransResponse doTrans(TransRequest transreq)throws RemoteException;	
	public boolean logout(String cardno)throws RemoteException;	
}