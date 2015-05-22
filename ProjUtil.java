package bank;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import java.io.*;

public class ProjUtil
{
	public static byte[] getSign(Signature signature,PrivateKey prikey,byte buf[])throws Exception
	{
		signature.initSign(prikey, pack4.Utils.createFixedRandom());			
		signature.update(buf);					
		byte[] sigBytes = signature.sign();
		return sigBytes;
	}
	public static boolean isValidSign(Signature signature,PublicKey pubkey,byte signbuf[],byte testbuf[])throws Exception
	{
		boolean signflag=true;
		signature.initVerify(pubkey);		
		signature.update(testbuf);
		if (!signature.verify(signbuf))
		{						
			signflag=false;			
		}
		return signflag;
	}
	public static byte[] hash(Object ob)throws Exception
	{
		byte buf[]=serialize(ob);
		MessageDigest hash =MessageDigest.getInstance("SHA1", "BC");
        byte hashbuf[]=hash.digest(buf);
		return hashbuf;
	}
	public static byte[] hash(byte buf[])throws Exception
	{		
		MessageDigest hash =MessageDigest.getInstance("SHA1", "BC");
        byte hashbuf[]=hash.digest(buf);
		return hashbuf;
	}
	public static boolean isValidHash(byte buf1[],byte buf2[])throws Exception
	{
		MessageDigest hash =MessageDigest.getInstance("SHA1", "BC");
        //byte testbuf[]=hash.digest(buf2);
		return MessageDigest.isEqual(buf1,buf2);
	}
	public static byte[] serialize(Object ob)throws Exception
	{
		ByteArrayOutputStream bout=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(bout);
		oos.writeObject(ob);
		byte buf[]=bout.toByteArray();				
		oos.close();
		return buf;
	}
	public static Object deserialize(byte buf[])throws Exception
	{
		ByteArrayInputStream bin=new ByteArrayInputStream(buf);
		ObjectInputStream ois=new ObjectInputStream(bin);
		Object ob=ois.readObject();
		ois.close();
		return ob;
	}
	public static SecretKey getDesKey()throws Exception
	{
		SecretKeyFactory sf = SecretKeyFactory.getInstance("DES");
		String pass=System.currentTimeMillis()+"";
		if(pass.length()<8)
		{
			int bal=8-pass.length();
			for(int i=1;i<=bal;i++)	
			pass+="$";
		}		
		return sf.generateSecret(new DESKeySpec(pass.getBytes()));		
	}
	public static SecretKey getDesKey(byte buf[])throws Exception
	{
		SecretKeyFactory sf = SecretKeyFactory.getInstance("DES");		
		return sf.generateSecret(new DESKeySpec(buf));		
	}
	public static byte[] desencrypt(byte []input,SecretKey key)throws Exception
	{				   
		Cipher cipher = Cipher.getInstance("DES", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(input);
		return cipherText;
	}
	public static byte[] desdecrypt(byte []input,SecretKey key)throws Exception
	{				   
		Cipher cipher = Cipher.getInstance("DES", "BC");
        cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(input);
		return plainText;
	}
}
