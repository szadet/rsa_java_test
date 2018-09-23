package authroization;

import java.util.List;

public class Server extends AccountService implements IAccount,IAccountService{
	protected AccountService accountService = new AccountService();
	protected String name= "None";
	protected Session session;
	protected MediumAdapter mediumAdapter;

	@Override
	public User login(String login, String password) {
		System.out.println("Login " + login);
		
		for (User user : accountService.SelectAllUser()) {
			if (user.getLogin().equals(login)) {
				if (user.getPassword().equals(password)) {
					return user;
				} else {
					try {
						throw new Exception("Acces denied:");
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
			}
			try {
				throw new Exception("User not found");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void logout() {
	}

	@Override
	public Session connect(Session session) {
		session.setServerName(name);
		session.setEncryption("none");
		this.session = session;
		return session;
	}
	
	public void setMediumAdapter(MediumAdapter mediumAdapter) {
		this.mediumAdapter = mediumAdapter;
	}
	
	public MediumAdapter getMediumAdapter(MediumAdapter mediumAdapter) {
		return this.mediumAdapter;
	}
}
