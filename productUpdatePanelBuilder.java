import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class productUpdatePanelBuilder extends JPanel{
	private JTextField nameTextField; 		// name
	private JTextField descTextField; 		// description
	private JTextField priceTextField;	// price
	private JTextField inventoryTextField;	//inventory


	public productUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel namePrompt = new JLabel("Name");
		nameTextField = new JTextField(45);
		
		JLabel pricePrompt = new JLabel("Price");
		priceTextField = new JTextField(55);
		
		JLabel descPrompt = new JLabel("Description");
		descTextField = new JTextField(100);
		
		JLabel inventoryPrompt = new JLabel("Inventory");
		inventoryTextField = new JTextField(10);
	   
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Product Information"));
		
		
		// add our labels and text boxes
		add(namePrompt);
		add(nameTextField);
		
		add(pricePrompt);
		add(priceTextField);
		
		add(descPrompt);
		add(descTextField);
		
		add(inventoryPrompt);
		add(inventoryTextField);
		
	}
	// create our getters.  
		// Note we are not interested in the text field objects, we want the actual text values they contain 
		public String getName() {
			return nameTextField.getText();
		}
		public String getDesc() {
			return descTextField.getText();
		}


		public Double getPrice() {
			if (priceTextField.getText().equals("")) {
				return null;
			} else {
				String getText = priceTextField.getText();
				return Double.parseDouble(getText);
			}
		}


		public Double getInventory() {
			if (inventoryTextField.getText().equals("")) {
				return null;
			} else {
				String getText = inventoryTextField.getText();
				return Double.parseDouble(getText);
			}
		}

		public void setName(String name) {
			this.nameTextField.setText(name);
		}

		public void setPrice(Double price) {
			this.priceTextField.setText(price.toString());
		}

		public void setDesc(String desc) {
			this.descTextField.setText(desc);
		}
		
		public void setInventory(Double inventory) {
			this.inventoryTextField.setText(inventory.toString());
		}
		

		// clear the form for a new search
		public void clear() {
			nameTextField.setText("");
			priceTextField.setText("");
			descTextField.setText("");
			inventoryTextField.setText("");
		}







}
