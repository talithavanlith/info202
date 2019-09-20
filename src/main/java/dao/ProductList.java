/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Product;
import java.util.*;

/**
 *
 * @author vanta342
 */
public class ProductList implements InterfaceDAO{
    
    static Map<Integer, Product> productMap = new HashMap<>();
    static Multimap<String, Product> categoryMap = HashMultimap.create();

    @Override
    public Collection<Product> getProducts() {
        return productMap.values();
    }

    @Override
    public void addProduct(Product product) {
        productMap.put(product.getProductID(), product);
        categoryMap.put(product.getCategory(), product);
    }
    
    @Override
    public void deleteProduct(Product product) {
        productMap.remove(product.getProductID(), product);
        categoryMap.remove(product.getCategory(), product);
    }

    @Override
    public Collection<String> getCategories() {
        return categoryMap.keySet();
    }
    
    @Override
    public Product search(Integer productID){
        return productMap.get(productID);
    }
    
    @Override
    public Collection<Product> filterCategories(String category){
        return categoryMap.get(category);
    }
}

