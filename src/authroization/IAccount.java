package authroization;

public interface IAccount {
	public Session connect(Session session);
	public User login(String login, String password) throws Exception;
	public void logout();
}
