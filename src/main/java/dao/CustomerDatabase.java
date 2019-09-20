/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import java.sql.*;

/**
 *
 * @author vanta342
 */
public class CustomerDatabase implements CustomerDAOInterface{
    
    private String url = "jdbc:h2:tcp://localhost:9041/project;IFEXISTS=TRUE";

    public CustomerDatabase() {
    }
    
    public CustomerDatabase(String url) {
        this.url = url;
    }

    @Override
    public void save(Customer customer) {
        
        String sql="insert into Customer (Username, Firstname, Surname, Password, Email, ShippingAddress, CreditCardDetails) values (?,?,?,?,?,?,?)";
         
        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
            ) {
            // copy the data from the product domain object into the SQL parameters
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getFirstname());
            stmt.setString(3, customer.getSurname());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getShippingAddress());
            stmt.setString(7, customer.getCreditCardDetails());

            stmt.executeUpdate();  // execute the statement

        } catch(SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
       }
    }

    @Override
    public Customer getCustomer(String username) {
        if(username == null) return null;
        
        String sql = "select * from customer where username = ?";

        try (
            // get connection to database
            Connection connection = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            // set the parameter
            stmt.setString(1, username);

            // execute the query
            ResultSet rs = stmt.executeQuery();

            // query only returns a single result, so use 'if' instead of 'while'
            if (rs.next()) {
                Integer id = rs.getInt("person_ID");
                String firstname = rs.getString("firstname");
                String surname = rs.getString("surname");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String address = rs.getString("shippingaddress");
                String credit = rs.getString("creditcarddetails");

                return new Customer(id, username, firstname, surname, password, email, address, credit);

            } else {
                // no customer matches given username so return null
                return null;
            }

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public Boolean validateCredentials(String username, String password) {
        if(username == null || password == null) return null;
        
        String sql = "SELECT * FROM customer WHERE username = ? AND password = ?";

        try (
            // get connection to database
            Connection connection = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            // set the parameter
            stmt.setString(1, username);
            stmt.setString(2, password);

            // execute the query
            ResultSet rs = stmt.executeQuery();

            // query only returns a single result, so use 'if' instead of 'while'
            if(rs.next()){
                return(true);
            }else{
                return false;
            }
            

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new RuntimeException(ex);
        }    
        
    }
    
}
