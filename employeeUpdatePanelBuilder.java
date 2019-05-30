import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class employeeUpdatePanelBuilder extends JPanel{
	private JTextField nameTextField; 		// name
	private JTextField addressTextField;	// address
	private JTextField phoneTextField;		// email
	private JComboBox<String> posComboField;       // positions

	public employeeUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel namePrompt = new JLabel("Name");
		nameTextField = new JTextField(45);
		
		JLabel addressPrompt = new JLabel("Address");
		addressTextField = new JTextField(55);
		
		JLabel phonePrompt = new JLabel("Phone");
		phoneTextField = new JTextField(55);
		
	
		
		 JLabel positionPrompt = new JLabel("Position");
		    posComboField = new JComboBox<String>();
		    posComboField.addItem("");
		    try {
		    	employeeTableManager getPos = new employeeTableManager();
			    
			    ResultSet posInfo = employeeTableManager.selectPositions();
			    
			    while (posInfo.next()) {
			    	String position = posInfo.getString("permissionname");
			    	posComboField.addItem(position);
			    }
			    
		      } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      }
			
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Employee Information"));
		
		
		// add our labels and text boxes
		add(namePrompt);
		add(nameTextField);
		
		add(addressPrompt);
		add(addressTextField);
		
		add(phonePrompt);
		add(phoneTextField);
		
		add(positionPrompt);
	    add(posComboField);
		
	}
	// create our getters.  
		// Note we are not interested in the text field objects, we want the actual text values they contain 
		public String getName() {
			return nameTextField.getText();
		}


		public String getAddress() {
			return addressTextField.getText();
		}


		public String getPhone() {
			return phoneTextField.getText();
		}
		
		public String getPosition() {
			return posComboField.getSelectedItem().toString();
		}

		public void setName(String name) {
			this.nameTextField.setText(name);
		}

		public void setAddress(String address) {
			this.addressTextField.setText(address);
		}

		public void setPhone(String phone) {
			this.phoneTextField.setText(phone);
		}
		
		public void setPosition(String position) {
			this.posComboField.setSelectedItem(position);
		}
		

		// clear the form for a new search
		public void clear() {
			nameTextField.setText("");
			addressTextField.setText("");
			phoneTextField.setText("");
			posComboField.setSelectedIndex(0);
		}







}
