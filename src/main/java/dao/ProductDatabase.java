/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 *
 * @author vanta342
 */
public class ProductDatabase implements InterfaceDAO{
    
    private String url = "jdbc:h2:tcp://localhost:9041/project;IFEXISTS=TRUE";

    public ProductDatabase() {
    }
    
    public ProductDatabase(String url) {
        this.url = url;
    }

    @Override
    public void addProduct(Product product) {
        
        String sql="merge into Product (Product_ID, Name, Description, Category, ListPrice, QuantityInStock) values (?,?,?,?,?,?)";
           
        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
            ) {
            // copy the data from the product domain object into the SQL parameters
            stmt.setInt(1, product.getProductID());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setBigDecimal(5, product.getListPrice());
            stmt.setBigDecimal(6, product.getQuantityInStock());

            stmt.executeUpdate();  // execute the statement

        } catch(SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
       }
    }

    @Override
    public void deleteProduct(Product product) {

        String sql="delete from product where product_id = ?";

        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            // copy the data from the student domain object into the SQL parameters
            stmt.setInt(1, product.getProductID());

            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new RuntimeException(ex);
        }

    }

    @Override
    public Collection<Product> filterCategories(String category) {
        String sql = "select * from product where category = ?";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            stmt.setString(1, category);
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            ArrayList<Product> products = new ArrayList<>();

            // iterate through the query results
            while (rs.next()) {

                // get the data out of the query
                Integer id = rs.getInt("product_ID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal listPrice = rs.getBigDecimal("listPrice");
                BigDecimal quantityInStock = rs.getBigDecimal("quantityInStock");
                

                // use the data to create a student object
                Product p = new Product(id, name, description, category, listPrice, quantityInStock);

                // and put it in the collection
                products.add(p);
            }

            return products;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<String> getCategories() {
        
        String sql = "select distinct category from Product order by category";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            ArrayList<String> categories = new ArrayList<>();

            // iterate through the query results
            while (rs.next()) {

                // get the data out of the query
                String category = rs.getString("category");

                // and put it in the collection
                categories.add(category);
            }

            return categories;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection<Product> getProducts() {

        String sql = "select * from product order by product_ID";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            ArrayList<Product> products = new ArrayList<>();

            // iterate through the query results
            while (rs.next()) {

                // get the data out of the query
                Integer id = rs.getInt("product_ID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal listPrice = rs.getBigDecimal("listPrice");
                BigDecimal quantityInStock = rs.getBigDecimal("quantityInStock");
                

                // use the data to create a student object
                Product p = new Product(id, name, description, category, listPrice, quantityInStock);

                // and put it in the collection
                products.add(p);
            }

            return products;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public Product search(Integer product_ID) {
        if(product_ID == null) return null;
        
        String sql = "select * from product where product_id = ?";

        try (
            // get connection to database
            Connection connection = JdbcConnection.getConnection(url);

            // create the statement
            PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            // set the parameter
            stmt.setInt(1, product_ID);

            // execute the query
            ResultSet rs = stmt.executeQuery();

            // query only returns a single result, so use 'if' instead of 'while'
            if (rs.next()) {
                Integer id = rs.getInt("product_ID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal listPrice = rs.getBigDecimal("listPrice");
                BigDecimal quantityInStock = rs.getBigDecimal("quantityInStock");

                return new Product(id, name, description, category, listPrice, quantityInStock);

            } else {
                // no student matches given ID so return null
                return null;
            }

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new RuntimeException(ex);
        }
    }
    
}
