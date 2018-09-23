package authroization;

public class User {
	private String login;
    private String password;
    private String firstName;
	private String lastName;
	private String email;
	private java.util.Date registration;

	public User() {
		setRegistration(new java.util.Date());
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public java.util.Date getRegistration() {
		return registration;
	}
	public void setRegistration(java.util.Date registration){
		this.registration = registration;
	}
		
	@Override public String toString()
	{
		String outString = "---------------------------------\n"; 
		outString += "| LOGIN: " + getLogin() + "\n";
		outString += "| FIRST NAME: " + getFirstName() + "\n";
		outString += "| LAST NAME: " + getLastName() + "\n";
		outString += "| EMAIL: " + getEmail() + "\n";
		outString += "---------------------------------\n";
		
	    return outString;
	}
}
