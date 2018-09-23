package abstracts;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;

public abstract class AbstractRSA implements ICrypt{
	protected BigInteger privateKey;
	protected BigInteger publicKey;
	protected BigInteger modulo;
	protected BigInteger p; //Hold internally
	protected BigInteger q;
	
	public byte[] encrypt(byte[] message) {
		BigInteger m = new BigInteger(message);
	
		return modularExponent(m, publicKey, publicKey).toByteArray();
	}

	public String encrypt(String message) {
		BigInteger m = new BigInteger(message.getBytes());
		return encrypt(m).toString();
	}
	
	public BigInteger decrypt(BigInteger cypher) {
		return modularExponent(cypher, privateKey, modulo);
	}
	
	public String decrypt(String cypher) {
		BigInteger c = new BigInteger(cypher);
		
		return new String(decrypt(c).toByteArray());
	}
	
	public byte[] decrypt(byte[] cypher) {
		return decrypt(new BigInteger(cypher)).toByteArray();
	}
	
	public BigInteger encrypt(BigInteger message) {
		return modularExponent(message, publicKey, modulo);
	}
	
	public void setPrivateKey(BigInteger privateKey) {
		this.privateKey = privateKey; 
	}
	
	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}
	
	public void setModulo(BigInteger modulo) {
		this.modulo = modulo;
	}
	
	public void setPrivateKey(String privateKey) {
		this.privateKey = new BigInteger(privateKey); 
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = new BigInteger(publicKey); 
	}
	
	public void setModulo(String modulo) {
		this.modulo = new BigInteger(modulo); 
	}
	
	public BigInteger getPrivateKey() {
		return this.privateKey;
	}
	
	public BigInteger getPublicKey() {
		return this.publicKey;
	}
	
	public BigInteger getModulo() {
		return this.modulo;
	}
	
	public void generateKeys(int keySize, BigInteger publicKey) {		
	    SecureRandom random = new SecureRandom();
		
        p = BigInteger.probablePrime(keySize/2,random);
        q = BigInteger.probablePrime(keySize/2,random);
        
        BigInteger modulo = p.multiply(q);
        BigInteger euler = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        
        if (publicKey.gcd(euler).equals(BigInteger.ONE)) {
        	setPrivateKey(publicKey.modInverse(euler));
        } else {
        	generateKeys(keySize, publicKey);
        }
        
        setModulo(modulo);
        setPublicKey(publicKey);
	}
	
	public void generateKeys(int keySize, int publicKey) {
		generateKeys(keySize, BigInteger.valueOf(publicKey));
	}
	
	public void copyKeys(ICrypt rsaAlgorithm) {
		AbstractRSA rsa = (AbstractRSA) rsaAlgorithm;
		
		this.privateKey = rsa.getPrivateKey();
		this.publicKey  = rsa.getPublicKey();
		this.modulo     = rsa.getModulo();
	}
	
	public String getPublicA() {
		return this.modulo.toString();
		
	}
	public String getPublicB() {
		return this.publicKey.toString();
	}
	
	public void setPublicA(String key) {
		this.setModulo(new BigInteger(key));
	}
	public void setPublicB(String key) {
		this.setPublicKey(new BigInteger(key));
	}
	
	protected abstract BigInteger modularExponent(BigInteger base, BigInteger exponent, BigInteger modulo);
	protected abstract BigInteger modMul(BigInteger a, BigInteger b, BigInteger m);
	protected abstract BigInteger normalize(BigInteger a, BigInteger R_2); //R_2 may not be used
}
