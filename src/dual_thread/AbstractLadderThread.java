package dual_thread;

import java.math.BigInteger;

public abstract class AbstractLadderThread implements Runnable{
	protected volatile BigInteger r0;
	protected volatile BigInteger r1;
	
	private BigInteger exponent;
	protected BigInteger modulo;
	Semaphore semaphoreLeft;
	Semaphore semaphoreRight;
	
//Semaphores	
	protected boolean operationDone;
	protected boolean writelDone;
	
	public AbstractLadderThread(BigInteger r0, BigInteger r1, BigInteger exponent, BigInteger modulo, Semaphore write, Semaphore operation) {
		this.r0 = r0;
		this.r1 = r1;
		this.exponent = exponent;
		this.modulo = modulo;

		this.semaphoreLeft = write;
		this.semaphoreRight = operation;
	}
	
	@Override
	public void run() {
		for (int index=exponent.bitLength()-1; index>=0; index--){
			
			if (exponent.testBit(index)){
	        	try {
					ladderOperation1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
	        	try {
					ladderOperation0();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	    }
	};
	
	protected abstract void ladderOperation0() throws InterruptedException;
	protected abstract void ladderOperation1()  throws InterruptedException;

	public BigInteger getR0() {
		return r0;
	}
}
