package databaseConnectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.TreeSet;

import datatypes.H2List;

public class H2Connector extends DatabaseConnector {

	String dbPath, sql;
	Connection connection;
	
	public H2Connector(String dbPath, String username, String password, String sql) throws ClassNotFoundException, SQLException {
		this.dbPath = dbPath;
		this.sql = sql;
		
		Class.forName("org.h2.Driver");
		connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/" + dbPath, username, password);
	}
	
	public String resolveID (String id){
		String value = null;
		
		try {
			String sql = "SELECT Entity FROM EntityID WHERE ID IS (?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(id));
			preparedStatement.executeQuery();
			ResultSet result = preparedStatement.getResultSet();
			while (result.next()) {
				value = result.getString("Entity");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}

	@Override
	public LinkedList<String> getFragmentTargets(String fragment) {
		LinkedList<String> list = new LinkedList<String>();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, fragment);
			preparedStatement.executeQuery();
			ResultSet result = preparedStatement.getResultSet();
			String tmp = null;
			while (result.next()) {
				tmp = result.getString(1);
			}
			TreeSet<String> tmpSet = H2List.stringToSet(tmp);
			list = new LinkedList<String>(tmpSet);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean fragmentExists(String fragment) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}