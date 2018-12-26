/**************************************
 * Project: Serialization2
 * File   : BusinessContact
 * @author  Alex Isaac
 * Date   : 11/10/16
 * 
 * Description:
 * 	1.) The purpose of this code is to create the layout for a Business Contact
 *  2.) There are no data structures other than simple if statements using regex data validation
 *  3.) There are no major algorithms used other than a few regular expressions for data validation.
 *  	I chose to put data validation on both ends of the program so that when updating contacts,
 *  	i could simply call the set methods and keep things a little less cluttered in the main window.
 *  	I couln't put data validation on only this end because when creating a new contact, if something 
 *  	was improperly formatted the object would still be created anyways, which was bad.
 *
 * Changes:
 * 
 *************************************************/
import java.io.Serializable;

import javax.swing.JOptionPane;

public class BusinessContact implements Serializable
{
	String firstName;
	String lastName;
	String phoneNumber;
	String emailAddress;
	String company;
	
	/**
	 * Method  : BusinessContact
	 * Purpose : Initialize a business contact
	 * @param  firstName the first name of the contact
	 * @param lastName the last name of the contact
	 * @param phoneNumber the phone number of the contact
	 * @param emailAddress the email of the contact
	 * @param company where the person works
	 * @returns void, nothing
	 */
	public BusinessContact(String firstName, String lastName, String phoneNumber, String emailAddress, String company)
	{
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setEmailAddress(emailAddress);
		setCompany(company);
	}
	/**
	 * Method : getFirstName
	 * Purpose: get the first name of the contact
	 * @return the firstName
	 */
	public String getFirstName() 
	{
		return firstName;
	}
	/**
	 * Method : setFirstName
	 * Purpose: set the first name of the contact
	 * @param firstName the firstName to set
	 * Returns: void, nothing
	 */
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	/**
	 * Method : getLastName
	 * Purpose: get the last name of the contact
	 * @return the lastName
	 */
	public String getLastName() 
	{
		return lastName;
	}
	/**
	 * Method : setLastName
	 * Purpose: set the last name of the contact
	 * @param lastName the lastName to set
	 * Returns: void, nothing
	 */
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	/**
	 * Method : getPhoneNumber
	 * Purpose: get the phone number of the contact
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	/**
	 * Method : setPhoneNumber
	 * Purpose: set the phone number of the contact with data validation
	 * @param phoneNumber the phoneNumber to set
	 * Returns: void, nothing
	 */
	public void setPhoneNumber(String phoneNumber) 
	{
		if(phoneNumber.matches("(\\(\\d{3}\\)\\d{3}-\\d{4})|(\\d{3}-\\d{3}-\\d{4})|(\\d{3}\\.\\d{3}\\.\\d{4})|(\\d{3}-\\d{3}-\\d{4})"))
		{
			this.phoneNumber = phoneNumber;
		}
		else
		{
			JOptionPane.showMessageDialog(null, phoneNumber + " is not a valid phone number.");
		}
	}
	/**
	 * Method : getEmailAddress
	 * Purpose: get the email address of the contact
	 * @return the emailAddress
	 */
	public String getEmailAddress() 
	{
		return emailAddress;
	}
	/**
	 * Method : setEmailAddress
	 * Purpose: set the email address of the contact
	 * @param emailAddress the emailAddress to set
	 * Returns: void, nothing
	 */
	public void setEmailAddress(String emailAddress) 
	{
		if(emailAddress.matches("([a-zA-Z0-9]+|[a-zA-Z0-9]+\\.[a-zA-Z0-9]+)@[a-zA-Z]+\\.(com|biz|gov|net){1}"))
		{
			this.emailAddress = emailAddress;
		}
		else
		{
			JOptionPane.showMessageDialog(null, emailAddress + " is not a valid Email Address.");
		}
	}
	/**
	 * Method : getCompany
	 * Purpose: get the company of the contact
	 * @return the company
	 */
	public String getCompany() 
	{
		return company;
	}
	/**
	 * Method : setCompany
	 * Purpose: set the company of the contact
	 * @param company the company to set
	 * Returns: void, nothing
	 */
	public void setCompany(String company) 
	{
		this.company = company;
	}
	
	/**
	 * Method : toString
	 * Purpose: convert the contact to a string
	 * @return String the contact as a formatted string
	 */
	@Override
	public String toString()
	{
		return String.format("First Name: %s\nLast Name: %s\nPhone Number: %s\n Email: %s\n Company: %s\n\n",
				firstName, lastName, phoneNumber, emailAddress, company);
	}
}
