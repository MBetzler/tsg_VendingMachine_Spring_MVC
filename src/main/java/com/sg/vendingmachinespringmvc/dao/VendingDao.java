/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.dto.Product;
import java.util.List;

/**
 *
 * @author betzler
 */
public interface VendingDao {

    List<Product> getAllProducts() throws VendingPersistenceException;

    Product getProductByIndex(int index) throws VendingPersistenceException;

    void updateProduct(String name, Product product) throws VendingPersistenceException;
}
