/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Collection;

/**
 *
 * @author vanta342
 */
public interface InterfaceDAO {

    void addProduct(Product product);

    void deleteProduct(Product product);

    Collection<Product> filterCategories(String category);

    Collection<String> getCategories();

    Collection<Product> getProducts();

    Product search(Integer productID);

  
    
}
