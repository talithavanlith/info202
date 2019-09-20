/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/**
 *
 * @author vanta342
 */
import dao.CustomerDAOInterface;
import domain.Customer;
import org.jooby.Err;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author vanta342
 */
public class CustomerModule extends Jooby{
       
    
    
    public CustomerModule(CustomerDAOInterface dao){
        get("/api/customer/:username", (req) -> {
            String username = req.param("username").value();
            final Customer customer = dao.getCustomer(username);
            if(customer == null){
                
                throw new Err(Status.NOT_FOUND);
            }
            customer.setPassword(null);
            return customer;
        });
        
        get("/api/validate/:values", (req) -> {
            String values = req.param("values").value();
            String[] splitValues = values.split(" ");
            
            final Boolean isValid = dao.validateCredentials(splitValues[0], splitValues[1]);
            final Customer customer = dao.getCustomer(splitValues[0]);
            
            if(isValid == null || customer == null){
                throw new Err(Status.NOT_FOUND);
            }
            if(isValid == true){
                return customer;
                
            }else{
                return null;
            }
        });
        
        post("/api/register", (req, rsp) -> {
            Customer customer = req.body().to(Customer.class);
            dao.save(customer);
            rsp.status(Status.CREATED);
        });
    }
}
