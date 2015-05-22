package pack6;

  import java.security.KeyPair;
  import java.security.KeyPairGenerator;
  import java.security.SecureRandom;

  public class Utils extends pack5.Utils
  {
      
    public static KeyPair generateRSAKeyPair()
       throws Exception
    {
       KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");

       kpGen.initialize(1024, new SecureRandom());

       return kpGen.generateKeyPair();
    }
    public static KeyPair generateDSAKeyPair()
       throws Exception
    {
       KeyPairGenerator kpGen = KeyPairGenerator.getInstance("DSA", "BC");

       kpGen.initialize(512, new SecureRandom());

       return kpGen.generateKeyPair();
    }

}
