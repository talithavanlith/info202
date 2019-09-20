/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;

/**
 *
 * @author vanta342
 */
public class SaleItem {
    
    BigDecimal quantityPurchased;
    BigDecimal salePrice;
    Sale sale;
    Product product;
    
    public SaleItem(Product product, BigDecimal salePrice, BigDecimal quantityPurchased) {
        if (product == null) {
            throw new IllegalArgumentException("You must provide a product");
        }
        this.quantityPurchased = quantityPurchased;
        this.salePrice = salePrice;
    }

    public BigDecimal getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(BigDecimal quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
    
    public BigDecimal getItemTotal() {
        return (salePrice.multiply(quantityPurchased));
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
}
