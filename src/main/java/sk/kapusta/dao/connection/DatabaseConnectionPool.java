package sk.kapusta.dao.connection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatabaseConnectionPool {

	private static DataSource dataSource;
	
    private static final String DRIVER_NAME;
    private static final String URL;
    private static final String UNAME;
    private static final String PWD;

    static {

    	DRIVER_NAME = "com.mysql.jdbc.Driver";
    	URL = "jdbc:mysql://localhost:3306/test";
    	UNAME = "root";
    	PWD = "";

    	if(dataSource == null){
    		
    		dataSource = setupDataSource();
    		
    	}
    	
    }

    public static Connection getConnection() throws SQLException {
    	
    	return dataSource.getConnection();
    	
    }

    private static DataSource setupDataSource() {
    	
    	final ComboPooledDataSource cpds = new ComboPooledDataSource();
    	
    	try {
    		
    		cpds.setDriverClass(DRIVER_NAME);
    		
    	} catch (PropertyVetoException e) {
    		
    		e.printStackTrace();
    		
    	}
    	
    	cpds.setJdbcUrl(URL);
    	cpds.setUser(UNAME);
    	cpds.setPassword(PWD);
    	cpds.setMinPoolSize(5);
    	cpds.setAcquireIncrement(5);
    	cpds.setMaxPoolSize(20);
    	
    	return cpds;
    	
    }
	
}
