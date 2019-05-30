import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class productTableManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://PettitFinalProject.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public productTableManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	public static ResultSet selectProducts(String strName, String strDesc, Double dblPrice, Double dblInventory) throws SQLException {

		// since we have a number (dblHR) that cannot search correctly using the like commane, 
		// this variable will be used to hold the addon variable if necessary
		String strAddOn;
		String strAddOn2;
		
		if (dblPrice != null ) {
			strAddOn = " AND productprice = ? " ;
					
		} else {
			strAddOn = "";
		}
		
		if (dblInventory != null ) {
			strAddOn2 = " AND productinventory = ? " ;
					
		} else {
			strAddOn2 = "";
		}
		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		String ourSQLSelect = "SELECT 	productID as ID 			, "
							+ "			productname AS Name		, "
							+ "			productdescription as Description, "
							+ "			productprice as Price	, "
							+ " 		productinventory as Inventory "
							+ "FROM products "
							+ "WHERE "
							+ " 		productname LIKE 	 ? "
							+ " AND 	productdescription LIKE ?"
							+strAddOn
							+ strAddOn2 ;
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strName + "%" );
		prepStmt.setString(2, "%" + strDesc + "%" );
		// check to see if dblPrice (productprice) is not null, add it to the parameter list
		if (dblPrice != null) {
			prepStmt.setDouble(3, dblPrice);
		}

		// check to see if dbdHR (hourlyrate) is not null, add it to the parameter list
		if (dblInventory != null) {
			prepStmt.setDouble(4, dblInventory);
		}
		
		// execute the query
		ResultSet productResults = prepStmt.executeQuery();
		return productResults;
		
	}
	
	
	public int insertRecord(String name, String desc, Double price, Double inventory) throws SQLException {
		// 		this method allows us to insert a record into our usertable in testdb
		
		int rows = 0;
		
		String ourSQLInsert = "INSERT INTO products "
							+ " 		(productname, productdescription, productprice,  productinventory) "
							+ " VALUES 	(       ?,			 ?,			?,			?)" ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString( 1, name);
		prepStmt.setString( 2, desc);
		prepStmt.setDouble( 3, price);
		prepStmt.setDouble( 4, inventory);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}
	
	public static ResultSet selectUpdate( int ID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect = "SELECT productID as ID					, "
				+ "						productname AS name		, "
				+ "						productdescription AS description 	    , "
				+ "						productprice AS price	, "
				+ " 					productinventory AS inventory		  "	
				+ " FROM products 								  "
				+ " WHERE productID = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, ID );
		
		ResultSet productResults = prepStmt.executeQuery();
		
		return productResults;
	}
	
	public int updateRecord (String name, String description, double price, double inventory, int ID) throws SQLException {
		
		int rows = 0;
		
		String ourSQLUpdate = "UPDATE products " 
							+ " SET "
							+ " 	productname 	=	?	, "
							+ " 	productdescription	=	?	, "
							+ " 	productprice	=	?   , "
							+ " 	productinventory	=	?	  "
							+ " WHERE productID    	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString( 1, name);
		prepStmt.setString( 2, description);
		prepStmt.setDouble( 3, price);
		prepStmt.setDouble( 4, inventory);
		prepStmt.setInt( 	5, ID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord( int ID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM products " 
							+ "WHERE productID = ? " ;

		// prepare our statment
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLDelete);
		
		// set our parms
		prepStmt.setInt(1, ID);
		
		// get rows from delete
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		// return the number of rows affected
		return rows;
	}
}
