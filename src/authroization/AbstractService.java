package authroization;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class AbstractService {
	private static final Confidential confidential = new Confidential();
	
//	private static final String dbClassName = "com.mysql.jdbc.Driver";
//  private static final String CONNECTION = "jdbc:mysql://127.0.0.1/account";
	protected enum DBOP {	INSERT, UPDATE, DELETE; }
	
	protected void synchronize(Object object, DBOP operation) {
		try {
			Connection connection = connect();
			System.out.println("Connected database successfully...");
			
			switch (operation) { //CRUD without read
				case INSERT: {insert(object, connection); break;}
				case UPDATE: {update(object, connection); break;}
				case DELETE: {delete(object, connection); break;}
			}
			connection.close();			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void delete(Object object, Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		
		String sql = getUpdateCommand(object);
		System.out.println(sql);
		stmt.execute(sql);
		
		stmt.close();
	}

	protected void update(Object object, Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		
		String sql = getUpdateCommand(object);
		System.out.println(sql);
		stmt.execute(sql);
		
		stmt.close();
	}
	
	private Connection connect() throws ClassNotFoundException, SQLException {
		System.out.println();
		Class.forName(confidential.dbClassName);

	    Properties p = new Properties();
		p.put("user",confidential.dbUser);
		p.put("password",confidential.dbPassword);
		
		return DriverManager.getConnection(confidential.CONNECTION,p);
	}

	protected void insert(Object object, Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		
		String sql = getInsertCommand(object);
		System.out.println(sql);
		stmt.execute(sql);

		stmt.execute("SELECT LAST_INSERT_ID();");
		
		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
		    setId(object, rs.getInt(1));
		}
		
		stmt.close();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> List<T> selectAll() {
		List<T> objects = new ArrayList<T>();
		
		try {
			Connection connection = connect();
			Statement stmt = connection.createStatement();
		    String sql = getSelectCommad();
		    
		    System.out.println(sql);
		    
		    ResultSet rs = stmt.executeQuery(sql);
		    while(rs.next()){
		    	objects.add((T) createDbObject(rs));	
		    }
		    rs.close();
		    stmt.close();
		    connection.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return objects;
	}
	
	protected abstract String getInsertCommand(Object object);
	protected abstract void setId(Object object, int id);
	protected abstract String getUpdateCommand(Object object);
	protected abstract String getDelateCommand(Object object);
	protected abstract String getSelectCommad();
	protected abstract Object createDbObject(ResultSet rs) throws SQLException;
}
