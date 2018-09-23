package authroization;

import java.math.BigInteger;

import abstracts.ICrypt;
import abstracts.RsaFactory;

public class ClientSafeDecorator extends Client implements IAccount{
	protected ICrypt saveConnection;
	private ICrypt rsa2048;
	private ICrypt rsa1024;
	private ICrypt rsa4096;
	
	public ClientSafeDecorator(Client client, String encryptionEngine) {
	    rsa2048 = new RsaFactory().create(encryptionEngine, 2048, 19);
		rsa1024 = new RsaFactory().create(encryptionEngine, 1024, 19);
		rsa4096 = new RsaFactory().create(encryptionEngine, 4096, 19);
		
		this.mediumAdapter = client.mediumAdapter;
		if (mediumAdapter != null) {
			this.mediumAdapter.setClient(this);
		}
		this.name=  client.name;
	}
	
	@Override
	public Session connect(Session session) {
		session = super.connect(session);
		
		System.out.println("Session encryption: " + session.getEncryption());
		
		switch (session.getEncryption()){
			case "rsa2048":	{saveConnection= rsa2048; break;}	
			case "rsa4096":	{saveConnection= rsa4096; break;}	
			case "rsa1024":	{saveConnection= rsa1024; break;}	
			case "none":	{break;}

			default:
				try {
					throw new Exception("Server encryption is not supported by this client");
				} catch (Exception e) {
					e.printStackTrace();
				}	
		}
		session.setClientKeyA(saveConnection.getPublicA());
		session.setClientKeyB(saveConnection.getPublicB());

		return session;
	}
	
	@Override
	public User login(String login, String password) throws Exception {
		User user = super.login(login, password);
		
		saveConnection.setPublicA(session.getServerKeyA());
	    saveConnection.setPublicB(session.getServerKeyB());
		
	    user.setLogin(saveConnection.encrypt(login));
		user.setPassword(saveConnection.encrypt(password));	
		
		saveConnection.setPublicA(session.getClientKeyA());
		saveConnection.setPublicB(session.getClientKeyB());
		
		return user;
	}

	@Override
	public void logout() {
	}

	public User getUserData(User user) {
		user.setFirstName(saveConnection.decrypt(user.getFirstName()));
		user.setLastName(saveConnection.decrypt(user.getLastName()));
		user.setEmail(saveConnection.decrypt(user.getEmail()));
		user.setLogin(saveConnection.decrypt(user.getLogin()));
		user.setPassword(saveConnection.decrypt(user.getPassword()));
		user.setRegistration(user.getRegistration());
		
		return user;
	}
}
