/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.InterfaceDAO;
import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.TreeSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author vanta342
 */
public class ProductEditorTest {
    
    private InterfaceDAO dao;
    //private InterfaceDAO dao = new ProductDatabase("jdbc:h2:tcp://localhost:9041/project-testing");
    private DialogFixture fixture;
    private Robot robot;
    
    @Before
    public void setUp() {
            robot = BasicRobot.robotWithNewAwtHierarchy();

            // Slow down the robot a little bit - default is 30 (milliseconds).
            // Do NOT make it less than 5 or you will have thread-race problems.
            robot.settings().delayBetweenEvents(20);

            // add some majors for testing with
            Collection<String> categories = new TreeSet<>();
            categories.add("Food");
            categories.add("Clothing");
            
            // create a mock for the DAO
            dao = mock(InterfaceDAO.class);

            // stub the getCategories method to return the test categories
            when(dao.getCategories()).thenReturn(categories);
    }
    
    
    @Test
    public void testSave() {
            // create the dialog passing in the mocked DAO
            ProductEditor dialog = new ProductEditor(null, true, dao);

            // use AssertJ to control the dialog
            fixture = new DialogFixture(robot, dialog);
            fixture.show().requireVisible();

            // enter some details into the UI components
            fixture.textBox("txtID").enterText("1234");
            fixture.textBox("txtName").enterText("T-Shirt");
            fixture.textBox("txtDescription").enterText("Green with a star on the front");
            fixture.comboBox("comboCategory").selectItem("Clothing");
            fixture.textBox("txtPrice").enterText("9.50");
            fixture.textBox("txtQuantity").enterText("70");

            // click the save button
            fixture.button("buttonSave").click();

            // create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
            ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

            // verify that the DAO.save method was called, and capture the passed student
            verify(dao).addProduct(argument.capture());

            // retrieve the passed student from the captor
            Product savedProduct = argument.getValue();

            // test that the student's details were properly saved
            assertEquals("Ensure the ID was saved", new Integer(1234), savedProduct.getProductID());
            assertEquals("Ensure the name was saved", "T-Shirt", savedProduct.getName());
            assertEquals("Ensure the comment was saved", "Green with a star on the front", savedProduct.getDescription());
            assertEquals("Ensure the category was saved", "Clothing", savedProduct.getCategory());
            assertEquals("Ensure the list price was saved", new BigDecimal("9.5"), savedProduct.getListPrice());
            assertEquals("Ensure the quantity was saved", new BigDecimal("70"), savedProduct.getQuantityInStock());
    }
          
    
    @Test
    public void testEdit() {
            //product to be edited
            Product product = new Product(1234, "Ice-Cream", "Chocolate", "Food", new BigDecimal("5.5"), new BigDecimal("23"));

            // create dialog passing in product and mocked DAO
            ProductEditor dialog = new ProductEditor(null, true, product, dao);

            // use AssertJ to control the dialog
            fixture = new DialogFixture(robot, dialog);

            // show the dialog on the screen, and ensure it is visible
            fixture.show().requireVisible();

            // verify that the UI componenents contains the product's details
            fixture.textBox("txtID").requireText("1234");
            fixture.textBox("txtName").requireText("Ice-Cream");
            fixture.textBox("txtDescription").requireText("Chocolate");
            fixture.comboBox("comboCategory").requireSelection("Food");
            fixture.textBox("txtPrice").requireText("5.50");
            fixture.textBox("txtQuantity").requireText("23");
            
            // edit the name
            fixture.textBox("txtName").selectAll().deleteText().enterText("Smoothie");

            // click the save button
            fixture.button("buttonSave").click();

            // create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
            ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

            // verify that the DAO.save method was called, and capture the passed product
            verify(dao).addProduct(argument.capture());

            // retrieve the passed product from the captor
            Product editedProduct = argument.getValue();

            // check that the changes were saved
            assertEquals("Ensure the name was changed", "Smoothie", editedProduct.getName());
    }
        
    @After
    public void tearDown() {
        // clean up fixture so that it is ready for the next test
        fixture.cleanUp();
        
    }
    
}
