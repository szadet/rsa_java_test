package authroization;
import java.sql.ResultSet;
import java.util.List;
import java.sql.SQLException;

public class AccountService extends AbstractService implements IAccountService {
	@Override
	public User CreateUser(String login, String firstName, String lastName, String password,
			String email) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLogin(login);
		user.setPassword(password);
		user.setEmail(email);
		
		synchronize(user,DBOP.INSERT);
		return user;
	}

	@Override
	public void ChangePassword(User user, String newPassword) {
		user.setPassword(newPassword);
		synchronize(user, DBOP.UPDATE);
	}
	
	@Override
	public void ChangeAddress(User user, String newAddress) {
		user.setEmail(newAddress);
		synchronize(user, DBOP.UPDATE);
	}

	@Override
	public void DeleteUser(User user) {
		synchronize(user, DBOP.DELETE);
	}

	@Override
	public List<User> SelectAllUser() {
		return selectAll();
	}
	
	@Override
	protected String getInsertCommand(Object object) {
		User user = (User) object;
		
		String sql = "INSERT INTO `users` "
			    + "(login, password, firstName, lastName, email, registration) "
				+ "VALUES ("
				+ "'" + user.getLogin() + "', " 
				+ "'" + user.getPassword() + "', " 
				+ "'" + user.getFirstName() + "', " 
				+ "'" + user.getLastName() + "', " 
				+ "'" + user.getEmail() + "', " 
				+ "NOW()" + ");";
		return sql;
	}

	@Override
	protected void setId(Object object, int id) {
		// TODO Auto-generated method stub //not used		
	}

	@Override
	protected String getUpdateCommand(Object object) {
		User user = (User) object;
		
		String sql = "UPDATE `users` " + 
				"SET login" + user.getLogin() + "," +
				"SET password" + user.getPassword() + "," +
				"SET firstName" + user.getFirstName() + "," +
				"SET lastName" + user.getLastName() + "," +
				"SET email" + user.getEmail() + "," +
				"WHERE login =" + user.getLogin() + ";";
		return sql;
	}

	@Override
	protected String getSelectCommad() {
		return "SELECT login, password, firstName, lastName, email, registration  FROM `users`;";
	}

	@Override
	protected Object createDbObject(ResultSet rs) throws SQLException {
    	User user = new User();
    	
    	user.setLogin(rs.getString("login"));
    	user.setPassword(rs.getString("password"));
    	user.setFirstName(rs.getString("firstName"));     
    	user.setLastName(rs.getString("lastName"));
    	user.setEmail(rs.getString("email"));
    	user.setRegistration(rs.getDate("registration"));
		return user;
	}

	@Override
	protected String getDelateCommand(Object object) {
		User user = (User) object;
		
		String sql = "DELTE `users` " + 
				"WHERE login =" + user.getLogin() + ";";
		return sql;
	}
}
