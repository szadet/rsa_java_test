package single_thread;
import java.math.BigInteger;

import abstracts.AbstractRSA;

public class ReferenceRSA extends AbstractRSA{

	@Override
	protected BigInteger modularExponent(BigInteger base, BigInteger exponent, BigInteger modulo) {
		return base.modPow(exponent, modulo);
	}

	@Override
	protected BigInteger modMul(BigInteger a, BigInteger b, BigInteger m) {
		return a.multiply(b).mod(m);
	}

	@Override
	protected BigInteger normalize(BigInteger a, BigInteger R_2) {
		// TODO Not_Used
		return null;
	}

	@Override
	public void generateKeys(int keySize, BigInteger publicKey) {
		// NOT NEEDED!!!
	}
}
