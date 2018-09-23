package crt_thread;
import java.math.BigInteger;

import dual_thread.DualThreadRsa;

public class runnableModuloPower extends DualThreadRsa implements Runnable{
	private BigInteger message;
	private BigInteger cypher;
	
	public void setCypher(BigInteger cypher) {
		this.cypher= cypher;
	}
	
	public BigInteger getMessage() {
		return message;
	}
	
	@Override
	public void run() {
		message = decrypt(cypher);
	}	
}
