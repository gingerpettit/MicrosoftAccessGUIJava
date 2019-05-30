import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class orderTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://PettitFinalProject.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public orderTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectOrders(String strDate, String strEmp,String strTable) throws SQLException {

		
		
		String queryAddOnEmp = "";
		if (strEmp != "") {
			queryAddOnEmp = " AND employeeID LIKE ? ";
		}
		String queryAddOnTable = "";
		if (strTable != "") {
			queryAddOnTable = " AND table LIKE ? ";
		}
	
		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		
		String ourSQLSelect = "SELECT 	o.orderID as ID, "
							+ "			o.orderdate AS Date, "
							+ "			e.employeeID AS Employee ,"
							+ "			t.tableID AS Table "
							+ "FROM orders o "
							+ " LEFT JOIN employees e ON e.employeeID =  o.employeeID "		 
							+ " LEFT JOIN tables t ON t.tableID =  o.table "		
							+ "WHERE "
							+ " 		orderdate LIKE 	 ? "
							+ queryAddOnEmp    
							+ queryAddOnTable; 
							
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strDate + "%" );
	
		
		int columnNumber = 2;
		
		if ( queryAddOnEmp.length() > 0) {
			prepStmt.setString(columnNumber, "%" + strEmp + "%"  );
			columnNumber++ ;
		}
		
		
		if ( queryAddOnTable.length() > 0) {
			prepStmt.setString(columnNumber, "%" + strTable + "%"  );
			columnNumber++ ;
		}

		
		ResultSet orderResults = prepStmt.executeQuery();
		return orderResults;
		
		
		
	}

	public static ResultSet selectUpdate( int orderID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT o.orderID as ID			, "
				+ "						o.orderdate AS Date	, "
				+ "						e.employeeID AS Employee  , "
				+ "					t.tableID AS Table   "
				+ " FROM orders o "
				+ " LEFT JOIN employees e ON e.employeeID    =  o.employeeID "
				+ " LEFT JOIN tables t ON t.tableID =  o.table "
				+ " WHERE orderID = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, orderID );
		
		ResultSet orderResults = prepStmt.executeQuery();
		
		return orderResults;
	}

	
	public int insertRecord(String date,  String employeeName, String tableName ) throws SQLException {
		// 		this method allows us to insert a record into our ordertable in williamsdb
		
		int rows = 0;
		
  		Integer empID 	= 0;
  		Integer tableID = 0;

  		// if employee name passed get id
  		if (! employeeName.isEmpty()) {
	  		String ourSQLEmployee  = "SELECT employeeID FROM employees WHERE employeename =  ?";
	  		PreparedStatement prepEmployee = conn.prepareStatement(ourSQLEmployee);
	  		prepEmployee.setString(1, employeeName);
	  		ResultSet employeeResults = prepEmployee.executeQuery();	  		

	  		while ( employeeResults.next()) {
	  			empID = employeeResults.getInt("employeeID");
	  		}
  		}
  		
  		//if tablename passed get tableid
  		if (! tableName.isEmpty()) {
	  		String ourSQLTable  = "SELECT tableID FROM tables WHERE tableName = ?";
	  		PreparedStatement prepTable = conn.prepareStatement(ourSQLTable);
	  		prepTable.setString(1, tableName);
	  		ResultSet tableResults = prepTable.executeQuery();	  		

	  		while ( tableResults.next()) {
	  			tableID = tableResults.getInt("tableID");
	  		}
  		}

		
		String ourSQLInsert = "INSERT INTO orders "
							+ " 		(orderdate, employeeID, table ) "
							+ " VALUES 	(       ?,			 ?,			?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString( 1, date);
		prepStmt.setInt(2, empID);
		prepStmt.setInt(3, tableID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String orderDate, String employeeName,String tableName, int orderID) throws SQLException {
		// 	rows = updater.updateRecord(date, employeeName, tableName, selectedRecordID);
				
		int rows = 0;
		
  		// Week 12
		// Created variable to hold value id of position from userpositions table

  		Integer empID 	= 0;
  		Integer tableID = 0;

  		// if employee name passed get id
  		if (! employeeName.isEmpty()) {
	  		String ourSQLEmployee  = "SELECT employeeID from employees where employeename =  ?";
	  		PreparedStatement prepEmployee = conn.prepareStatement(ourSQLEmployee);
	  		prepEmployee.setString(1, employeeName);
	  		ResultSet employeeResults = prepEmployee.executeQuery();	  		

	  		while ( employeeResults.next()) {
	  			empID = employeeResults.getInt("employeeID");
	  		}
	  		
  		}
  		
  		//if tablename passed get tableid
  		if (! tableName.isEmpty()) {
	  		String ourSQLTable  = "SELECT tableID from tables where tablename = ?";
	  		PreparedStatement prepTable = conn.prepareStatement(ourSQLTable);
	  		prepTable.setString(1, tableName);
	  		ResultSet tableResults = prepTable.executeQuery();	  		

	  		while ( tableResults.next()) {
	  			tableID = tableResults.getInt("tableID");
	  		}
  		}

		String ourSQLUpdate = "UPDATE orders " 
							+ " SET "
							+ " 	orderdate 	=	?	, "
							+ "     employeeID 	=  	?   , "
							+ "     table 	=  	?     "
							+ " WHERE orderID  	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString( 1, orderDate);
		prepStmt.setInt(2, empID);	
		prepStmt.setInt(3, tableID);
		prepStmt.setDouble(4, orderID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord( int userID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM orders " 
							+ "WHERE orderId = ? " ;

		// prepare our statment
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLDelete);
		
		// set our parms
		prepStmt.setInt(1, userID);
		
		// get rows from delete
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		// return the number of rows affected
		return rows;
	}
	 public static ResultSet selectTables() throws SQLException
	   {
		   	   
		   //Creates the SQL Statement
		   String ourSQLSelect = "SELECT * from tables";  
	                     
		   // Create a Statement object.
		   PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);

		   ResultSet tableResults = prepStmt.executeQuery();
		  

		   return tableResults;
	   }
	 public static ResultSet selectEmployees() throws SQLException
	   {
		   	   
		   //Creates the SQL Statement
		   String ourSQLSelect = "SELECT * from employees";  
	                     
		   // Create a Statement object.
		   PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);

		   ResultSet employeeResults = prepStmt.executeQuery();
		  

		   return employeeResults;
	   }
}
