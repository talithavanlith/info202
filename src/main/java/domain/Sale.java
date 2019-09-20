/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author vanta342
 */
public class Sale {
    
    Customer customer;
    Integer saleID;
    Date date;
    String status;
    ArrayList<SaleItem> saleItemList = new ArrayList();

    public Sale(Customer customer, Integer saleID, Date date, String status) {
        if (customer == null) {
            throw new IllegalArgumentException("You must provide a customer");
        }
        this.saleID = saleID;
        this.date = date;
        this.status = status;
    }

    public Integer getSaleID() {
        return saleID;
    }

    public void setSaleID(Integer saleID) {
        this.saleID = saleID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public BigDecimal getTotal(){
        BigDecimal result = new BigDecimal(0);
        for (SaleItem item : saleItemList){
            result.add(item.getItemTotal());
        }
        return result;
    }
    
    public void addItem(SaleItem saleItem){
        saleItemList.add(saleItem);
    }

    public ArrayList getItems() {
        return saleItemList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<SaleItem> getSaleItemList() {
        return saleItemList;
    }

    public void setSaleItemList(ArrayList<SaleItem> saleItemList) {
        this.saleItemList = saleItemList;
    }

    @Override
    public String toString() {
        return "Sale{" + "customer=" + customer.getUsername() + ", saleID=" + saleID + ", date=" + date + ", status=" + status + ", saleItemList=" + saleItemList + '}';
    }

    
}
