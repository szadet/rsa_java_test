package dual_thread;
import java.math.BigInteger;

import abstracts.AbstractRSA;

  
public class DualThreadRsa extends AbstractRSA{
	private static volatile BigInteger r0;
	private static volatile BigInteger r1;
	
	@Override
	protected BigInteger modularExponent(BigInteger base, BigInteger exponent, BigInteger modulo) {
		r0 = new BigInteger("1");
		r1 = base.mod(modulo);
		
		BigInteger e = exponent.mod(modulo);
		Semaphore semaphoreLeft = new Semaphore();
		Semaphore semaphoreRight = new Semaphore();
		
		MultiplyThread multiplyThread = new MultiplyThread(r0, r1, e, modulo, semaphoreLeft, semaphoreRight);
		SquareThread squareThread = new SquareThread(r0, r1, exponent, modulo, semaphoreLeft, semaphoreRight);
		
		Thread thread0 = new Thread(multiplyThread);
		Thread thread1 = new Thread(squareThread);
		
		thread0.start();
		thread1.start();
		
		try {
			thread0.join();
			thread1.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		assert(multiplyThread.getR0() == squareThread.getR0());
        return multiplyThread.getR0(); 
	}

	@Override
	protected BigInteger modMul(BigInteger a, BigInteger b, BigInteger m) {
		return a.multiply(b).mod(m);
	}

	@Override
	protected BigInteger normalize(BigInteger a, BigInteger R_2) {
		return null;
	}
}
