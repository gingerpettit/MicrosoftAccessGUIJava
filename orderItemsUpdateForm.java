import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import net.proteanit.sql.DbUtils;


public class orderItemsUpdateForm extends JFrame {
	// GRID FORMATTING
	private final int GRIDWIDTH 	= 1000;		// size our grid 
	private final int GRIDHEIGHT	= 300;


	orderItemsUpdatePanelBuilder orderItemsInfoPanel;		// panel for user information
	JPanel buttonPanel;				// panel for buttons
	JPanel listPanel;				// panel for our list
	String searchString = "";		 
	JScrollPane scrollPane ;		// a scroll pane to hold our list
	JTable ourTable;				// holds the result set
	int selectedRecordID = 0;		// holds our record id from the list
	
	public orderItemsUpdateForm() throws SQLException {
		// set the window title
		setTitle("Update items");
		// set our action when the user close
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// create a panel builder object
		orderItemsInfoPanel = new orderItemsUpdatePanelBuilder();
		
		// build the button panel object
		buildButtonPanel();
		
		// build the list panel object
		buildListPanel();
		
		// create a BorderManager layout
		setLayout(new BorderLayout());
		
		// add the panels to the content pane
		add(orderItemsInfoPanel, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);
		add(buttonPanel,BorderLayout.SOUTH);

		// pack and display the window
		pack();
		setVisible(true);
	
	}
	
	
	private void buildListPanel() throws SQLException {
		// Create the panel
		listPanel = new JPanel();
		
		// add a titled border to the panel
		listPanel.setBorder(BorderFactory.createTitledBorder("Order Information"));
		
		// create an object to access the db
		orderItemsManager um = new orderItemsManager();
		
		// create a resultset to hold the blank search for when first startup
		ResultSet userInfo = um.selectOrders("", "","");
		
		// user rs2xml to display our resultset
		ourTable = new JTable(DbUtils.resultSetToTableModel(userInfo));
		
		
		// scroll pane so we scroll
		scrollPane = new JScrollPane(ourTable);
		listPanel.add(scrollPane);
		
		// add a listener that will update our selectedRecordID with the currently selected record
		ourTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
				
				public void valueChanged(ListSelectionEvent event) {
					// check to make sure table is begin reloaded
					if (ourTable.getSelectedRow() >= 0) {
						
						
						try {
							// get the id of the row
							selectedRecordID = (int) ourTable.getValueAt(ourTable.getSelectedRow(),0);
							ResultSet updateInfo = orderItemsManager.selectUpdate(selectedRecordID);

							while(updateInfo.next()) {
								orderItemsInfoPanel.setPrice(updateInfo.getString("orderPrice"));
								
								// COMBO BOX
								orderItemsInfoPanel.setMenu(updateInfo.getString("MenuName"));
								orderItemsInfoPanel.setOrder(updateInfo.getString("OrderID"));
								

							}
						
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			});
		
	}

	
	
	
	private void buildButtonPanel() {
		// create a panel for the buttons
		buttonPanel = new JPanel();
		
		// create a Search button and add an action listener
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new SearchButtonListener());
				
		// create a update button and add an action listener
		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(new InsertButtonListener());

		
		// create a update button and add an action listener
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new UpdateButtonListener());
		
		// create a delete button and add an action listener
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteButtonListener());

		
		// create a clear button and add an action listener
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ClearButtonListener());

		// create an exit button to exit the application
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ExitButtonListener());
		
		// add buttons to the panel
		buttonPanel.add(searchButton);
		buttonPanel.add(insertButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);
		
		// set a default button
		getRootPane().setDefaultButton(searchButton);
		
	}
	
	private void resetForm() throws SQLException {
		
		// clear all the input boxes
		orderItemsInfoPanel.clear();
		
		// refresh the gird
		ResultSet searchInfo = null;
		
		// create an object to instantiate orderManager class
		orderItemsManager um = new orderItemsManager();
		
		// call our selectUsers with no filters
		searchInfo = orderItemsManager.selectOrders("", "","");

		// reset the table to new search criteria
		ourTable.setModel(DbUtils.resultSetToTableModel(searchInfo));
		
		// reinitialize our selected record
		selectedRecordID = 0;

	}

	// Handle search, filter button
		public class SearchButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e1) {
				// Get the user information from the text fields
				String strprice = orderItemsInfoPanel.getPrice();
				
				String MenuName  = orderItemsInfoPanel.getMenu();
				String OrderName     = orderItemsInfoPanel.getOrder();
				
				// create a ResultSet variable to hold our search info
				ResultSet searchInfo = null;
				
				try {
					// create an object to instantiate the Connection to the table	
					orderItemsManager getInfo = new orderItemsManager();
					// create a blank search of the table
					searchInfo = getInfo.selectOrders(strprice, MenuName, OrderName );

					// reset the table to new search criteria
					ourTable.setModel(DbUtils.resultSetToTableModel(searchInfo));

					
					
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}

		}


	public class InsertButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String price= orderItemsInfoPanel.getPrice();
			
			String menuName = orderItemsInfoPanel.getMenu();
			String orderName = orderItemsInfoPanel.getOrder();
			
			if (orderName.equals("")){
				JOptionPane.showMessageDialog(null, "Cannot insert a record with no ID.");
				return;
			}
			
			
			try {

				int rows = 0;
				
				// instantiate our orderManager
				orderItemsManager itemsManager = new orderItemsManager();
				
				// insert the record
				rows = itemsManager.insertRecord(price, menuName , orderName );
				
				if (rows > 0) {
					// let the user know the record was added
					JOptionPane.showMessageDialog(null,"Record successfully added!");

					// rest the form
					resetForm();
					
					
				}
				
				else {
					JOptionPane.showMessageDialog(null,"Record insert failed!");
				}
					
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	
	}

	public class UpdateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e1) {
			if (selectedRecordID < 1) {
				JOptionPane.showMessageDialog(null,"Please select a record to update.");
				return;
			}
			
			// Get the user information from the text fields
			String price = orderItemsInfoPanel.getPrice();
		
			String menu = orderItemsInfoPanel.getMenu();
			String order   = orderItemsInfoPanel.getOrder();
			
			int rows = 0;
			orderItemsManager updater;
			
			
			try {
				// create an object to instantiate the Connection to the table	
				updater = new orderItemsManager();
				rows = updater.updateRecord(price,  menu, order, selectedRecordID);
				
				if (rows > 0) {
					JOptionPane.showMessageDialog(null, "Record updated successfully");
					
					// clear all the input boxes
					orderItemsInfoPanel.clear();
					
					// refresh the gird
					
					ResultSet searchInfo = null;
					
					// create an object to0 instantiate userTableManager class
					orderItemsManager getInfo = new orderItemsManager();
					
					// call our selectUsers with no filters
					searchInfo = getInfo.selectOrders("", "","");

					// reset the table to new search criteria
					ourTable.setModel(DbUtils.resultSetToTableModel(searchInfo));

				
					// reinitialize our selected record
					selectedRecordID = 0;

			

					
				} else {
					JOptionPane.showMessageDialog(null, "Record Update Failed");
				}
				
				
				
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

	}

	public class ClearButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e1) {
			
			try {
				// reset the form
				resetForm();
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

	}

	public class DeleteButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e1) {
		
			// Verify we have a record selected
			if (selectedRecordID < 1) {
				JOptionPane.showMessageDialog(null,"Please select a record to delete");
				return;
			}
				
				// verify they want to delete
			if( JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record, "
					+ "and all connected records?\nThis action cannot be undone.", "Confirm Delete", 
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {  
					return;
			}
			
			// initialize variable to hold records affected
			int rows = 0;
			orderItemsManager Deleter;
			
			
			try {
				// create an object to instantiate the Connection to the table	
				Deleter = new  orderItemsManager();
				rows = Deleter.deleteRecord(selectedRecordID);
				
				if (rows > 0) {
					//reset the form
					resetForm();

					// inform user
					JOptionPane.showMessageDialog(null, "Record Deleted successfully");
				} else {
					JOptionPane.showMessageDialog(null, "Record Delete Failed");
				}
				
				
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

	}

	// exit the form
	public class ExitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// exit the app
			dispose();

		}

	}

	// test driver for form
	public static void main(String[] arg0) throws SQLException {
		orderItemsUpdateForm dbgs = new orderItemsUpdateForm();
	}
}
