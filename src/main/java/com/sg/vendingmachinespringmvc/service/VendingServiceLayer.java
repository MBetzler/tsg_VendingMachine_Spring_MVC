/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dao.VendingPersistenceException;
import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.dto.Product;
import com.sg.vendingmachinespringmvc.dto.SpendingMoney;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface VendingServiceLayer {

    List<Product> getAllProducts() throws VendingPersistenceException;

    Product getProductByIndex(int index) throws VendingPersistenceException;

    void updateProduct(String name, Product product) throws VendingPersistenceException;

    void checkProductStock(Product product) throws VendingNoItemInventoryException;

    void checkCustomerFunds(Product product, SpendingMoney enteredMoney) throws VendingInsufficientFundsException;

    String buildChangeText(Change change);
}
