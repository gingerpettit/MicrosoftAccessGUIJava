import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class orderUpdatePanelBuilder extends JPanel{
	private JTextField tableTextField; 		// table
	private JTextField dateTextField;	// time
	private JComboBox<String> tableComboField;     // tables
	private JComboBox<String> employeeComboField;   // employees
	public orderUpdatePanelBuilder() {
		// create labels and text fields for our user data
		 JLabel tablePrompt = new JLabel("Table ");
		 tableComboField = new JComboBox<String>();
		 tableComboField.addItem("");
		
		JLabel datePrompt = new JLabel("Date");
		dateTextField = new JTextField(55);
		
		JLabel employeePrompt = new JLabel("Employee");
		employeeComboField = new JComboBox<String>();
	    employeeComboField.addItem("");
	    
		
		 try {
			 orderTableManager  users  = new orderTableManager();
		    	orderTableManager tableCombo = new orderTableManager();
			    
		    	ResultSet employeeInfo = orderTableManager.selectEmployees();
			    ResultSet tableInfo    = orderTableManager.selectTables();
			   
			    while (employeeInfo.next()) {
			    	String emp = employeeInfo.getString("employeename");
			    	employeeComboField.addItem(emp);
			    }
			    while (tableInfo.next()) {
			    	String table = tableInfo.getString("tablename");
			    	tableComboField.addItem(table);
			    }
			    
		      } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      }
		
		// create a grid layout manager with 12 rows and 1 column
		setLayout(new GridLayout(12,1));
		setBorder(BorderFactory.createTitledBorder("Enter Order Information"));
		
		
		// add our labels and text boxes
		add(tablePrompt);
		add(tableComboField);
		
		add(datePrompt);
		add(dateTextField);
		
		add(employeePrompt);
	    add(employeeComboField);
		
		
	}
	// create our getters.  
		// Note we are not interested in the text field objects, we want the actual text values they contain 
	public String getTable()
	{
		return tableComboField.getSelectedItem().toString();
	}

		public String getDate() {
			return dateTextField.getText();
		}
		
		public String getEmployee()
		{
			return employeeComboField.getSelectedItem().toString();
		}

		
	
		public void setTable(String strTableName)
		{
			tableComboField.setSelectedItem(strTableName);
		} 

		public void setDate(String date) {
			this.dateTextField.setText(date);
		}
		public void setEmployee(String strEmployeeName)
		{
			employeeComboField.setSelectedItem(strEmployeeName);
		} 
		

		// clear the form for a new search
		public void clear() {
			
			dateTextField.setText("");
			employeeComboField.setSelectedIndex(0);
			tableComboField.setSelectedIndex(0);
			
		}







}
