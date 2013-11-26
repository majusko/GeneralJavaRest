package sk.kapusta.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import sk.kapusta.dao.connection.DatabaseConnection;

public class BaseDAO {
	
	public final DatabaseConnection connection;
	
	public BaseDAO(){
		
		final String url= "jdbc:mysql://localhost:3306/";
		final String dbName = "test";
		final String driver = "com.mysql.jdbc.Driver";
		final String userName = "root";
		final String password = "";
		
		connection = DatabaseConnection.getDbCon(url, dbName, driver, userName, password);
		
	}
	
	public PreparedStatement prepareQuery(String query) throws SQLException {
		
		final PreparedStatement preparedStatement = connection.conn.prepareStatement(query);
		
		return preparedStatement;
		
	}
	
	public String inClauseBuilder(String query, int size){
		
		query = query + " IN ( ";
		for(int i = 0; i < size; i++){
			query = query + " ?, ";
		}
		query = query.substring(0, query.length()-2);
		query = query + " ) ";
		
		return query;
		
	}

}
