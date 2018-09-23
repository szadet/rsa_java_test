package single_thread;
import java.math.BigInteger;
import java.security.SecureRandom;

import abstracts.AbstractRSA;

public class SimpleRSA extends AbstractRSA {
	@Override
	protected BigInteger modularExponent(BigInteger base, BigInteger exponent, BigInteger modulo) {
		BigInteger r0 = new BigInteger("1");
		BigInteger r1 = base.mod(modulo);
		BigInteger e = exponent.mod(modulo);
      
        for (int k=0; k<e.bitLength(); k++){ 
         	if (e.testBit(k))
        		r0 = modMul(r0, r1, modulo);

         	r1 = modMul(r1, r1, modulo);
        } 
        return r0; 
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
