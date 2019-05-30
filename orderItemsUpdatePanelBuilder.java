import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class orderItemsUpdatePanelBuilder extends JPanel {
	private JTextField priceTextField; 		// name
	
	private JComboBox<String> orderComboField;   // orders
	private JComboBox<String> menuComboField;     // items

	public orderItemsUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel pricePrompt = new JLabel("Price");
		priceTextField = new JTextField(45);
		
		

		JLabel menuPrompt = new JLabel("Product");
		menuComboField = new JComboBox<String>();
	    menuComboField.addItem("");
	    
	    JLabel orderPrompt = new JLabel("Order ID");
	    orderComboField = new JComboBox<String>();
	    orderComboField.addItem("");

	    
	    try {
	    	orderItemsManager ord  = new orderItemsManager();
	    	orderItemsManager menu = new orderItemsManager();
		    
		    ResultSet orderInfo = orderItemsManager.selectOrder();
		    ResultSet menuInfo    = orderItemsManager.selectProducts();
		    
		    while (orderInfo.next()) {
		    	String ordid = orderInfo.getString("orderID");
		    	orderComboField.addItem(ordid);
		    }
		    while (menuInfo.next()) {
		    	String product = menuInfo.getString("productname");
		    	menuComboField.addItem(product);
		    }
		    
	      } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
		
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Order Item Information"));
		
		
		// add our labels and text boxes
		add(pricePrompt);
		add(priceTextField);
		
		
	    add(orderPrompt);
	    add(orderComboField);
	    
	    add(menuPrompt);
	    add(menuComboField);
		
	}

	public String getPrice() {
		return priceTextField.getText();
	}
	
	//Setters and Getters
	
	public String getOrder()
	{
		return orderComboField.getSelectedItem().toString();
	}
	public String getMenu()
	{
		return menuComboField.getSelectedItem().toString();
	}
	
	/* 
	 * The setPos sets the position to the selected id
	 */
	 
	
	public void setOrder(String strOrderID)
	{
		orderComboField.setSelectedItem(strOrderID);
	} 
	public void setMenu(String strMenuName)
	{
		menuComboField.setSelectedItem(strMenuName);
	} 
	
	
	public void setPrice(String price) {
		this.priceTextField.setText(price);
	}
	
	/*
    	The clear method sets each of the 
    	text fields to an empty string.
	*/
	
	public void clear() {
		priceTextField.setText("");
		orderComboField.setSelectedIndex(0);
		menuComboField.setSelectedIndex(0);
		
	}
	
	
}
