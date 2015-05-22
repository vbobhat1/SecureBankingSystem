package pack3;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


public class Utils
    extends pack2.Utils
{
  
    public static SecretKey createKeyForAES(int bitLength, SecureRandom random)
        throws NoSuchAlgorithmException, NoSuchProviderException
    {
        KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");

        generator.init(256, random);

        return generator.generateKey();
    }

   
    public static IvParameterSpec createCtrIvForAES(int messageNumber,
                                                          SecureRandom random)
    {
        byte[]          ivBytes = new byte[16];

        // initially randomize

        random.nextBytes(ivBytes);

        // set the message number bytes

        ivBytes[0] = (byte)(messageNumber >> 24);
        ivBytes[1] = (byte)(messageNumber >> 16);
        ivBytes[2] = (byte)(messageNumber >> 8);
        ivBytes[3] = (byte)(messageNumber >> 0);

        // set the counter bytes to 1

        for (int i = 0; i != 7; i++)
        {
            ivBytes[8 + i] = 0;
        }

        ivBytes[15] = 1;

        return new IvParameterSpec(ivBytes);
    }

   
    public static String toString(byte[] bytes, int length)
    {
        char[] chars = new char[length];

        for (int i = 0; i != chars.length; i++)
        {
            chars[i] = (char)(bytes[i] & 0xff);
        }

        return new String(chars);
    }

    
    public static String toString(byte[] bytes)
    {
        return toString(bytes, bytes.length);
    }
   
    public static byte[] toByteArray(String string)
    {
        byte[] bytes = new byte[string.length()];
        char[] chars = string.toCharArray();

        for (int i = 0; i != chars.length; i++)
        {
            bytes[i] = (byte)chars[i];
        }

        return bytes;
    }
}

