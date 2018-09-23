package crt_thread;

import java.math.BigInteger;
import java.security.SecureRandom;

import abstracts.AbstractRSA;
import dual_thread.DualThreadRsa;

public class CrtRSA extends DualThreadRsa {
	private BigInteger dp;
	private BigInteger dq;
	private BigInteger qinv;
	private runnableModuloPower pChannel = new runnableModuloPower();
	private runnableModuloPower qChannel = new runnableModuloPower();
	private Thread pThread = new Thread(pChannel);
	private Thread qThread = new Thread(qChannel);
	
	@Override
	public void generateKeys(int keySize, BigInteger publicKey) {		
	   super.generateKeys(keySize, publicKey);
	
	   dp = publicKey.modInverse(p.subtract(BigInteger.ONE));
	   dq = publicKey.modInverse(q.subtract(BigInteger.ONE));
	   qinv = q.modInverse(p);
	   
	   pChannel.setModulo(p);
	   qChannel.setModulo(q);
	   
	   pChannel.setPrivateKey(dp);
	   qChannel.setPrivateKey(dq);
	}
	
	@Override
	public BigInteger decrypt(BigInteger cypher) {
		pThread = new Thread(pChannel); //required
		qThread = new Thread(qChannel);
		
		pChannel.setCypher(cypher);
		qChannel.setCypher(cypher);
		
		pThread.start();
		qThread.start();

		try {
			pThread.join();
			qThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		BigInteger m1= pChannel.getMessage();
		BigInteger m2= qChannel.getMessage();
		
		BigInteger h = qinv.multiply(m1.subtract(m2)).mod(p);

		return m2.add(h.multiply(q));//.mod(modulo);
	}
}
