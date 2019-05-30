import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class orderItemsManager {

	// create a named constant for the DB URL we use to access our db using JDBC
	public final String DB_URL = "jdbc:ucanaccess://PettitFinalProject.accdb" ;
	
	// field to hold a pointer to our db connection
	private static Connection conn;

	public orderItemsManager() throws SQLException {
		conn = DriverManager.getConnection(DB_URL);
	}
	

	// select recrods based on a search criteria entered by the user. 
	// this search/select will be parameterized based on values user enters
	
	// ADD POSITIONS/COMBO BOX
	public static ResultSet selectOrders(String strPrice, String strItemID, String strOrderID) throws SQLException {

		
		
		String queryAddOnItem = "";
		if (strItemID != "") {
			queryAddOnItem = " AND productID LIKE ? ";
		}
		String queryAddOnOrder = "";
		if (strOrderID != "") {
			queryAddOnOrder = " AND orderID LIKE ? ";
		}
	
		
		// create the SQL statement, note how we concatenate the strAddOn whether we need it or not
		// Not needed for strings since the like command finds all results when the search field blank
		
		
		String ourSQLSelect = "SELECT 	i.orderitemID as ID, "
							+ "			i.price AS Price, "
							+ "			p.productID AS Product, "
							+ "			o.orderID AS OrderID "
							+ "FROM orderItem i "
							+ " LEFT JOIN products p ON p.productID =  i.productID "		 
							+ " LEFT JOIN orders o ON o.orderID =  i.orderID "		
							+ "WHERE "
							+ " 		price LIKE 	 ? "
							+ queryAddOnItem    
							+ queryAddOnOrder; 
							
				
		// create our prepared statement
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// our prepared statement had placeholders (?) we need to fill in in order to execute our query
		prepStmt.setString(1, "%" + strPrice + "%" );
	
		
	
		
		int columnNumber = 2;
		
		if ( queryAddOnItem.length() > 0) {
			prepStmt.setString(columnNumber, "%" + strItemID + "%"  );
			columnNumber++ ;
		}
		
		
		if ( queryAddOnOrder.length() > 0) {
			prepStmt.setString(columnNumber, "%" + strOrderID + "%"  );
			columnNumber++ ;
		}

		
		ResultSet orderItemResults = prepStmt.executeQuery();
		return orderItemResults;
		
		
		
	}

	public static ResultSet selectUpdate( int ID) throws SQLException {
		// create sql statement to select a record based on the passed in intID
		
		String ourSQLSelect ="SELECT  i.orderitemID as ID, "
				+ "			i.price AS Price, "
				+ "			p.productID AS Product "
				+ "			o.orderID AS OrderID ,"
				+ " FROM orderitems i "
				+ " LEFT JOIN products p ON p.productID =  i.productID "			
				+ " LEFT JOIN orders o ON o.orderID =  i.orderID "
				+ " WHERE orderitemID = ?								  ";
		
		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);
		
		// insert the variables to our prepstmt
		prepStmt.setInt(1, ID );
		
		ResultSet orderItemResults = prepStmt.executeQuery();
		
		return orderItemResults;
	}

	
	public int insertRecord( String price,String productName, String ordID  ) throws SQLException {
		// 		this method allows us to insert a record into our ordertable in williamsdb
		
		int rows = 0;
		
  		int prodID 	= 0;
  		int orderID =0;

  		
  		if (! productName.isEmpty()) {
	  		String ourSQLMenu  = "SELECT productID FROM products where productName =  ?";
	  		PreparedStatement prepMenu = conn.prepareStatement(ourSQLMenu);
	  		prepMenu.setString(1, productName);
	  		ResultSet productResults = prepMenu.executeQuery();	  		

	  		while ( productResults.next()) {
	  			prodID = productResults.getInt("productID");
	  		}
	  		
  		}
  		
  		
  		if ( ! ordID.isEmpty()) {
	  		String ourSQLOrder  = "SELECT orderID FROM orders WHERE orderID = ?";
	  		PreparedStatement prepOrder = conn.prepareStatement(ourSQLOrder);
	  		prepOrder.setString(1, ordID);
	  		ResultSet orderResults = prepOrder.executeQuery();	  		

	  		while ( orderResults.next()) {
	  			orderID = orderResults.getInt("orderID");
	  		}
  		}

		
		String ourSQLInsert = "INSERT INTO orderItem "
							+ " 		(price,  productID , orderID ) "
							+ " VALUES 	(       ?,			?,       ?) " ;
		
		// create a prepared statement object
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLInsert);
	
		// pass parameters to our prepared statement prepStmt
		prepStmt.setString( 1, price);
		prepStmt.setInt(2, prodID);
		prepStmt.setInt(3, orderID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
	}

	public int updateRecord (String orderPrice,  String menuName,String orderName, int orderitemsID) throws SQLException {
		// 	rows = updater.updateRecord(date, employeeName, tableName, selectedRecordID);
				
		int rows = 0;
		
  		// Week 12
		// Created variable to hold value id of position from userpositions table

  		Integer menuID 	= 0;
  		Integer orderID = 0;

  		// if employee name passed get id
  		if (!menuName.isEmpty()) {
	  		String ourSQLMenu  = "SELECT menuid from menu where menuname =  ?";
	  		PreparedStatement prepMenu = conn.prepareStatement(ourSQLMenu);
	  		prepMenu.setString(1, menuName);
	  		ResultSet employeeResults = prepMenu.executeQuery();	  		

	  		while ( employeeResults.next()) {
	  			menuID = employeeResults.getInt("userid");
	  		}
	  		
  		}
  		
  		//if tablename passed get tableid
  		if (! orderName.isEmpty()) {
	  		String ourSQLorder  = "SELECT orderid from orders where orderid = ?";
	  		PreparedStatement preporder = conn.prepareStatement(ourSQLorder);
	  		preporder.setString(1, orderName);
	  		ResultSet orderResults = preporder.executeQuery();	  		

	  		while ( orderResults.next()) {
	  			orderID = orderResults.getInt("orderid");
	  		}
  		}

		String ourSQLUpdate = "UPDATE orderitems " 
							+ " SET "
							+ " 	orderprice 	=	?	, "
							+ " 	ordernotes 	=	?	, "
							+ "     menuname 	=  	?   , "
							+ "     orderid 	=  	?     "
							+ " WHERE orderitemID  	=	?	  ";

		
		// create a prepared statement 
		PreparedStatement prepStmt = conn.prepareStatement(ourSQLUpdate);
		
		// set our parms
		prepStmt.setString( 1, orderPrice);
		
		prepStmt.setInt(3, menuID);	
		prepStmt.setInt(4, orderID);
		prepStmt.setDouble(5, menuID);
		
		// execute the insert
		rows = prepStmt.executeUpdate();
		prepStmt.close();
		
		return rows;
		
		
	}
	
	
	public int deleteRecord( int userID) throws SQLException {
		// variable to hold rows affected
		int rows = 0;
		
		// build our delete statement
		String ourSQLDelete = "DELETE FROM orderItem " 
							+ "WHERE orderitemId = ? " ;

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
	
	public static ResultSet selectOrder() throws SQLException
	   {
		   	   
		   //Creates the SQL Statement
		   String ourSQLSelect = "SELECT * from orders";  
	                     
		   // Create a Statement object.
		   PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);

		   ResultSet orderResults = prepStmt.executeQuery();
		  

		   return orderResults;
	   }
	 public static ResultSet selectProducts() throws SQLException
	   {
		   	   
		   //Creates the SQL Statement
		   String ourSQLSelect = "SELECT * from products";  
	                     
		   // Create a Statement object.
		   PreparedStatement prepStmt = conn.prepareStatement(ourSQLSelect);

		   ResultSet productsResults = prepStmt.executeQuery();
		  

		   return productsResults;
	   }
}
