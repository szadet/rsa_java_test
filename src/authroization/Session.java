package authroization;

public class Session { //TODO; Adding state
	private String serverName= "NOT_INITIALIZED";
	private String clientName= "NOT_INITIALIZED";
	private String encryption= "none";
	private String serverKeyA= "0"; //Normally public keys
	private String serverKeyB= "0";
	private String clientKeyA= "0"; //Normally public keys
	private String clientKeyB= "0";
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerKeyA() {
		return serverKeyA;
	}
	public void setServerKeyA(String keyA) {
		this.serverKeyA = keyA;
	}
	public String getServerKeyB() {
		return serverKeyB;
	}
	public void setServerKeyB(String keyB) {
		this.serverKeyB = keyB;
	}
	public String getEncryption() {
		return encryption;
	}
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	public String getClientKeyA() {
		return clientKeyA;
	}
	public void setClientKeyA(String clientKeyA) {
		this.clientKeyA = clientKeyA;
	}
	public String getClientKeyB() {
		return clientKeyB;
	}
	public void setClientKeyB(String clientKeyB) {
		this.clientKeyB = clientKeyB;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String toString() {
		return "------------------------------\n" +
			   "--serverName: " + getServerName() + "\n" +
			   "--clientName: " + getClientName()+ "\n" +
			   "--server keyA: " + getServerKeyA() + "\n" +
			   "--server keyB: " + getServerKeyB() + "\n" +
			   "--client keyA: " + getClientKeyA() + "\n" +
			   "--client keyB: " + getClientKeyB() + "\n" +
			   "------------------------------";
	}
}
