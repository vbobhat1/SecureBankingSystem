package bank;
import java.math.BigInteger;
public class Keys implements java.io.Serializable
{
	private BigInteger g,n,lambda;
	private int bitLength;
	public Keys(){}
	public Keys(BigInteger g,BigInteger n)
	{
		this.g=g;
		this.n=n;
	}
	public Keys(BigInteger g,BigInteger n,BigInteger lambda,int bitlen)
	{
		this.g=g;
		this.n=n;
		this.lambda=lambda;
		bitLength=bitlen;
	}
	public BigInteger getG()
	{
		return g;
	}
	public BigInteger getN()
	{
		return n;
	}
	public BigInteger getLambda()//private key
	{
		return lambda;
	}
	public int getBitLength()
	{
		return bitLength;
	}
}