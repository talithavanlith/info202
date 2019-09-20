/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.*;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author vanta342
 */
public class Server extends Jooby{
    
    InterfaceDAO dao = new ProductDatabase();
    CustomerDAOInterface cusDAO = new CustomerDatabase();
    SaleDAOInterface saleDAO = new SaleJdbcDAO();
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("\nStarting Server.");
        
        Server server = new Server();
        
        CompletableFuture.runAsync(() -> {
            server.start();
        });
        
        server.onStarted(() -> {
            System.out.println("\nPress Enter to stop the server.");
        });
        
        // wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
    }
    
    public Server(){
       port(4190);
       use(new AssetModule());
       use(new Gzon());
       use(new ProductModule(dao));
       use(new CustomerModule(cusDAO));
       use(new SaleModule(saleDAO));
    }
}
