package authroization;
import java.util.List;

public interface IAccountService {
	public User CreateUser(String login, String firstName, String lastName, String password, String email);
	public void ChangePassword(User user, String newPassword);
	public void ChangeAddress(User user, String newAddress);
	public void DeleteUser(User user);
	public List<User> SelectAllUser();
}
