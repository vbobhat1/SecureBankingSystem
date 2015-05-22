package bank;
import java.math.*;
import java.util.*;

public class Paillier 
{

	private BigInteger p, q, lambda;
	public BigInteger n;
	public BigInteger nsquare;
	private BigInteger g;
	private int bitLength;
	
	public Paillier(int bitLengthVal, int certainty) 
	{
		KeyGeneration(bitLengthVal, certainty);
	}

	public Paillier() 
	{
		//KeyGeneration(512, 64);
	}
	public Keys getKeys()
	{
		return KeyGeneration(512, 64);
	}
	public Keys KeyGeneration(int bitLengthVal, int certainty) 
	{
		bitLength = bitLengthVal;
		p = new BigInteger(bitLength / 2, certainty, new Random());
		q = new BigInteger(bitLength / 2, certainty, new Random());
		n = p.multiply(q);
		nsquare = n.multiply(n);
		g = new BigInteger("13");	
		lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)).divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
		if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) 
		{
			System.out.println("g is not good. Choose g again.");
			System.exit(1);
		}
		return new Keys(g,n,lambda,bitLength);
		
	}
	public BigInteger Encryption(BigInteger m, BigInteger r) 
	{
		return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
	}	

	public BigInteger Encryption(BigInteger m) 
	{
		BigInteger r = new BigInteger(bitLength, new Random());
		return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
	}
	public static BigInteger Encryption(BigInteger m,BigInteger g,BigInteger n) 
	{
		int bitLength=512;
		BigInteger nsquare=n.multiply(n);
		BigInteger r = new BigInteger(bitLength, new Random());
		return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
	}
	public BigInteger Decryption(BigInteger c) 
	{
		BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
		return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
	}

}
 

