package assign3.part1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

	public Connection con;
	public Connection openConnection() throws SQLException{
		  // Load the Oracle database driver 
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
        //Establish the connection using the connection string
        con= DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl","system","oracle" );
       // System.out.println("Connected to the database");
        return con;
	}
	public void closeConnection(){
		 try { 
	           con.close();           
	           } catch (SQLException e) { 
	          System.err.println("Cannot close connection: " + e.getMessage()); 
	       } 
	}
}
