package dual_thread;

import java.math.BigInteger;

public class SquareThread extends AbstractLadderThread{

	public SquareThread(BigInteger r0, BigInteger r1, BigInteger exponent, BigInteger modulo, Semaphore write,
			Semaphore operation) {
		super(r0, r1, exponent, modulo, write, operation);
	}

	@Override
	protected void ladderOperation0() throws InterruptedException {	
		r0 = r0.multiply(r0).mod(modulo);

		r1= semaphoreRight.receive();
		semaphoreLeft.send(r0);
	}

	@Override
	protected void ladderOperation1() throws InterruptedException {
		r1 = r1.multiply(r1).mod(modulo);
		
		r0= semaphoreRight.receive();
		semaphoreLeft.send(r1);
	}
}
