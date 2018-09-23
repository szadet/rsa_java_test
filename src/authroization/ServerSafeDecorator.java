package authroization;

import abstracts.ICrypt;

public class ServerSafeDecorator extends Server implements IAccount,IAccountService{
	protected ICrypt saveConnection;
	protected String encryption;
	
	public ServerSafeDecorator(AccountService accountService, ICrypt crypt, String encryption){
		Server server = (Server) accountService;
		
		this.name = server.name;
		this.saveConnection = crypt;
		this.encryption = encryption;
		
		this.mediumAdapter = server.mediumAdapter;
		
		if (mediumAdapter != null) { //might be not connected yet
			System.out.println("Klient ustawil adapter");
			this.mediumAdapter.setServer(this);
		}
		this.name= server.name;
	}
	
	public User login(String login, String password) {
		String decryptedLogin = saveConnection.decrypt(login);
		String decryptedPassword= saveConnection.decrypt(password);
		User user = super.login(decryptedLogin, decryptedPassword);
		
		saveConnection.setPublicA(session.getClientKeyA());
		saveConnection.setPublicB(session.getClientKeyB());
		
		user.setLogin(saveConnection.encrypt(user.getLogin()));
		user.setPassword(saveConnection.encrypt(user.getPassword()));
			
		user.setEmail(saveConnection.encrypt(user.getEmail()));
		user.setFirstName(saveConnection.encrypt(user.getFirstName()));
		user.setLastName(saveConnection.encrypt(user.getLastName()));
		user.setRegistration(user.getRegistration());
			
		saveConnection.setPublicA(session.getServerKeyA());
		saveConnection.setPublicB(session.getServerKeyB());
			
		return user;
	}
	
	public User CreateUser(String login, String firstName, String lastName, String password, String email) {
		String decryptedLogin =  saveConnection.decrypt(firstName);
		String decryptedFirstName =  saveConnection.decrypt(lastName);
		String decryptedLastName = saveConnection.decrypt(password);
		String decryptedPassword = saveConnection.decrypt(password);
		String decryptedEmail = saveConnection.decrypt(password);
		
		return super.CreateUser(decryptedLogin, decryptedFirstName, decryptedLastName, decryptedPassword, decryptedEmail);
	}
	
	public void ChangePassword(User user, String newPassword) {
		String decryptedPassword= saveConnection.decrypt(newPassword);
	
		super.ChangePassword(user, decryptedPassword);
	}

	public void ChangeAddress(User user, String newAddress) {
		String decryptedAddress= saveConnection.decrypt(newAddress);
		
		super.ChangePassword(user, decryptedAddress);
	}
	
	public void DeleteUser(User user) {
		super.DeleteUser(user);
	}
	
	@Override
	public Session connect(Session session) {
		session = super.connect(session);

		session.setServerKeyA(saveConnection.getPublicA());
		session.setServerKeyB(saveConnection.getPublicB());
		session.setEncryption(this.encryption);
		
		this.session = session;
		return session;
	}
}
