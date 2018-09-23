package authroization;

public class Client implements IAccount{
	protected String name;
	protected Session session;
	protected MediumAdapter mediumAdapter;
	
	@Override
	public Session connect(Session session) {
		this.session = session;
		return session;
	}

	@Override
	public User login(String login, String password) throws Exception {
		User user = new User();
		user.setLogin(login);
		user.setPassword(login);
		
		return user;
	}
	
	public Session createSession() {
		Session session = new Session();
		session.setClientName(name);
		this.session = session;
		
		return  session;
	}

	@Override
	public void logout() {
	}

	public User getUserData(User user) { //Dummy
		return user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMediumAdapter(MediumAdapter mediumAdapter) {
		System.out.println("Client podlączył medium");
		this.mediumAdapter = mediumAdapter;
	}
	
	public MediumAdapter getMediumAdapter() {
		System.out.println("Medium zabralo klienta");
		return this.mediumAdapter;
	}
}
