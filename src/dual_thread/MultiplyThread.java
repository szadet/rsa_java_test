package dual_thread;

import java.math.BigInteger;

public class MultiplyThread extends AbstractLadderThread{
	public MultiplyThread(BigInteger r0, BigInteger r1, BigInteger exponent, BigInteger modulo, Semaphore write,
			Semaphore operation) {
		super(r0, r1, exponent, modulo, write, operation);
	}

	@Override
	protected void ladderOperation0() throws InterruptedException {		
		r1 = r0.multiply(r1).mod(modulo);

		semaphoreRight.send(r1);
		r0 = semaphoreLeft.receive();
	}

	@Override
	protected void ladderOperation1() throws InterruptedException {
	    r0 = r0.multiply(r1).mod(modulo);
	    
		semaphoreRight.send(r0);
		r1 = semaphoreLeft.receive();
	}

}
