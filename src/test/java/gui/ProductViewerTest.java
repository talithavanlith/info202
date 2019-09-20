/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.InterfaceDAO;
import dao.ProductDatabase;
import domain.Product;
import gui.helpers.SimpleListModel;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author vanta342
 */
public class ProductViewerTest {
    
    private InterfaceDAO dao;// = new ProductDatabase("jdbc:h2:tcp://localhost:9041/project-testing");
    private DialogFixture fixture;
    private Robot robot;
    private Product product1;
    private Product product2;
    private Product product3;
    
    @Before
    public void setUp() {
            robot = BasicRobot.robotWithNewAwtHierarchy();

            // Slow down the robot a little bit - default is 30 (milliseconds).
            // Do NOT make it less than 5 or you will have thread-race problems.
            robot.settings().delayBetweenEvents(50);
            
            // create a mock for the DAO
            dao = mock(InterfaceDAO.class);
            
            // create a products collection for stubbing purposes
            Collection<Product> products = new HashSet<>();
            
            // create some products for testing with
            product1 = new Product(1234, "Ice-Cream", "Chocolate", "Food", new BigDecimal("5.5"), new BigDecimal("23"));
            product2 = new Product(5678, "Smoothie", "Strawberry", "Food", new BigDecimal("4.5"), new BigDecimal("27"));
            product3 = new Product(9999, "T-Shirt", "Green", "Clothes", new BigDecimal("9.5"), new BigDecimal("83"));

            // add the test products to the product list
            products.add(product1);
            products.add(product2);
            products.add(product3);
            
            // stub the getProducts method
            when(dao.getProducts()).thenReturn(products);
            
//            // stub the deleteProduct method
//            Mockito.doAnswer(new Answer<Void>() {
//               @Override
//               public Void answer(InvocationOnMock invocation) throws Throwable {
//                  products.remove(product1);
//                  return null;
//               }
//            }).when(dao).deleteProduct(product1);
    }
    
    
    @Test
    public void testComboBox() {
// create the dialog passing in the mocked DAO
            ProductViewer dialog = new ProductViewer(null, true, dao);

            // use AssertJ to control the dialog
            fixture = new DialogFixture(robot, dialog);
            fixture.show().requireVisible();
            
            Mockito.verify(dao).getProducts();

            // get the model
            SimpleListModel model = (SimpleListModel) fixture.list("comboProductList").target().getModel();

            // check the contents
            assertTrue("list contains product1", model.contains(product1));
            assertTrue("list contains product2", model.contains(product2));
            assertTrue("list contains product3", model.contains(product2));
            assertEquals("list contains the correct number of products", 3, model.getSize());
    }
          
    
     
    @After
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
        
    }
    
}
