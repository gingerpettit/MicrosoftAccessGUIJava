import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class employeeTableManager {
	// create a named constant for the DB URL we use to access our db using JDBC
		public final String DB_URL = "jdbc:ucanaccess://PettitFinalProject.accdb" ;
		
		// field to hold a pointer to our db connection
		private static Connection conn;

		public employeeTableManager() throws SQLException {
			conn = DriverManager.getConnection(DB_URL);
		}
		
		public static ResultSet selectEmployees(String strName, String strAddress, String strPhone, String strPosition) throws SQLException {
			// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
			// Not needed for strings since the like command finds all results when the search field blank
			
			String AddOnPosition = "";
			if (strPosition != "") {
				AddOnPosition = " AND employeeposition LIKE ? ";
			} 
			
			String ourSQLSelect = "SELECT 	e.employeeID as ID, "
					+ "			e.employeename AS Name, "
					+ "			e.employeeaddress AS Address, "
					+ "			e.employeephone AS Phone, "
					+ "			p.permissionname AS Position "
					+ "FROM employees e "
					+ " LEFT JOIN permissions p ON p.permissionname =  e.employeeposition "
					+ "WHERE "
					+ " 		employeename LIKE 	 ? "
					+ " AND 	employeeaddress LIKE ? "
					+ " AND 	employeephone LIKE 	 ? "
					+ AddOnPosition;
					
			// create our prepared statement
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
			
			// our prepared statement had place holders (?) we need to fill in in order to execute our query
			prepStmt.setString(1, "%" + strName + "%" );
			prepStmt.setString(2, "%" + strAddress + "%" );
			prepStmt.setString(3, "%" + strPhone + "%" );
			

			int columnNumber = 4;
			
			if ( AddOnPosition.length() > 0) {
				prepStmt.setString(columnNumber, "%" + strPosition + "%");
				columnNumber++ ;
			}
		
			// execute the query
			ResultSet employeeResults = prepStmt.executeQuery();
			return employeeResults;
			
		}
		
		public int insertRecord(String strName, String strAddress, String strPhone, String strPosition) throws SQLException {
			// 		this method allows us to insert a record into our employees			
			int rows = 0;

			
	  		String empPos;
	  		String ourSQLPos = "SELECT permissionname from permissions where permissionname = ?";  
	  		// Create a Statement object.
	  		PreparedStatement prepPos = conn.prepareStatement(ourSQLPos);
	  		prepPos.setString(1, strPosition);
	  		ResultSet posResults = prepPos.executeQuery();
	  		//Gets the result of what was returned from the database for the name passed to the method
	  		while (posResults.next()) {
	  			empPos = posResults.getString("permissionname");
	  		}

	  		
	  		String ourSQLInsert = "INSERT INTO employees "
								+ " 		(employeename, employeeaddress, employeephone, employeeposition) "
								+ " VALUES 	(       ?,			 ?,			?,			?)" ;
			
			// create a prepared statement object
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
		
			// pass parameters to our prepared statement prepStmt
			prepStmt.setString( 1, strName);
			prepStmt.setString( 2, strAddress);
			prepStmt.setString( 3, strPhone);
			prepStmt.setString(4, strPosition);
			
			
			// execute the insert
			rows = prepStmt.executeUpdate();
			prepStmt.close();
			
			return rows;
		}
		public static ResultSet selectUpdate( int employeeID) throws SQLException {
			// create sql statement to select a record based on the passed in intID
			
			String ourSQLSelect = "SELECT employeeID as ID				, "
					+ "						employeename AS name		, "
					+ "						employeeaddress AS address	, "
					+ "						employeephone AS phone		, "
					+ " 					employeeposition AS position"	
					+ " FROM employees 								  "
					+ " WHERE employeeID = ?								  ";
			
			
			// create a prepared statement 
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
			
			// insert the variables to our prepstmt
			prepStmt.setInt(1, employeeID );
			
			ResultSet employeeResults = prepStmt.executeQuery();
			
			return employeeResults;
		}
		public int updateRecord (String strName, String strAddress, String strPhone, String strPosition, int id) throws SQLException {
			
			int rows = 0;
			
			String ourSQLUpdate = "UPDATE employees " 
								+ " SET "
								+ " 	employeename 	=	?	, "
								+ " 	employeeaddress =	?   , "
								+ " 	employeephone	=	?	, "
								+ " 	employeeposition	=	?	  "
								+ " WHERE employeeID    	=	?	  ";

			
			// create a prepared statement 
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
			
			// set our parms
			prepStmt.setString( 1, strName);
			prepStmt.setString( 2, strAddress);
			prepStmt.setString( 3, strPhone);
			prepStmt.setString( 4, strPosition);
			prepStmt.setInt( 	5, id);
			
			// execute the insert
			rows = prepStmt.executeUpdate();
			prepStmt.close();
			
			return rows;			
		}
		public int deleteRecord( int employeeID) throws SQLException {
			// variable to hold rows affected
			int rows = 0;
			
			// build our delete statement
			String ourSQLDelete = "DELETE FROM employees " 
								+ "WHERE employeeID = ? " ;

			// prepare our statment
			PreparedStatement prepStmt = conn.prepareStatement(ourSQLDelete);
			
			// set our parms
			prepStmt.setInt(1, employeeID);
			
			// get rows from delete
			rows = prepStmt.executeUpdate();
			prepStmt.close();
			
			// return the number of rows affected
			return rows;
		}
		
		 public static ResultSet selectPositions() throws SQLException
		   {
			   	   
			   //Creates the SQL Statement
			   String ourSQLSelect = "SELECT * from permissions";  
		                     
			   // Create a Statement object.
			   PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);

			   ResultSet positionResults = prepStmt.executeQuery();
			  

			   return positionResults;
		   }
}
