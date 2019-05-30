import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class tableUpdatePanelBuilder extends JPanel{
	private JTextField nameTextField; 		// name
	private JTextField phoneTextField;		// email


	public tableUpdatePanelBuilder() {
		// create labels and text fields for our user data
		JLabel namePrompt = new JLabel("Name");
		nameTextField = new JTextField(45);
		
		
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Table Information"));
		
		
		// add our labels and text boxes
		add(namePrompt);
		add(nameTextField);	
	}
	// create our getters.  
		// Note we are not interested in the text field objects, we want the actual text values they contain 
		public String getName() {
			return nameTextField.getText();
		}
		
		public void setName(String name) {
			this.nameTextField.setText(name);
		}
		// clear the form for a new search
		public void clear() {
			nameTextField.setText("");
		}

}
