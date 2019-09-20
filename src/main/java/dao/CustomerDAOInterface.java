/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;

/**
 *
 * @author vanta342
 */
public interface CustomerDAOInterface {
    void save(Customer customer);
    Customer getCustomer(String username);
    Boolean validateCredentials(String username, String password);
}
