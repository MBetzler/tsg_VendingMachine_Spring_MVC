/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author betzler
 */
public class Product {

    private String productName;
    private BigDecimal productPrice;
    private int productQuantity;

    public Product(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void decrementProductQuantity() {
        this.productQuantity = this.productQuantity - 1;
    }

    @Override
    public String toString() {
        return "Product: " + productName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.productName);
        hash = 67 * hash + Objects.hashCode(this.productPrice);
        hash = 67 * hash + this.productQuantity;
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
        if (this.productQuantity != other.productQuantity) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.productPrice, other.productPrice)) {
            return false;
        }
        return true;
    }

}
