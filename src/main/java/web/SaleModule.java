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
//import dao.SaleDAOInterface;
import dao.SaleDAOInterface;
import domain.Sale;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author vanta342
 */
public class SaleModule extends Jooby{ 
    
    public SaleModule(SaleDAOInterface dao){

        post("/api/sales", (req, rsp) -> {
            Sale sale = req.body().to(Sale.class);
            sale.setStatus("Order Complete");
            dao.save(sale);
            dao.email(sale);
            System.out.println(sale.toString());
            rsp.status(Status.CREATED);
        });
    }
}
