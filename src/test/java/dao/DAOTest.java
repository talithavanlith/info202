/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vanta342
 */
public class DAOTest {
    
    //private InterfaceDAO dao = new ProductList();
    private InterfaceDAO dao = new ProductDatabase("jdbc:h2:tcp://localhost:9041/project-testing");
    
    private Product prodOne;
    private Product prodTwo;
    private Product prodThree;
    
    public DAOTest() {
    }
    
    @Before
    public void setUp() {
        this.prodOne = new Product(1, "name1", "desc1", "cat1", new BigDecimal("11.00"), new BigDecimal("22.00"));
        this.prodTwo = new Product(2, "name2", "desc2", "cat2", new BigDecimal("33.00"), new BigDecimal("44.00"));
        this.prodThree = new Product(3, "name3", "desc3", "cat3", new BigDecimal("55.00"), new BigDecimal("66.00"));
        
        // save the products
        dao.addProduct(prodOne);
        dao.addProduct(prodTwo);
        // Note: Intentionally not saving prodThree
    }
    
    @Test
    public void testDaoSave() {
        // save the product using DAO
        dao.addProduct(prodThree);
        
        // retrieve the same product via DAO
        Product retrieved = dao.search(3);
        
        // ensure that the product we saved is the one we got back
        assertEquals("Retrieved product should be the same", prodThree, retrieved);
    }
    
    @Test
    public void testDaoEdit() {
        
        // create a new product to overwrite product 1
        Product productEdit = new Product(1, "name1edit", "desc1", "cat1", new BigDecimal("11.00"), new BigDecimal("22.00"));
        
        // save the product using DAO
        dao.addProduct(productEdit);
        
        // retrieve the same product via DAO
        Product retrieved = dao.search(1);
        
        // ensure that the product was edited
        assertEquals("Retrieved product's name is changed", "name1edit", retrieved.getName());
    }
    
    @Test
    public void testDaoDelete() {
        // delete the product via the DAO
        dao.deleteProduct(prodOne);
        // try to retrieve the deleted product
        Product retrieved = dao.search(1);
        // ensure that the student was not retrieved (should be null)
        assertNull("Product should no longer exist", retrieved);
    }
    
    @Test
    public void testDaoGetAll() {
        Collection<Product> products = dao.getProducts();
        
        // ensure the result includes the two saved products
        assertTrue("prodOne should exist", products.contains(prodOne));
        assertTrue("prodTwo should exist", products.contains(prodTwo));
        
        // ensure the result ONLY includes the two saved products
        assertEquals("Only 2 products in result", 2, products.size());
        
        // find prodOne - result is not a map, so we have to scan for it
        for (Product p : products) {
            if (p.equals(prodOne)) {
                
                // ensure that all of the details were correctly retrieved
                assertEquals(prodOne.getProductID(), p.getProductID());
                assertEquals(prodOne.getName(), p.getName());
                assertEquals(prodOne.getDescription(), p.getDescription());
                assertEquals(prodOne.getCategory(), p.getCategory());
                assertEquals(prodOne.getListPrice(), p.getListPrice());
                assertEquals(prodOne.getQuantityInStock(), p.getQuantityInStock());
            }
        }
    }
    
    @Test
    public void testDaoGetCategories() {
        Collection<String> categories = dao.getCategories();
        
        // ensure the result includes the category's of the two saved products
        assertTrue("cat1 should be in the list returned", categories.contains("cat1"));
        assertTrue("cat2 should be in the list returned", categories.contains("cat2"));
        
        // ensure the result ONLY includes the category's of the two saved products
        assertEquals("Only 2 categories in result", 2, categories.size());
        
    }
    
    @Test
    public void testDaoSearch() {
        // get prodOne using search method
        Product result = dao.search(1);
        
        // assert that you got back prodOne, and not another product
        assertEquals("Product One was found", prodOne, result);
        
        // assert that prodOne's details were properly retrieved
        assertEquals(prodOne.getProductID(), result.getProductID());
        assertEquals(prodOne.getName(), result.getName());
        assertEquals(prodOne.getDescription(), result.getDescription());
        assertEquals(prodOne.getCategory(), result.getCategory());
        assertEquals(prodOne.getListPrice(), result.getListPrice());
        assertEquals(prodOne.getQuantityInStock(), result.getQuantityInStock());
        
        // call getById using a non-existent ID
        Product nullResult = dao.search(4);
        
        // assert that the result is null
        assertEquals("Result from searching a non-existent productID is null", null, nullResult);
        
    }
    
    @Test
    public void testDaoFilterCategories() {
        // get all products in the "cat1" category
        Collection<Product> result = dao.filterCategories("cat1");
        
        // assert that you got back only products in the "cat1" category (only prodOne)
        assertTrue("Product One was found", result.contains(prodOne));
        
        // assert that you only got one product back
        assertEquals("Only one product returned", 1, result.size());
        
        // call filterCategories using a non-existent category
        Collection<Product> EmptyResult = dao.filterCategories("cat4");
        
        // assert that the result is empty
        assertTrue("Result from filtering a non-existent category is empty", EmptyResult.isEmpty());
        
    }
    
    @After
    public void tearDown() {
        dao.deleteProduct(prodOne);
        dao.deleteProduct(prodTwo);
        dao.deleteProduct(prodThree);
    }
    
}
