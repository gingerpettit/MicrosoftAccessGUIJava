import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class customerUpdatePanelBuilder extends JPanel{
	private JTextField nameTextField; 		// name
	private JTextField phoneTextField;		// email


	public customerUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel namePrompt = new JLabel("Name");
		nameTextField = new JTextField(45);
		
		JLabel phonePrompt = new JLabel("Phone");
		phoneTextField = new JTextField(55);
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter User Information"));
		
		
		// add our labels and text boxes
		add(namePrompt);
		add(nameTextField);
		add(phonePrompt);
		add(phoneTextField);
		
	}
	// create our getters.  
		// Note we are not interested in the text field objects, we want the actual text values they contain 
		public String getName() {
			return nameTextField.getText();
		}

		public String getPhone() {
			return phoneTextField.getText();
		}
		
		public void setName(String name) {
			this.nameTextField.setText(name);
		}

		public void setPhone(String phone) {
			this.phoneTextField.setText(phone);
		}

		// clear the form for a new search
		public void clear() {
			nameTextField.setText("");
			phoneTextField.setText("");
		}

}
