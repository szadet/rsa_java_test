import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Random;

import org.eclipse.swt.widgets.Display;
import org.junit.jupiter.api.Test;

import abstracts.ICrypt;
import abstracts.RsaFactory;
import authroization.Client;
import authroization.ClientSafeDecorator;
import authroization.MediumAdapter;
import authroization.Server;
import authroization.ServerSafeDecorator;
import authroization.Session;
import authroization.User;
import crt_thread.CrtRSA;
import dual_thread.DualThreadRsa;
import gui.UserGUI;
import single_thread.ReferenceRSA;
import single_thread.SimpleRSA;

class Tester {
	@Test
	void crypt1024simple() {
		ReferenceRSA ref = new ReferenceRSA();
		SimpleRSA simpleRSA = new SimpleRSA();
		
		simpleRSA.generateKeys(1024, new BigInteger("19"));
		
		ref.setPrivateKey(simpleRSA.getPrivateKey());
		ref.setPublicKey(simpleRSA.getPublicKey());
		ref.setModulo(simpleRSA.getModulo());

		BigInteger message = new BigInteger(1024, new Random());
		BigInteger simpleCypher = simpleRSA.encrypt(message); 
		BigInteger refCypher = ref.encrypt(message);
		
		if (!simpleCypher.equals(refCypher))
			fail("Encryption failed");
		
		if (!simpleRSA.decrypt(simpleCypher).equals(message))
			fail("Decyption failed");
	}

	@Test
	void crypt1024dual() {
		DualThreadRsa dualRSA = new DualThreadRsa();
		ReferenceRSA ref = new ReferenceRSA();
		
		dualRSA.generateKeys(1024, new BigInteger("19"));
		
		ref.setPrivateKey(dualRSA.getPrivateKey());
		ref.setPublicKey(dualRSA.getPublicKey());
		ref.setModulo(dualRSA.getModulo());

		BigInteger message = new BigInteger(1024, new Random());
		BigInteger dualCypher = dualRSA.encrypt(message);
		BigInteger refCypher = ref.encrypt(message);
		
		
		if (!dualCypher.equals(refCypher))
			fail("Encryption failed");
		else
			System.out.println("Encryption success");
		
		BigInteger decrypted = dualRSA.decrypt(refCypher);
		
		if (!decrypted.equals(message.mod(dualRSA.getModulo()))) {
			System.out.println(decrypted);
			System.out.println(message);
			fail("Decyption failed");
		} else
			System.out.println("Decryption success");
	}
	
	@Test
	void crypt2048dual() {
		DualThreadRsa dualRSA = new DualThreadRsa();
		ReferenceRSA ref = new ReferenceRSA();
		
		dualRSA.generateKeys(2048, new BigInteger("19"));
		
		ref.setPrivateKey(dualRSA.getPrivateKey());
		ref.setPublicKey(dualRSA.getPublicKey());
		ref.setModulo(dualRSA.getModulo());

		BigInteger message = new BigInteger(1024, new Random());
		BigInteger dualCypher = dualRSA.encrypt(message);
		BigInteger refCypher = ref.encrypt(message);
			
		if (!dualCypher.equals(refCypher))
			fail("Encryption failed");
		else
			System.out.println("Encryption success");
		
		BigInteger decrypted = dualRSA.decrypt(refCypher);
		
		if (!decrypted.equals(message.mod(dualRSA.getModulo()))) {
			System.out.println(decrypted);
			System.out.println(message);
			fail("Decyption failed");
		} else
			System.out.println("Decryption success");
	}
	
	@Test
	void comparison2048() {
		RsaFactory factory = new RsaFactory();
		ICrypt single = factory.create("single",2048,19);
		ICrypt dual = factory.create("dual",2048,19);
		ICrypt reference = factory.create("reference",2048,19);
		ICrypt crt = factory.create("crt",2048,19);
		
		testRSA(single, dual, crt, reference, new BigInteger(1024, new Random()));
	}
	
	@Test
	void comparison4096() {
		RsaFactory factory = new RsaFactory();
		ICrypt single = factory.create("single",4096,19);
		ICrypt dual = factory.create("dual",4096,19);
		ICrypt reference = factory.create("reference",4096,19);
		ICrypt crt = factory.create("crt",4096,19);
		
		testRSA(single, dual, crt, reference, new BigInteger(2048, new Random()));
	}
	
	@Test
	void comparison512() {
		RsaFactory factory = new RsaFactory();
		ICrypt single = factory.create("single",512,19);
		ICrypt dual = factory.create("dual",512,19);
		ICrypt reference = factory.create("reference",512,19);
		ICrypt crt = factory.create("crt",512,19);
		
		testRSA(single, dual, crt, reference, new BigInteger(256, new Random()));
	}
	
	@Test
	void comparison192() {
		RsaFactory factory = new RsaFactory();
		ICrypt single = factory.create("single",192,19);
		ICrypt dual = factory.create("dual",192,19);
		ICrypt reference = factory.create("reference",192,19);
		ICrypt crt = factory.create("crt",192,19);
		
		testRSA(single, dual, crt, reference, new BigInteger(192, new Random()));
	}
	
	@Test
	void serverTest() {		
		Server server= new Server();
		Client client= new Client();
		
		server.setName("Serwer");
		client.setName("Client");
		MediumAdapter mediumAdapter = new MediumAdapter(client, server);
		
		Session session= client.getMediumAdapter().connect(client.createSession());
		
		try {
			User user = client.getMediumAdapter().login("tomek", "tomek");
			
			if (!user.getFirstName().equals("tomek") || !user.getLastName().equals("tomek"))
				fail("Not connected");
		} catch (Exception e) {
			fail("Bad connection");
			e.printStackTrace();
		}
	}
	
	@Test
	void secureServerTest1024single() {		
		Server server= new Server();
		Client client= new Client();
		
		server.setName("Serwer");
		client.setName("Client");
		
		server = new ServerSafeDecorator(server, new RsaFactory().create("single", 1024, 19),"rsa1024");
		client = new ClientSafeDecorator(client, "single");
		
		MediumAdapter mediumAdapter = new MediumAdapter(client, server);
		
		Session session= client.getMediumAdapter().connect(client.createSession());
		try {
			User user = client.getMediumAdapter().login("tomek", "tomek");
			
			if (!user.getFirstName().equals("tomek") || !user.getLastName().equals("tomek"))
				fail("Not connected");
		} catch (Exception e) {
			fail("Bad connection");
			e.printStackTrace();
		}
	}
	
	@Test
	void secureServerTest2048single() {		
		Server server= new Server();
		Client client= new Client();
		
		server.setName("Serwer");
		client.setName("Client");
		
		server = new ServerSafeDecorator(server, new RsaFactory().create("single", 2048, 19),"rsa2048");
		client = new ClientSafeDecorator(client, "single");
		
		MediumAdapter mediumAdapter = new MediumAdapter(client, server);
		
		Session session= client.getMediumAdapter().connect(client.createSession());
		try {
			User user = client.getMediumAdapter().login("tomek", "tomek");
			
			if (!user.getFirstName().equals("tomek") || !user.getLastName().equals("tomek"))
				fail("Not connected");
		} catch (Exception e) {
			fail("Bad connection");
			e.printStackTrace();
		}
	}
	
	private void testRSA(ICrypt single, ICrypt dual, ICrypt crt, ICrypt reference, BigInteger message){
		reference.copyKeys(crt);
		dual.copyKeys(crt);
		single.copyKeys(crt);
		
		BigInteger refMessage;
		BigInteger singleMessage;
		BigInteger dualMessage;
		BigInteger crtMessage;
		
		BigInteger refCypher;
		BigInteger singleCypher;
		BigInteger dualCypher;
		BigInteger crtCypher = null;
		
		long start;
		
		System.out.println("Encrypt");
		start = System.currentTimeMillis();
		refCypher = reference.encrypt(message);
		System.out.println("Reference = " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();
		singleCypher = single.encrypt(message);
		System.out.println("Single = " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();
		dualCypher = dual.encrypt(message);
		System.out.println("Dual = " + (System.currentTimeMillis() - start) + "ms");
		
		System.out.println("Decrypt");
		start = System.currentTimeMillis();
		refMessage = reference.decrypt(refCypher);
		System.out.println("Reference = " + (System.currentTimeMillis() - start) + "ms");
		if (!refMessage.equals(message))
			fail("Reference Failed - key inproper generation");	
		
		start = System.currentTimeMillis();
		singleMessage = single.decrypt(refCypher);
		System.out.println("Single = " + (System.currentTimeMillis() - start) + "ms");
		if (!singleMessage.equals(message))
			fail("Single Failed");	
		
		start = System.currentTimeMillis();
		dualMessage = dual.decrypt(refCypher);
		System.out.println("Dual = " + (System.currentTimeMillis() - start) + "ms");
		if (!dualMessage.equals(message))
			fail("Dual Failed");	
		
		start = System.currentTimeMillis();
		crtMessage = crt.decrypt(refCypher);
		System.out.println("CRT = " + (System.currentTimeMillis() - start) + "ms");
		//if (!crtMessage.equals(message))
		//	fail("CRT Failed");
	}
}