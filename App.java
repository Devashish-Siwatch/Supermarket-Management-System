import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class App {
	private int size = 16;
    private JFrame frame;
    private JPanel panel, parent;
    private JPanel mainPanel;
    private JPanel[] screens;
    //private JTable[][] tblData;
    private CardLayout cardLayout;
    private JButton[] buttons;
    private String[] buttonNames = { "Add New Product", "Add New Supplier", "Add New Customer",
            "Add New Employee",
            "Add New Sale", "Add New Purchase",
            "Update Product Quantity",
            "Update Supplier Contact Info", "Update Employee Job Position",
            "Delete Sale", "Inventory Report", "Show All Suppliers", "Show All Customers", "Employee Report",
            "Sales Report", "Show All Purchases" };
    // JDBC driver name and database URL
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:3306/supermarket";

    // Database credentials
    final String USER = "root";
    final String PASS = "ktbits@2020";

    public App() {
        parent = new JPanel(new CardLayout());
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        frame = new JFrame("SuperMarket Management System");
        panel = new JPanel(new GridLayout(4, 4, 10, 10)); 
        buttons = new JButton[size];
        screens = new JPanel[size];


        // Create buttons and add them to the panel
        for (int i = 0; i < buttonNames.length; i++) {
            buttons[i] = new JButton(buttonNames[i]);
            buttons[i].setFont(new Font("Helvetica", Font.PLAIN, 16)); // Set font for buttons
            buttons[i].setBackground(new Color(66, 139, 202)); // Set background color for buttons
            buttons[i].setForeground(Color.WHITE); // Set text color for buttons
            buttons[i].setBorderPainted(false); // Remove button border
            buttons[i].setFocusPainted(false); // Remove button focus border
            buttons[i].setOpaque(true); // Enable button to be painted with custom background color
            buttons[i].addActionListener(new ButtonClickListener()); // Add ActionListener to handle button clicks
            panel.add(buttons[i]);
        }

        // Add the panel to the frame
        parent.add(panel, "Home Screen");
        // Create panels for each screen
        addNewProduct();
        addNewSupplier();
        addNewCustomer();
        addNewEmployee();
        addNewSale();
        addNewPurchase();
        updateProductQuantity();
        updateSupplierContactInfo();
        updateEmployeeJobPosition();
        deleteSaleScreen();
        showAllProducts();
        showAllSuppliers();
        showAllCustomers();
        showAllEmployees();
        showAllSales();
        showAllPurchases();
        frame.getContentPane().add(parent);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 800); // Set frame size
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    // ActionListener to handle button clicks
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource(); // Get the button that was clicked
            String buttonText = sourceButton.getText(); // Get the text of the button
            // Open a new screen based on the button text
            // JOptionPane.showMessageDialog(frame, "Navigating to " + buttonText + "
            // screen.");
            showAllProducts();
            showAllSuppliers();
            showAllCustomers();
            showAllEmployees();
            showAllSales();
            showAllPurchases();
            CardLayout cl = (CardLayout) (parent.getLayout());
            cl.show(parent, buttonText);
        }
    }

    private void addNewProduct() {
        // Add New Product Screen
        screens[0] = new JPanel(new FlowLayout());
        JTextField productName = new JTextField(10);
        JTextField description = new JTextField(10);
        JTextField price = new JTextField(10);
        JTextField quantity = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[0].add(new JLabel("Product Name:"));
        screens[0].add(productName);
        screens[0].add(new JLabel("Description:"));
        screens[0].add(description);
        screens[0].add(new JLabel("Price:"));
        screens[0].add(price);
        screens[0].add(new JLabel("Quantity:"));
        screens[0].add(quantity);
        screens[0].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                    		 Class.forName(JDBC_DRIVER);
							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
							System.out.println("Connected With the database successfully");
							PreparedStatement preparedStatement=connection.prepareStatement("insert into products (product_name,description,price,quantity) values(?,?,?,?)");
							preparedStatement.setString(1,productName.getText());
							preparedStatement.setString(2,description.getText());
							preparedStatement.setString(3,price.getText());
							preparedStatement.setString(4, quantity.getText());
							preparedStatement.executeUpdate();
							productName.setText("");
							description.setText("");
							price.setText("");
							quantity.setText("");
							System.out.println("Data stored successfully");
							JOptionPane.showMessageDialog(frame, "Added new product");
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frame, e1.getMessage());
						}//Establishing connection
                        
                        CardLayout cl = (CardLayout) (parent.getLayout());
                        cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[0], "Add New Product");

    }
    
    private void addNewSupplier() {
        // Add New Supplier Screen
        screens[1] = new JPanel(new FlowLayout());
        JTextField supplierAddress = new JTextField(10);
        JTextField supplierPhone = new JTextField(10);
        JTextField supplierName = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel

        screens[1].add(new JLabel("Supplier Name:"));
        screens[1].add(supplierName);
        screens[1].add(new JLabel("Supplier Address:"));
        screens[1].add(supplierAddress);
        screens[1].add(new JLabel("Supplier Phone:"));
        screens[1].add(supplierPhone);

        screens[1].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                    		 Class.forName(JDBC_DRIVER);
 							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
 							System.out.println("Connected With the database successfully");
 							PreparedStatement preparedStatement=connection.prepareStatement("insert into suppliers (supplier_name,address,contact_info) values(?,?,?)");
 							preparedStatement.setString(1,supplierName.getText());
 							preparedStatement.setString(2,supplierAddress.getText());
 							preparedStatement.setString(3,supplierPhone.getText());
 							preparedStatement.executeUpdate();
 							supplierName.setText("");
 							supplierAddress.setText("");
 							supplierPhone.setText("");
 							System.out.println("Data stored successfully");
 							JOptionPane.showMessageDialog(frame, "Added new supplier");
 						} catch (Exception e1) {
 							// TODO Auto-generated catch block
 							e1.printStackTrace();
 							JOptionPane.showMessageDialog(frame, e1.getMessage());
 						}//Establishing connection
                        
                        CardLayout cl = (CardLayout) (parent.getLayout());
                        cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[1], "Add New Supplier");

    }

    private void addNewCustomer() {
        // Add New Customer Screen
        screens[2] = new JPanel(new FlowLayout());
        JTextField customerName = new JTextField(10);
        JTextField customerAddress = new JTextField(10);
        JTextField customerPhone = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[2].add(new JLabel("Customer Name:"));
        screens[2].add(customerName);
        screens[2].add(new JLabel("Customer Address:"));
        screens[2].add(customerAddress);
        screens[2].add(new JLabel("Customer Phone:"));
        screens[2].add(customerPhone);
        screens[2].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                   		 Class.forName(JDBC_DRIVER);
							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
							System.out.println("Connected With the database successfully");
							PreparedStatement preparedStatement=connection.prepareStatement("insert into customers (customer_name,address,contact_info) values(?,?,?)");
							preparedStatement.setString(1,customerName.getText());
							preparedStatement.setString(2,customerAddress.getText());
							preparedStatement.setString(3,customerPhone.getText());
							preparedStatement.executeUpdate();
							customerName.setText("");
							customerAddress.setText("");
							customerPhone.setText("");
							System.out.println("Data stored successfully");
							JOptionPane.showMessageDialog(frame, "Added new customer");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frame, e1.getMessage());
						}//Establishing connection
                   
                        
                        CardLayout cl = (CardLayout) (parent.getLayout());
                        cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[2], "Add New Customer");

    }

    private void addNewEmployee() {
        // Add New Employee Screen
        screens[3] = new JPanel(new FlowLayout());
        JTextField employeeName = new JTextField(10);
        JTextField employeeAddress = new JTextField(10);
        JTextField employeePhone = new JTextField(10);
        JTextField jobPosition = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[3].add(new JLabel("Employee Name:"));
        screens[3].add(employeeName);
        screens[3].add(new JLabel("Employee Address:"));
        screens[3].add(employeeAddress);
        screens[3].add(new JLabel("Employee Phone:"));
        screens[3].add(employeePhone);
        screens[3].add(new JLabel("Job Position:"));
        screens[3].add(jobPosition);
        screens[3].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                      		 Class.forName(JDBC_DRIVER);
   							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
   							System.out.println("Connected With the database successfully");
   							PreparedStatement preparedStatement=connection.prepareStatement("insert into employees (employee_name,address,contact_info,job_po) values(?,?,?)");
   							preparedStatement.setString(1,employeeName.getText());
   							preparedStatement.setString(2,employeeAddress.getText());
   							preparedStatement.setString(3,employeePhone.getText());
   							preparedStatement.setString(4,jobPosition.getText());
   							preparedStatement.executeUpdate();
   							employeeName.setText("");
   							employeeAddress.setText("");
   							employeePhone.setText("");
   							jobPosition.setText("");
   							System.out.println("Data stored successfully");
   							JOptionPane.showMessageDialog(frame, "Added new employee");
   						} catch (Exception e1) {
   							// TODO Auto-generated catch block
   							e1.printStackTrace();
   							JOptionPane.showMessageDialog(frame, e1.getMessage());
   						}//Establishing connection
              
                        CardLayout cl = (CardLayout) (parent.getLayout());
                        cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[3], "Add New Employee");

    }

    private void addNewSale() {
        // Add New Sale Screen
        screens[4] = new JPanel(new FlowLayout());
        JTextField saleDate = new JTextField(10);
        JTextField quantity = new JTextField(10);
        JTextField customerId = new JTextField(10);
        JTextField productId = new JTextField(10);
        JTextField employeeId = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[4].add(new JLabel("Sale Date:"));
        screens[4].add(saleDate);
        screens[4].add(new JLabel("Quantity:"));
        screens[4].add(quantity);
        screens[4].add(new JLabel("Employee Id:"));
        screens[4].add(employeeId);
        screens[4].add(new JLabel("Customer ID:"));
        screens[4].add(customerId);
        screens[4].add(new JLabel("Product ID:"));
        screens[4].add(productId);
        screens[4].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                     		Class.forName(JDBC_DRIVER);
  							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
  							System.out.println("Connected With the database successfully");
  							PreparedStatement preparedStatement=connection.prepareStatement("select * from Products where product_id=?");
  							preparedStatement.setInt(1,Integer.parseInt(productId.getText()));
  							//System.out.println("HI");
  							ResultSet rs = preparedStatement.executeQuery();
  							rs.next();
  							int quan=rs.getInt("quantity");
  							if(quan>=Integer.parseInt(quantity.getText()))
  							{
  							preparedStatement=connection.prepareStatement("insert into sales (date_of_sale,customer_id,employee_id,product_id,quantity) values(?,?,?,?,?)");
  							preparedStatement.setString(1,saleDate.getText());
  							preparedStatement.setString(2,customerId.getText());
  							preparedStatement.setString(3,employeeId.getText());
  							preparedStatement.setString(4,productId.getText());
  							preparedStatement.setString(5,quantity.getText());
  							preparedStatement.executeUpdate();
  							}
  							preparedStatement=connection.prepareStatement("Update Products set quantity=? where product_id=?");
  							preparedStatement.setInt(2,Integer.parseInt(productId.getText()));
  							preparedStatement.setInt(1,quan-Integer.parseInt(quantity.getText()));
  							preparedStatement.executeUpdate();
  							saleDate.setText("");
  							customerId.setText("");
  							employeeId.setText("");
  							productId.setText("");
  							quantity.setText("");
  							System.out.println("Data stored successfully");
  							JOptionPane.showMessageDialog(frame, "Added new sale");
  						} catch (Exception e1) {
  							// TODO Auto-generated catch block
  							e1.printStackTrace();
  							JOptionPane.showMessageDialog(frame, e1.getMessage());
  						}//Establishing connection
                        
                        CardLayout cl = (CardLayout) (parent.getLayout());
                        cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[4], "Add New Sale");

    }

    private void addNewPurchase() {
        // Add New Purchase Screen
        screens[5] = new JPanel(new FlowLayout());
        JTextField purchaseDate = new JTextField(10);
        JTextField supplierId = new JTextField(10);
        JTextField productId = new JTextField(10);
        JTextField quantity = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[5].add(new JLabel("Purchase Date:"));
        screens[5].add(purchaseDate);
        screens[5].add(new JLabel("Supplier ID:"));
        screens[5].add(supplierId);
        screens[5].add(new JLabel("Product ID:"));
        screens[5].add(productId);
        screens[5].add(new JLabel("Quantity:"));
        screens[5].add(quantity);
        screens[5].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override                
                    public void actionPerformed(ActionEvent e) {
                    	try {
                    		 Class.forName(JDBC_DRIVER);
 							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
 							System.out.println("Connected With the database successfully");
 							PreparedStatement preparedStatement=connection.prepareStatement("select * from Products where product_id=?");
  							preparedStatement.setInt(1,Integer.parseInt(productId.getText()));
  							//System.out.println("HI");
  							ResultSet rs = preparedStatement.executeQuery();
  							rs.next();
  							int quan=rs.getInt("quantity");
 							preparedStatement=connection.prepareStatement("insert into purchases (date_of_purchase,supplier_id,product_id,quantity) values(?,?,?,?)");
 							preparedStatement.setString(1,purchaseDate.getText());
 							preparedStatement.setString(2,supplierId.getText());
 							preparedStatement.setString(3,productId.getText());
 							preparedStatement.setString(4,quantity.getText());
 							preparedStatement.executeUpdate();
 							preparedStatement=connection.prepareStatement("Update Products set quantity=? where product_id=?");
  							preparedStatement.setInt(2,Integer.parseInt(productId.getText()));
  							preparedStatement.setInt(1,quan+Integer.parseInt(quantity.getText()));
  							preparedStatement.executeUpdate();
  							purchaseDate.setText("");
  							supplierId.setText("");
  							productId.setText("");
  							quantity.setText("");
 							System.out.println("Data stored successfully");
 							JOptionPane.showMessageDialog(frame, "Added new purchase");
 						} catch (Exception e1) {
 							// TODO Auto-generated catch block
 							e1.printStackTrace();
 							JOptionPane.showMessageDialog(frame, e1.getMessage());
 						}//Establishing connection
                        
                        CardLayout cl = (CardLayout) (parent.getLayout());
                        cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[5], "Add New Purchase");

    }

    private void updateProductQuantity() {
        // Update Product Quantity Screen
        screens[6] = new JPanel(new FlowLayout());
        JTextField productId = new JTextField(10);
        JTextField quantity = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[6].add(new JLabel("Product ID:"));
        screens[6].add(productId);
        screens[6].add(new JLabel("Quantity:"));
        screens[6].add(quantity);
        screens[6].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                   		 Class.forName(JDBC_DRIVER);
							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
							System.out.println("Connected With the database successfully");
							PreparedStatement preparedStatement=connection.prepareStatement("Update Products set quantity=? where product_id=?");
 							preparedStatement.setInt(2,Integer.parseInt(productId.getText()));
 							preparedStatement.setInt(1,Integer.parseInt(quantity.getText()));
 							preparedStatement.executeUpdate();
 							productId.setText("");
 							quantity.setText("");
							System.out.println("Data stored successfully");
							JOptionPane.showMessageDialog(frame, "Product Updated");
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(frame, e1.getMessage());
						}//Establishing connection
                        CardLayout cl = (CardLayout) (parent.getLayout());
                        cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[6], "Update Product Quantity");

    }

    private void updateSupplierContactInfo() {
        // Update Supplier Contact Info Screen
        screens[7] = new JPanel(new FlowLayout());
        JTextField supplierId = new JTextField(10);
        JTextField supplierPhone = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[7].add(new JLabel("Supplier ID:"));
        screens[7].add(supplierId);
        screens[7].add(new JLabel("Supplier Phone:"));
        screens[7].add(supplierPhone);
        screens[7].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                      		 Class.forName(JDBC_DRIVER);
   							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
   							System.out.println("Connected With the database successfully");
   							PreparedStatement preparedStatement=connection.prepareStatement("Update Suppliers set contact_info=? where supplier_id=?");
    							preparedStatement.setInt(2,Integer.parseInt(supplierId.getText()));
    							preparedStatement.setString(1,supplierPhone.getText());
    							preparedStatement.executeUpdate();
    							supplierId.setText("");
    							supplierPhone.setText("");
   							System.out.println("Data stored successfully");
   							JOptionPane.showMessageDialog(frame, "Supplier Contact Info Updated");
   						} catch (Exception e1) {
   							// TODO Auto-generated catch block
   							e1.printStackTrace();
   							JOptionPane.showMessageDialog(frame, e1.getMessage());
   						}//Establishing connection
                           CardLayout cl = (CardLayout) (parent.getLayout());
                           cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[7], "Update Supplier Contact Info");

    }

    private void updateEmployeeJobPosition() {
        // Update Employee Job Position Screen
        screens[8] = new JPanel(new FlowLayout());
        JTextField employeeId = new JTextField(10);
        JTextField employeeJobPosition = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[8].add(new JLabel("Employee ID:"));
        screens[8].add(employeeId);
        screens[8].add(new JLabel("Employee Job Position:"));
        screens[8].add(employeeJobPosition);
        screens[8].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                     		 Class.forName(JDBC_DRIVER);
  							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
  							System.out.println("Connected With the database successfully");
  							PreparedStatement preparedStatement=connection.prepareStatement("Update Employees set job_position=? where employee_id=?");
   							preparedStatement.setInt(2,Integer.parseInt(employeeId.getText()));
   							preparedStatement.setString(1,employeeJobPosition.getText());
   							preparedStatement.executeUpdate();
   							employeeId.setText("");
   							employeeJobPosition.setText("");
  							System.out.println("Data stored successfully");
  							JOptionPane.showMessageDialog(frame, "Employee Job Position Updated");
  						} catch (Exception e1) {
  							// TODO Auto-generated catch block
  							e1.printStackTrace();
  							JOptionPane.showMessageDialog(frame, e1.getMessage());
  						}//Establishing connection
                          CardLayout cl = (CardLayout) (parent.getLayout());
                          cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[8], "Update Employee Job Position");
    }

    private void deleteSaleScreen() {
        // Delete Sale Screen
        screens[9] = new JPanel(new FlowLayout());
        JTextField saleIdTextField = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Add text field and submit button to the panel
        screens[9].add(new JLabel("Sale ID:"));
        screens[9].add(saleIdTextField);
        screens[9].add(submitButton);
        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	try {
                    		 Class.forName(JDBC_DRIVER);
 							Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
 							System.out.println("Connected With the database successfully");
 							PreparedStatement preparedStatement=connection.prepareStatement("select * from Sales where sales_id=?");
  							preparedStatement.setInt(1,Integer.parseInt(saleIdTextField.getText()));
  							//System.out.println("HI");
  							ResultSet rs = preparedStatement.executeQuery();
  							rs.next();
  							int quan=rs.getInt("quantity");
  							int pid=rs.getInt("product_id");
 							preparedStatement=connection.prepareStatement("Delete from sales where sales_id=?");
  							preparedStatement.setInt(1,Integer.parseInt(saleIdTextField.getText()));
  							preparedStatement.executeUpdate();
  							preparedStatement=connection.prepareStatement("select * from Products where product_id=?");
  							preparedStatement.setInt(1,pid);
  							//System.out.println("HI");
  							rs = preparedStatement.executeQuery();
  							rs.next();
  							int quan1=rs.getInt("quantity");
  							preparedStatement=connection.prepareStatement("Update Products set quantity=? where product_id=?");
 							preparedStatement.setInt(2,pid);
 							preparedStatement.setInt(1,quan+quan1);
 							preparedStatement.executeUpdate();
 							saleIdTextField.setText("");
 							System.out.println("Data stored successfully");
 							JOptionPane.showMessageDialog(frame, "Sale Deleted");
 						} catch (Exception e1) {
 							// TODO Auto-generated catch block
 							e1.printStackTrace();
 							JOptionPane.showMessageDialog(frame, e1.getMessage());
 						}//Establishing connection
                         CardLayout cl = (CardLayout) (parent.getLayout());
                         cl.show(parent, "Home Screen");
                    }
                });

        parent.add(screens[9], "Delete Sale");

    }
    private void showAllProducts() {
        screens[10] = new JPanel(new GridLayout(2,1));
        ArrayList<String[]> arr=new ArrayList<String[]>();
        try {
    		 Class.forName(JDBC_DRIVER);
				Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
				//System.out.println("Connected With the database successfully");
				PreparedStatement preparedStatement=connection.prepareStatement("Select * from Products");
				ResultSet rs = preparedStatement.executeQuery();
//				ResultSetMetaData rsmd = rs.getMetaData();
//				JTable tblData[];
//				DefaultTableModel model = (DefaultTableModel) tblData.getModel();
				while(rs.next())
				{
					String arr1[];
					arr1=new String[5];
					arr1[0]=(rs.getString("product_name"));
					arr1[1]=(rs.getString("product_id"));
					arr1[2]=(rs.getString("description"));
					arr1[3]=(rs.getString("price"));
					arr1[4]=(rs.getString("quantity"));
					arr.add(arr1);
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, e1.getMessage());
			}//Establishing connection
        String[][] data = new String[arr.size()][];
        String[] row;
        for(int i =0;i<arr.size();i++) {
        	row = arr.get(i);
        	data[i] = row;
        }
        String column[] = { "NAME", "ID", "DESCRIPTION","PRICE","QUANTITY" };
        JTable jt = new JTable(data, column);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        screens[10].add(sp);
        JButton back = new JButton("Back to Home");
        back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	 CardLayout cl = (CardLayout) (parent.getLayout());
                         cl.show(parent, "Home Screen");
                    }});
        back.setSize(40, 40);
        JPanel bpane = new JPanel(new FlowLayout());
        bpane.add(back);
        screens[10].add(bpane);
        parent.add(screens[10], "Inventory Report");
    }

    private void showAllSuppliers() {
        screens[11] = new JPanel(new GridLayout(2,1));
        ArrayList<String[]> arr=new ArrayList<String[]>();
        try {
    		 Class.forName(JDBC_DRIVER);
				Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
				//System.out.println("Connected With the database successfully");
				PreparedStatement preparedStatement=connection.prepareStatement("Select * from Suppliers");
				ResultSet rs = preparedStatement.executeQuery();
//				ResultSetMetaData rsmd = rs.getMetaData();
//				JTable tblData[];
//				DefaultTableModel model = (DefaultTableModel) tblData.getModel();
				while(rs.next())
				{
					String arr1[];
					arr1=new String[4];
					arr1[0]=(rs.getString("supplier_name"));
					arr1[1]=(rs.getString("supplier_id"));
					arr1[2]=(rs.getString("address"));
					arr1[3]=(rs.getString("contact_info"));
					arr.add(arr1);
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, e1.getMessage());
			}//Establishing connection
        String[][] data = new String[arr.size()][];
        String[] row;
        for(int i =0;i<arr.size();i++) {
        	row = arr.get(i);
        	data[i] = row;
        }
        String column[] = { "NAME", "ID", "ADDRESS","CONTACT INFO" };
        JTable jt = new JTable(data, column);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        screens[11].add(sp);
        JButton back = new JButton("Back to Home");
        back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	 CardLayout cl = (CardLayout) (parent.getLayout());
                         cl.show(parent, "Home Screen");
                    }});
        back.setSize(40, 40);
        JPanel bpane = new JPanel(new FlowLayout());
        bpane.add(back);
        screens[11].add(bpane);
        parent.add(screens[11], "Show All Suppliers");
    }

    private void showAllCustomers() {
        screens[12] = new JPanel(new GridLayout(2,1));
        ArrayList<String[]> arr=new ArrayList<String[]>();
        try {
    		 Class.forName(JDBC_DRIVER);
				Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
				//System.out.println("Connected With the database successfully");
				PreparedStatement preparedStatement=connection.prepareStatement("Select * from Customers");
				ResultSet rs = preparedStatement.executeQuery();
//				ResultSetMetaData rsmd = rs.getMetaData();
//				JTable tblData[];
//				DefaultTableModel model = (DefaultTableModel) tblData.getModel();
				while(rs.next())
				{
					String arr1[];
					arr1=new String[4];
					arr1[0]=(rs.getString("customer_name"));
					arr1[1]=(rs.getString("customer_id"));
					arr1[2]=(rs.getString("address"));
					arr1[3]=(rs.getString("contact_info"));
					arr.add(arr1);
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, e1.getMessage());
			}//Establishing connection
        String[][] data = new String[arr.size()][];
        String[] row;
        for(int i =0;i<arr.size();i++) {
        	row = arr.get(i);
        	data[i] = row;
        }
        String column[] = { "NAME", "ID", "ADDRESS","CONTACT INFO" };
        JTable jt = new JTable(data, column);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        screens[12].add(sp);
        JButton back = new JButton("Back to Home");
        back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	 CardLayout cl = (CardLayout) (parent.getLayout());
                         cl.show(parent, "Home Screen");
                    }});
        back.setSize(40, 40);
        JPanel bpane = new JPanel(new FlowLayout());
        bpane.add(back);
        screens[12].add(bpane);
        parent.add(screens[12], "Show All Customers");
    }

    private void showAllEmployees() {
        screens[13] = new JPanel(new GridLayout(2,1));
        ArrayList<String[]> arr=new ArrayList<String[]>();
        try {
    		 Class.forName(JDBC_DRIVER);
				Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
				//System.out.println("Connected With the database successfully");
				PreparedStatement preparedStatement=connection.prepareStatement("Select employees.employee_id ,employees.employee_name,employees.address,employees.contact_info,employees.job_position,sum(sales.quantity*products.price) as total_sales from ((products join sales on products.product_id=sales.product_id) right join employees on employees.employee_id=sales.employee_id) group by employees.employee_id;");
				ResultSet rs = preparedStatement.executeQuery();
//				ResultSetMetaData rsmd = rs.getMetaData();
//				JTable tblData[];
//				DefaultTableModel model = (DefaultTableModel) tblData.getModel();
				while(rs.next())
				{
					String arr1[];
					arr1=new String[6];
					arr1[0]=(rs.getString("employees.employee_id"));
					arr1[1]=(rs.getString("employees.employee_name"));
					arr1[2]=(rs.getString("employees.address"));
					arr1[3]=(rs.getString("employees.contact_info"));
					arr1[4]=(rs.getString("employees.job_position"));
					arr1[5]=(rs.getString("total_sales"));
					if(arr1[5]==null)
						arr1[5]="0.00";
					arr.add(arr1);
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, e1.getMessage());
			}//Establishing connection
        String[][] data = new String[arr.size()][];
        String[] row;
        for(int i =0;i<arr.size();i++) {
        	row = arr.get(i);
        	data[i] = row;
        }
        String column[] = { "ID", "NAME", "ADDRESS","CONTACT INFO","JOB POSITION", "TOTAL SALES"};
        JTable jt = new JTable(data, column);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        screens[13].add(sp);
        JButton back = new JButton("Back to Home");
        back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	 CardLayout cl = (CardLayout) (parent.getLayout());
                         cl.show(parent, "Home Screen");
                    }});
        back.setSize(40, 40);
        JPanel bpane = new JPanel(new FlowLayout());
        bpane.add(back);
        screens[13].add(bpane);
        parent.add(screens[13], "Employee Report");
    }

    private void showAllSales() {
        screens[14] = new JPanel(new GridLayout(2,1));
        ArrayList<String[]> arr=new ArrayList<String[]>();
        try {
    		 Class.forName(JDBC_DRIVER);
				Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
				//System.out.println("Connected With the database successfully");
				PreparedStatement preparedStatement=connection.prepareStatement("Select sales_id, date_of_sale, customer_name, product_name,employee_name,sales.quantity, (sales.quantity*products.price) as TotalAmount from (((employees join sales on employees.employee_id=sales.employee_id) join products on products.product_id=sales.product_id) join customers on customers.customer_id=sales.customer_id);");
				ResultSet rs = preparedStatement.executeQuery();
//				ResultSetMetaData rsmd = rs.getMetaData();
//				JTable tblData[];
//				DefaultTableModel model = (DefaultTableModel) tblData.getModel();
				while(rs.next())
				{
					String arr1[];
					arr1=new String[7];
					arr1[0]=(rs.getString("sales_id"));
					arr1[1]=(rs.getString("date_of_sale"));
					arr1[2]=(rs.getString("customer_name"));
					arr1[3]=(rs.getString("product_name"));
					arr1[4]=(rs.getString("employee_name"));
					arr1[5]=(rs.getString("sales.quantity"));
					arr1[6]=(rs.getString("TotalAmount"));
					arr.add(arr1);
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, e1.getMessage());
			}//Establishing connection
        String[][] data = new String[arr.size()][];
        String[] row;
        for(int i =0;i<arr.size();i++) {
        	row = arr.get(i);
        	data[i] = row;
        }
        String column[] = { "SALES ID", "DATE OF SALE", "CUSTOMER NAME","PRODUCT NAME","EMPLOYEE NAME", "QUANTITY", "TOTAL AMOUNT" };
        JTable jt = new JTable(data, column);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        screens[14].add(sp);
        JButton back = new JButton("Back to Home");
        back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	 CardLayout cl = (CardLayout) (parent.getLayout());
                         cl.show(parent, "Home Screen");
                    }});
        back.setSize(40, 40);
        JPanel bpane = new JPanel(new FlowLayout());
        bpane.add(back);
        screens[14].add(bpane);
        parent.add(screens[14], "Sales Report");
    }

    private void showAllPurchases() {
        screens[15] = new JPanel(new GridLayout(2,1));
        ArrayList<String[]> arr=new ArrayList<String[]>();
        try {
    		 Class.forName(JDBC_DRIVER);
				Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
				//System.out.println("Connected With the database successfully");
				PreparedStatement preparedStatement=connection.prepareStatement("Select * from purchases");
				ResultSet rs = preparedStatement.executeQuery();
//				ResultSetMetaData rsmd = rs.getMetaData();
//				JTable tblData[];
//				DefaultTableModel model = (DefaultTableModel) tblData.getModel();
				while(rs.next())
				{
					String arr1[];
					arr1=new String[5];
					arr1[0]=(rs.getString("date_of_purchase"));
					arr1[1]=(rs.getString("purchases_id"));
					arr1[2]=(rs.getString("supplier_id"));
					arr1[3]=(rs.getString("product_id"));
					arr1[4]=(rs.getString("quantity"));
					arr.add(arr1);
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, e1.getMessage());
			}//Establishing connection
        String[][] data = new String[arr.size()][];
        String[] row;
        for(int i =0;i<arr.size();i++) {
        	row = arr.get(i);
        	data[i] = row;
        }
        String column[] = { "DATE OF PURCHASE", "PURCHASE ID", "SUPPLIER ID","PRODUCT ID","QUANTITY" };
        JTable jt = new JTable(data, column);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        screens[15].add(sp);
        JButton back = new JButton("Back to Home");
        back.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	 CardLayout cl = (CardLayout) (parent.getLayout());
                         cl.show(parent, "Home Screen");
                    }});
        back.setSize(40, 40);
        JPanel bpane = new JPanel(new FlowLayout());
        bpane.add(back);
        screens[15].add(bpane);
        parent.add(screens[15], "Show All Purchases");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new App());
    }
}
