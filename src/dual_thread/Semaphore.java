package dual_thread;

import java.math.BigInteger;

public class Semaphore {
	private boolean signal = false;
	private BigInteger value = new BigInteger("0");

	public synchronized void take() {
		this.signal = true;
		this.notify();
	}

	public synchronized void release() throws InterruptedException{
		while(!this.signal) wait();
		this.signal = false;
	}
	
	public synchronized void send(BigInteger value) throws InterruptedException {
		this.value = value;
		take();
	}
	
	public synchronized BigInteger receive() throws InterruptedException{
		release();
		return this.value;
	}
}