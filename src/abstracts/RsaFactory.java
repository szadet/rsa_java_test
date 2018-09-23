package abstracts;
import javax.naming.Reference;

import crt_thread.CrtRSA;
import dual_thread.DualThreadRsa;
import single_thread.ReferenceRSA;
import single_thread.SimpleRSA;

public class RsaFactory {
	public ICrypt create(String algorithmType, int keySize, int e) {
		ICrypt crypt;
		
		switch (algorithmType) {
          case "single":	{ crypt = (ICrypt) new SimpleRSA(); break;}
          case "dual":		{ crypt = (ICrypt) new DualThreadRsa(); break; }
          case "reference": { crypt = (ICrypt) new ReferenceRSA(); break; }
          case "crt":		{ crypt = (ICrypt) new CrtRSA(); break; }
        
          default:
             throw new IllegalArgumentException("Type " + algorithmType + " not available");
		}

		crypt.generateKeys(keySize, e);
		return crypt;
	}
}
