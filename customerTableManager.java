import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerTableManager {
	// create a named constant for the DB URL we use to access our db using JDBC
		public final String DB_URL = "jdbc:ucanaccess://PettitFinalProject.accdb" ;
		
		// field to hold a pointer to our db connection
		private static Connection conn;

		public customerTableManager() throws SQLException {
			conn = DriverManager.getConnection(DB_URL);
		}
		
		public static ResultSet selectCustomers(String strName,  String strPhone) throws SQLException {
			// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
			// Not needed for strings since the like command finds all results when the search field blank
			
			String ourSQLSelect = "SELECT 	customerID AS ID 			, "
								+ "			customername AS Name		, "
								+ "			customerphone as Phone		 "
								+ "FROM customers "
								+ "WHERE "
								+ " 		customername LIKE 	 ? "
								+ " AND 	customerphone LIKE 	 ? ";
					
			// create our prepared statement
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
			
			// our prepared statement had place holders (?) we need to fill in in order to execute our query
			prepStmt.setString(1, "%" + strName + "%" );
			prepStmt.setString(2, "%" + strPhone + "%" );


			// execute the query
			ResultSet customerResults = prepStmt.executeQuery();
			return customerResults;
			
		}
		
		public int insertRecord(String strName, String strPhone) throws SQLException {
			// 		this method allows us to insert a record into our employees			
			int rows = 0;
			
			String ourSQLInsert = "INSERT INTO customers "
								+ " 		(customername, customerphone) "
								+ " VALUES 	(       ?,			 ?)" ;
			
			// create a prepared statement object
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
		
			// pass parameters to our prepared statement prepStmt
			prepStmt.setString( 1, strName);
			prepStmt.setString( 2, strPhone);
			
			// execute the insert
			rows = prepStmt.executeUpdate();
			prepStmt.close();
			
			return rows;
		}
		public static ResultSet selectUpdate( int customerID) throws SQLException {
			// create sql statement to select a record based on the passed in intID
			
			String ourSQLSelect = "SELECT customerID as ID				, "
					+ "						customername AS name		, "
					+ "						customerphone AS phone		 "	
					+ " FROM customers 								  "
					+ " WHERE customerID = ?								  ";
			
			
			// create a prepared statement 
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
			
			// insert the variables to our prepstmt
			prepStmt.setInt(1, customerID );
			
			ResultSet customerResults = prepStmt.executeQuery();
			
			return customerResults;
		}
		public int updateRecord (String strName, String strPhone, int id) throws SQLException {
			
			int rows = 0;
			
			String ourSQLUpdate = "UPDATE customers " 
								+ " SET "
								+ " 	customername 	=	?	, "
								+ " 	customerphone	=	?	 "
								+ " WHERE customerID    	=	?	  ";

			
			// create a prepared statement 
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
			
			// set our parms
			prepStmt.setString( 1, strName);
			prepStmt.setString( 2, strPhone);
			prepStmt.setInt(3, id);
			
			// execute the insert
			rows = prepStmt.executeUpdate();
			prepStmt.close();
			
			return rows;			
		}
		public int deleteRecord( int customerID) throws SQLException {
			// variable to hold rows affected
			int rows = 0;
			
			// build our delete statement
			String ourSQLDelete = "DELETE FROM customers " 
								+ "WHERE customerID = ? " ;

			// prepare our statement
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLDelete);
			
			// set our parms
			prepStmt.setInt(1, customerID);
			
			// get rows from delete
			rows = prepStmt.executeUpdate();
			prepStmt.close();
			
			// return the number of rows affected
			return rows;
		}
}
