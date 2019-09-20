/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.Objects;
import net.sf.oval.constraint.*;

/**
 *
 * @author vanta342
 */
public class Product {
    
    @NotNull(message = "Product ID must be provided.")
    @NotNegative(message = "Product ID cannot contain a negative sign.")
    @Length(max=9, message="Product ID cannot be longer than 9 characters.")
    private Integer productID;
    
    @NotNull(message = "Name must be provided.")
    @NotBlank(message = "Name must be provided.")
    @Length(min=2, max=50, message="Name must contain between 2-50 characters.")
    private String name;
    
    @Length(max=200, message="Description cannot be longer than 200 characters.")
    private String description;
    
    @NotNull(message = "Category must be provided.")
    @NotBlank(message = "Category must be provided.")
    @Length(max=20, message="Category cannot be longer than 20 characters.")
    private String category;
    
    @NotNull(message = "Price must be provided.")
    @NotNegative(message = "Price must be zero or greater.")
    private BigDecimal listPrice;
    
    @NotNull(message = "Quantity must be provided.")
    @NotNegative(message = "Quantity must be zero or greater.")
    private BigDecimal quantityInStock;

    public Product() {
    }
    
    public Product(Integer productID, String name, String description, String Category, BigDecimal listPrice, BigDecimal quantityInStock) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.category = Category;
        this.listPrice = listPrice;
        this.quantityInStock = quantityInStock;
    }
    
    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String Category) {
        this.category = Category;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(BigDecimal quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "PRODUCT DETAILS = productID: " + productID + ", name: " + name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }
    
    
    
}
