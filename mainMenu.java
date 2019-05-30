import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class mainMenu extends JFrame{
	
		// define class level fields/variables
		JPanel buttonPanel;  	// panel for our buttons

		
	public mainMenu(){
		// set the title of the form
		setTitle("Select Menu Option");
				
		// specify the action to take when the close button is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		// build the button panel
		buildButtonPanel();
				
		// create a border layout manager
		setLayout(new BorderLayout());
				
		// add the panels to the content pane
		add(buttonPanel, BorderLayout.NORTH);
				
		setSize(700,700);
				
		//set pack and set visible
		pack();
		setVisible(true);
	}
		
		
	public void buildButtonPanel(){
		// construct the button panel, add buttons and listeners
		buttonPanel = new JPanel();
		
		// create a search users button and add listener
		JButton manageEmployeesButton = new JButton("Manage Employees");
		manageEmployeesButton.addActionListener(new ManageEmployeesButtonListener());
				
		// create a search permissions button and add listener
		JButton managePermissionsButton = new JButton("Manage Permissions");
		managePermissionsButton.addActionListener(new ManagePermissionsButtonListener());
				
		// create a search permissions button and add listener
		JButton manageCustomersButton = new JButton("Manage Customers");
		manageCustomersButton.addActionListener(new ManageCustomersButtonListener());
		
		// create a search users button and add listener
		JButton manageProductsButton = new JButton("Manage Products");
		manageProductsButton.addActionListener(new ManageProductsButtonListener());
		
		// create a search users button and add listener
		JButton manageOrdersButton = new JButton("Manage Orders");
		manageOrdersButton.addActionListener(new ManageOrdersButtonListener());

		
		
		// create a search users button and add listener
		JButton manageTableButton = new JButton("Manage Tables");
		manageTableButton.addActionListener(new ManageTableButtonListener());

		// set the layout and add the buttons
		buttonPanel.setLayout(new GridLayout(4,1));
		// add the buttons
		buttonPanel.add(manageEmployeesButton);
		buttonPanel.add(managePermissionsButton);
		buttonPanel.add(manageCustomersButton);
		buttonPanel.add(manageProductsButton);
		buttonPanel.add(manageOrdersButton);
		buttonPanel.add(manageTableButton);
	}
	public class ManageEmployeesButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				employeeUpdateForm ds = new employeeUpdateForm();
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	public class ManagePermissionsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				permissionsUpdateForm perm = new permissionsUpdateForm();
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	public class ManageCustomersButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				customerUpdateForm cs = new customerUpdateForm();
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	public class ManageProductsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				productUpdateForm cs = new productUpdateForm();
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	public class ManageOrdersButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				orderUpdateForm cs = new orderUpdateForm();
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	public class ManageTableButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			try {
				tableUpdateForm ts = new tableUpdateForm();
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	
	public static void main(String[] args) {
		mainMenu mm = new mainMenu();

	}

}
