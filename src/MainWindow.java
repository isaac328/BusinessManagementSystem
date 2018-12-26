/**************************************
 * Project: Serialization2
 * File   : MainWindow
 * @author  Alex Isaac
 * Date   : 11/10/16
 * 
 * Description:
 * 	1.) The purpose of this code is to create a graphical user interface that a user can use to manage a contact list.
 *  2.) Data structures include if structures and an ArrayList to hold all the contacts.
 *  3.) There are several medium sized algorithms throughout this program. The most complex ones are involved in the save
 *  	buttons, where a JFileChooser is used to select a save location, ensures the correct extension is used, serializes 
 *  	the ArrayList, and saves it where ever the user wanted. Additionally, data validation is used in the form of
 *  	regular expressions to make sure forms are filled out correctly. Something of note is the use of a remote ActionListener
 *  	for the comboBox. While building this program, i ran into several errors (ArrayIndexOutOfBoundsException mostly) 
 *  	when trying to clear the comboBox in order to update it with new contacts. Some online research suggested that the
 *  	ActionListener on the comboBox was trying to perform each time a change was made, which was messing everything up. 
 *  	So i had to remove the actionListener each time i wanted to remove something, and then put it back on after the 
 *  	changes were made. Whether or not this was what was actually causing the problem, i have no clue, but it worked, so. 
 *
 * Changes:
 * 
 *************************************************/
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.apache.commons.io.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow {

	private JFrame frmContactManagementSystem;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtPhoneNumber;
	private JTextField txtEmail;
	private JTextField txtCompany;
	private JButton btnAdd; 
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnNew;
	private JFileChooser fileChooser;
	private JComboBox<String> comboBoxContacts;
	ArrayList<BusinessContact> contactList = new ArrayList<BusinessContact>();
	private boolean fileSaved = false;
	private File file;

	/**
	 * Method  : main
	 * Purpose : Launch the application
	 * @param String[] args
	 * @returns void, nothing
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmContactManagementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	
	//Remote action listener for comboBox since it needs to be added and removed a lot
	ActionListener comboAction = new ActionListener() {
		public void actionPerformed(ActionEvent arg0)
		{
			//get the index of the selected contact
			int num = comboBoxContacts.getSelectedIndex();
			
			//fill in all the text fields with the info of that contact
			txtFirstName.setText(contactList.get(num).getFirstName());
			txtLastName.setText(contactList.get(num).getLastName());
			txtPhoneNumber.setText(contactList.get(num).getPhoneNumber());
			txtEmail.setText(contactList.get(num).getEmailAddress());
			txtCompany.setText(contactList.get(num).getCompany());
			
			//enable/disable buttons
			btnAdd.setEnabled(false);
			btnUpdate.setEnabled(true);
		}
	};
	
	/**
	 * Method  : saveAs
	 * Purpose : Opens a fileChooser, lets the user select a save location, and saves the file there
	 * @returns void, nothing
	 */

	public void saveAs()
	{
		//Create filechooser, set mode, set recommended filename
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setSelectedFile(new File("contacts.ser"));
		
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.CANCEL_OPTION)
			return;
		
		//get the file and check to make sure the extension is .ser
		file = fileChooser.getSelectedFile();
		if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("ser")) 
		{
			file = new File(file.toString() + ".ser");
		} 
		
		//export the list
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getPath())))
		{
			out.writeObject(contactList);
			JOptionPane.showMessageDialog(null, "Save Successful!", "Success!", JOptionPane.PLAIN_MESSAGE);
			fileSaved = true;
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Unable to export contact list: " + ex.toString());
		}
	}
	/**
	 * Method  : initialize
	 * Purpose : Initialize the contents of the frame
	 * @param String[] args
	 * @returns void, nothing
	 */

	private void initialize() {
		frmContactManagementSystem = new JFrame();
		frmContactManagementSystem.setTitle("Contact Management System");
		frmContactManagementSystem.setResizable(false);
		frmContactManagementSystem.getContentPane().setBackground(Color.WHITE);
		frmContactManagementSystem.setBounds(100, 100, 798, 422);
		frmContactManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmContactManagementSystem.getContentPane().setLayout(null);
		
		JLabel lblContacts = new JLabel("Contacts");
		lblContacts.setBounds(15, 16, 69, 20);
		frmContactManagementSystem.getContentPane().add(lblContacts);
		
		comboBoxContacts = new JComboBox<String>();
		comboBoxContacts.addActionListener(comboAction); // add remote actionListener
		
		comboBoxContacts.setBounds(15, 38, 160, 26);
		frmContactManagementSystem.getContentPane().add(comboBoxContacts);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(304, 16, 108, 20);
		frmContactManagementSystem.getContentPane().add(lblFirstName);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(304, 38, 167, 26);
		frmContactManagementSystem.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(512, 16, 108, 20);
		frmContactManagementSystem.getContentPane().add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(512, 38, 222, 26);
		frmContactManagementSystem.getContentPane().add(txtLastName);
		txtLastName.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(304, 102, 121, 20);
		frmContactManagementSystem.getContentPane().add(lblPhoneNumber);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(304, 127, 167, 26);
		frmContactManagementSystem.getContentPane().add(txtPhoneNumber);
		txtPhoneNumber.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(512, 102, 69, 20);
		frmContactManagementSystem.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(512, 127, 222, 26);
		frmContactManagementSystem.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblCompany = new JLabel("Company");
		lblCompany.setBounds(304, 185, 108, 20);
		frmContactManagementSystem.getContentPane().add(lblCompany);
		
		txtCompany = new JTextField();
		txtCompany.setBounds(304, 214, 433, 26);
		frmContactManagementSystem.getContentPane().add(txtCompany);
		txtCompany.setColumns(10);
		
		btnNew = new JButton("New");
		btnNew.setEnabled(false);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				//resets texts fields
				txtFirstName.setText("");
				txtLastName.setText("");
				txtPhoneNumber.setText("");
				txtEmail.setText("");
				txtCompany.setText("");
				
				//disable update button, enable add button
				btnUpdate.setEnabled(false);
				btnAdd.setEnabled(true);
			}
		});
		btnNew.setBounds(27, 268, 148, 43);
		frmContactManagementSystem.getContentPane().add(btnNew);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				//filters for whether texts fields are filled out properly
				if (txtFirstName.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid first name.");
				}
				else if (txtLastName.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid last name.");
				}
				else if(!txtPhoneNumber.getText().matches("(\\(\\d{3}\\)\\d{3}-\\d{4})|(\\d{3}-\\d{3}-\\d{4})|(\\d{3}\\.\\d{3}\\.\\d{4})|(\\d{3}-\\d{3}-\\d{4})"))
				{
					JOptionPane.showMessageDialog(null, txtPhoneNumber.getText() + " is not a valid phone number.");
				}
				else if (!txtEmail.getText().matches("([a-zA-Z0-9]+|[a-zA-Z0-9]+\\.[a-zA-Z0-9]+)@[a-zA-Z]+\\.(com|biz|gov|net){1}"))
				{
					JOptionPane.showMessageDialog(null, txtEmail.getText() + " is not a valid Email Address.");
				}
				else if (txtCompany.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please enter a valid company name");
				}
				else
				{
					//creates new business contact with text field info
					BusinessContact newContact = new BusinessContact(txtFirstName.getText(), txtLastName.getText(), txtPhoneNumber.getText(),
							txtEmail.getText(), txtCompany.getText());
					
					//adds new contact to contact list, adds item to combobox, sets selected combobox to new contact, enables buttons
					contactList.add(newContact);
					comboBoxContacts.addItem(String.format("%s %s", newContact.getFirstName(), newContact.getLastName()));
					comboBoxContacts.setSelectedItem(String.format("%s %s", newContact.getFirstName(), newContact.getLastName()));
					btnDelete.setEnabled(true);
					btnNew.setEnabled(true);
				}
			}
		});
		btnAdd.setBounds(225, 268, 148, 43);
		frmContactManagementSystem.getContentPane().add(btnAdd);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					//get current index(contact they want to update)
					int num = comboBoxContacts.getSelectedIndex();
					
					//Update all the info for that contact in the contact list (on memory only)
					contactList.get(num).setFirstName(txtFirstName.getText());
					contactList.get(num).setLastName(txtLastName.getText());
					contactList.get(num).setPhoneNumber(txtPhoneNumber.getText());
					contactList.get(num).setEmailAddress(txtEmail.getText());
					contactList.get(num).setCompany(txtCompany.getText());
					
					//remove comboBox actionListener so it doesn't freak out when we remove everything
					comboBoxContacts.removeActionListener(comboAction);
				
					comboBoxContacts.removeAllItems();
				
					//refill combobox with updated contacts
					for(BusinessContact contact : contactList)
					{
						comboBoxContacts.addItem(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
					}
					
					//set comboBox to the one we just updated
					comboBoxContacts.setSelectedIndex(num);
					//put the actionListener back on
					comboBoxContacts.addActionListener(comboAction);
					}
					catch(ArrayIndexOutOfBoundsException ex)
					{
						JOptionPane.showMessageDialog(null, "Enter Missing Fields");
					}
			}
		});
		btnUpdate.setBounds(412, 268, 148, 43);
		frmContactManagementSystem.getContentPane().add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				//make sure they really want to delete it
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this contact?", "Warning", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION)
				{
					try
					{
						//get the index they want removed
						int num = comboBoxContacts.getSelectedIndex();
						
						//remove actionLister again so it doesn't mess everything up
						comboBoxContacts.removeActionListener(comboAction);
						
						//remove that contact in the contactList ArrayList, remove all items from comboBox
						contactList.remove(num);
						comboBoxContacts.removeAllItems();
						//if there was only 1 contact in the list and we just deleted it, set everything to empty
						if(contactList.isEmpty())
						{
							txtFirstName.setText("");
							txtLastName.setText("");
							txtPhoneNumber.setText("");
							txtEmail.setText("");
							txtCompany.setText("");
							
							btnNew.setEnabled(false);
							btnDelete.setEnabled(false);
							btnUpdate.setEnabled(false);
							btnAdd.setEnabled(true);
						}
						//otherwise refill comboBox with remaining contacts
						else
						{
							for(BusinessContact contact : contactList)
							{
								comboBoxContacts.addItem(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
							}
							num = comboBoxContacts.getSelectedIndex();
							
							//re-populate text fields with current contact information
							txtFirstName.setText(contactList.get(num).getFirstName());
							txtLastName.setText(contactList.get(num).getLastName());
							txtPhoneNumber.setText(contactList.get(num).getPhoneNumber());
							txtEmail.setText(contactList.get(num).getEmailAddress());
							txtCompany.setText(contactList.get(num).getCompany());
						}
						//add the actionListener back onto comboBox
						comboBoxContacts.addActionListener(comboAction);
					}
					catch(ArrayIndexOutOfBoundsException ex)
					{
						JOptionPane.showMessageDialog(null, "There are no contacts to delete");
					}	
				}
			}
		});
		btnDelete.setBounds(596, 268, 148, 43);
		frmContactManagementSystem.getContentPane().add(btnDelete);
		
		JLabel lblPic = new JLabel("");
		lblPic.setIcon(new ImageIcon(MainWindow.class.getResource("/Images/contactList.jpg")));
		lblPic.setBounds(68, 90, 128, 131);
		frmContactManagementSystem.getContentPane().add(lblPic);
		
		JMenuBar menuBar = new JMenuBar();
		frmContactManagementSystem.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setIcon(new ImageIcon(MainWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(fileSaved == false)
				{
					saveAs();
				}
				else
				{
					try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getPath())))
					{
						out.writeObject(contactList);
						JOptionPane.showMessageDialog(null, "Save Successful!", "Success!", JOptionPane.PLAIN_MESSAGE);
						fileSaved = true;
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Unable to export contact list: " + ex.toString());
					}
				}
				
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmOpenContactList = new JMenuItem("Open Contact List");
		mntmOpenContactList.setIcon(new ImageIcon(MainWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/TreeOpen.gif")));
		mntmOpenContactList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				//create file chooser, set selection mode, and put on a filter so the user can only select .ser files
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("serialized extension","ser");
				fileChooser.setFileFilter(filter);
				
				int result = fileChooser.showOpenDialog(null);
				if(result == JFileChooser.CANCEL_OPTION)
					return;
				
				file = fileChooser.getSelectedFile();
				
				try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getPath())))
				{
					contactList = (ArrayList<BusinessContact>) in.readObject();
					
					//remove combo box action listener so it doesn't freak out when removing items
					comboBoxContacts.removeActionListener(comboAction);
					
					comboBoxContacts.removeAllItems();
					//refill combo box with new contacts
					for(BusinessContact contact : contactList)
					{
						comboBoxContacts.addItem(String.format("%s %s", contact.getFirstName(), contact.getLastName()));
					}
					//set combo box to first contact
					comboBoxContacts.setSelectedIndex(0);
					
					//re-populate text fields with first contact
					txtFirstName.setText(contactList.get(0).getFirstName());
					txtLastName.setText(contactList.get(0).getLastName());
					txtPhoneNumber.setText(contactList.get(0).getPhoneNumber());
					txtEmail.setText(contactList.get(0).getEmailAddress());
					txtCompany.setText(contactList.get(0).getCompany());
					
					//reset buttons
					btnAdd.setEnabled(false);
					btnNew.setEnabled(true);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					
					//add the action listener back on to comboBox
					comboBoxContacts.addActionListener(comboAction);	
					
					//set filedSaved to true
					fileSaved = true;
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Unable to Import contact list: " + ex.toString(), "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				saveAs();
			}
		});
		mntmSaveAs.setIcon(new ImageIcon(MainWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		mnFile.add(mntmSaveAs);
		mnFile.add(mntmOpenContactList);
	}
}
