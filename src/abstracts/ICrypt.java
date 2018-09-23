package abstracts;
import java.math.BigInteger;

public interface ICrypt {
	public byte[] encrypt(byte[] message);
	public byte[] decrypt(byte[] cypher);
	public String encrypt(String message);
	public String decrypt(String cypher);
	public BigInteger encrypt(BigInteger message);
	public BigInteger decrypt(BigInteger cypher);
	public void generateKeys(int keySize, BigInteger publicKey);
	public void generateKeys(int keySize, int publicKey);

//Dla wymiany kluczy
	public String getPublicA(); //Assuming that we have two parts of keys
	public String getPublicB();
	public void setPublicA(String key);
	public void setPublicB(String key);
//
	public void copyKeys(ICrypt rsaAlgorithm); //FIXME: For test purpose only. Remove it.
}
