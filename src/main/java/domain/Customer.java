/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author vanta342
 */
public class Customer {
    
    Integer personID;
    String username;
    String firstname;
    String surname;
    String password;
    String email;
    String shippingAddress;
    String creditCardDetails;

    public Customer() {
    }

    public Customer(Integer personID, String username, String firstname, String surname, String password, String email, String shippingAddress, String creditCardDetails) {
        this.personID = personID;
        this.username = username;
        this.firstname = firstname;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.creditCardDetails = creditCardDetails;
    }

    public Customer(String username, String firstname, String surname, String password, String email, String shippingAddress, String creditCardDetails) {
        this.username = username;
        this.firstname = firstname;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.creditCardDetails = creditCardDetails;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }
    
}
