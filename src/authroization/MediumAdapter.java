package authroization;

public class MediumAdapter implements IAccount{
	Client client;
	Server server;
	
	public MediumAdapter(Client client, Server server){
		this.client = client;
		client.setMediumAdapter(this);
		this.server = server;
		server.setMediumAdapter(this);
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public Session connect(Session session){
		session = server.connect(session);
		session = client.connect(session);
		return session = server.connect(session);
	}

	@Override
	public User login(String login, String password) throws Exception {
		User user = client.login(login, password);
		user = server.login(user.getLogin(), user.getPassword());
		user = client.getUserData(user);
		return user;
	}

	@Override
	public void logout() { //TODO
	}
}
