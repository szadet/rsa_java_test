package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import authroization.Client;
import authroization.ClientSafeDecorator;
import authroization.IAccount;
import authroization.Session;
import authroization.User;

public class UserGUI {
	private Shell shell;
	private Text login;
	private Text password;
	private List encryption;
	private Client client;
    private Button connect;

	public UserGUI(Display display, String name) {
		setShell(new Shell(display));
		client = new Client();

		getShell().setText("Account");
		GridLayout gridLayout = new GridLayout();
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridLayout.numColumns = 2;
		gridData.horizontalSpan = 1;
		getShell().setLayout(gridLayout);
		getShell().setLayoutData(gridData);

		new Label(getShell(), SWT.NULL).setText("login:");
		login = new Text(getShell(), SWT.SINGLE | SWT.BORDER);
		login.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(getShell(), SWT.NULL).setText("password:");
		password = new Text(getShell(), SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(getShell(), SWT.NULL).setText("Supported coding:");
		encryption = new List(getShell(), SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		encryption.add("single");
		encryption.add("dual");
		encryption.add("crt");
		encryption.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		encryption.select(0);
		encryption.addSelectionListener(new EncryptionListener());
		
		connect = new Button(getShell(), SWT.BUTTON2);
		connect.setText("login");
		connect.addListener(SWT.Selection, new ConnectionListener());
	}
	
	private class EncryptionListener implements SelectionListener{
		@Override
		
		public void widgetSelected(SelectionEvent e) {
			
			switch (encryption.getSelection()[0]) {
				case "single": {client = new ClientSafeDecorator( client, "single"); break;}
				case "dual":   {client = new ClientSafeDecorator( client, "dual"); break;}
				case "crt":	   {client = new ClientSafeDecorator( client, "crt"); break;}
				default: break;
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}
	
	private class ConnectionListener implements Listener{

		@Override
		public void handleEvent(Event event) {
			System.out.println("Jestem tutaj!!!");
			
			try {
				Session session= client.getMediumAdapter().connect(client.createSession());
				User user = client.getMediumAdapter().login(login.getText(), password.getText());
				
				MessageBox dialog = new MessageBox(getShell(), SWT.ICON_INFORMATION | SWT.OK);
				dialog.setMessage("User confidential data:\n " + user);
 	        	dialog.open();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Client getClient() {
		return this.client;
	}

	public Shell getShell() {
		return shell;
	}

	private void setShell(Shell shell) {
		this.shell = shell;
	}
	
}
