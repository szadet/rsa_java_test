import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import abstracts.ICrypt;
import abstracts.RsaFactory;
import authroization.Client;
import authroization.ClientSafeDecorator;
import authroization.MediumAdapter;
import authroization.Server;
import authroization.ServerSafeDecorator;
import authroization.Session;
import authroization.User;
import gui.UserGUI;

public class Main {

	public static void main(String[] args) throws Exception {
		Display display = new Display();
		UserGUI userGUI = new UserGUI(display, "klient");
		
		Server server= new Server();
		server.setName("Serwer");
		server = new ServerSafeDecorator(server, new RsaFactory().create("crt", 4096, 19),"rsa4096");
		
		MediumAdapter mediumAdapter = new MediumAdapter(userGUI.getClient(), (Server)server);
		System.out.println("Medium connected");
		
		userGUI.getShell().pack();
		userGUI.getShell().open();
		
		while (!userGUI.getShell().isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
