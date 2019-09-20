/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.InterfaceDAO;
import org.jooby.Jooby;

/**
 *
 * @author vanta342
 */
public class ProductModule extends Jooby{
        
   
    public ProductModule(InterfaceDAO dao){
       get("/api/products", () -> dao.getProducts());
       
       get("/api/products/:id", (req) -> {
            int id = req.param("id").intValue();
            return dao.search(id);
       });
       
       get("/api/categories", () -> dao.getCategories());
       get("/api/categories/:category", (req) -> {
            String category = req.param("category").value();
            return dao.filterCategories(category);
       });
    }
}
