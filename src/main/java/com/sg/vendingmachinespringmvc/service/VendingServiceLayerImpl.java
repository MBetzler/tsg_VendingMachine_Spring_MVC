/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.service;

import com.sg.vendingmachinespringmvc.dao.VendingDao;
import com.sg.vendingmachinespringmvc.dao.VendingPersistenceException;
import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.dto.Money;
import com.sg.vendingmachinespringmvc.dto.Product;
import com.sg.vendingmachinespringmvc.dto.SpendingMoney;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

/**
 *
 * @author betzler
 */
@Component
public class VendingServiceLayerImpl implements VendingServiceLayer {

    @Inject
    VendingDao dao;

    @Override
    public List<Product> getAllProducts() throws VendingPersistenceException {
        return dao.getAllProducts();
    }

    @Override
    public Product getProductByIndex(int index) throws VendingPersistenceException {
        return dao.getProductByIndex(index);
    }

    @Override
    public void updateProduct(String name, Product product) throws VendingPersistenceException {
        dao.updateProduct(name, product);
    }

    @Override
    public void checkProductStock(Product product) throws VendingNoItemInventoryException {
        if (product.getProductQuantity() <= 0) {
            throw new VendingNoItemInventoryException("SOLD OUT!!!");
        }
    }

    @Override
    public void checkCustomerFunds(Product product, SpendingMoney enteredMoney) throws VendingInsufficientFundsException {
        if (enteredMoney.getTotal() == null) {
            throw new VendingInsufficientFundsException("You must first insert money.");
        } else if (product.getProductPrice().compareTo(enteredMoney.getTotal()) > 0) {
            throw new VendingInsufficientFundsException("Please insert: $" + product.getProductPrice().subtract(enteredMoney.getTotal()) + ".");
        }
    }

    @Override
    public String buildChangeText(Change change) {
        String changeText = "";

        if (change.getCoinQuantities()[0] > 0) {
            if (change.getCoinQuantities()[0] == 1) {
                changeText += "1 quarter";
            } else {
                changeText += change.getCoinQuantities()[0] + " " + Money.QUARTER.getPrint();
            }
        }

        if (change.getCoinQuantities()[1] > 0) {
            if (changeText.length() > 0) {
                changeText += ", ";
            }

            if (change.getCoinQuantities()[1] == 1) {
                changeText += "1 dime";
            } else {
                changeText += change.getCoinQuantities()[1] + " " + Money.DIME.getPrint();
            }
        }

        if (change.getCoinQuantities()[2] > 0) {
            if (changeText.length() > 0) {
                changeText += ", ";
            }

            if (change.getCoinQuantities()[2] == 1) {
                changeText += "1 nickel";
            } else {
                changeText += change.getCoinQuantities()[2] + " " + Money.NICKEL.getPrint();
            }
        }

        if (change.getCoinQuantities()[3] > 0) {
            if (changeText.length() > 0) {
                changeText += ", ";
            }

            if (change.getCoinQuantities()[3] == 1) {
                changeText += "1 penny";
            } else {
                changeText += change.getCoinQuantities()[3] + " " + Money.PENNY.getPrint();
            }
        }

        return changeText;
    }
}
