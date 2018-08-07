/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.controller;

import com.sg.vendingmachinespringmvc.dao.VendingPersistenceException;
import com.sg.vendingmachinespringmvc.dto.Change;
import com.sg.vendingmachinespringmvc.dto.Money;
import com.sg.vendingmachinespringmvc.dto.Product;
import com.sg.vendingmachinespringmvc.dto.SpendingMoney;
import com.sg.vendingmachinespringmvc.service.VendingInsufficientFundsException;
import com.sg.vendingmachinespringmvc.service.VendingNoItemInventoryException;
import com.sg.vendingmachinespringmvc.service.VendingServiceLayer;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author betzler
 */
@Controller
public class VendingController {

    Integer productIndex;
    BigDecimal enteredMoney;
    String vendingMessages;
    String returnedChange;

    @Inject
    VendingServiceLayer service;

    @GetMapping("/")
    public String loadIndex(Model model) throws VendingPersistenceException {
        List<Product> productList = service.getAllProducts();
        model.addAttribute("productList", productList);
        model.addAttribute("productIndex", productIndex);
        model.addAttribute("totalMoney", enteredMoney);
        model.addAttribute("returnedChange", returnedChange);
        model.addAttribute("vendingMessages", vendingMessages);

        return "index";
    }

    @PostMapping("/selectProduct")
    public String selectProduct(Model model, Integer selectedIndex) {
        productIndex = selectedIndex;
        vendingMessages = "";
        returnedChange = "";

        return "redirect:/";
    }

    @PostMapping("/addMoney")
    public String addMoney(Model model, BigDecimal totalMoney, Money moneyAdded) {
        if (totalMoney == null) {
            totalMoney = new BigDecimal("0");
        }

        SpendingMoney currentMoney = new SpendingMoney(totalMoney);
        currentMoney.addMoney(moneyAdded, 1);

        enteredMoney = currentMoney.getTotal();
        vendingMessages = "";
        returnedChange = "";

        return "redirect:/";
    }

    @PostMapping("/getChange")
    public String getChange(Model model) {
        if (enteredMoney == null || enteredMoney.compareTo(new BigDecimal("0")) == 0) {
            vendingMessages = "There's no change to dispense.";
            returnedChange = "";
        } else {
            Change change = new Change(enteredMoney);
            returnedChange = service.buildChangeText(change);
            vendingMessages = "";
        }

        enteredMoney = null;
        productIndex = null;

        return "redirect:/";
    }

    @PostMapping("/makePurchase")
    public String makePurchase(Model model) throws VendingNoItemInventoryException, VendingInsufficientFundsException, VendingPersistenceException {

        if (productIndex != null && !productIndex.equals("")) {

            Product selectedProduct = service.getProductByIndex(productIndex - 1);
            SpendingMoney currentMoney = new SpendingMoney(enteredMoney);

            try {
                service.checkProductStock(selectedProduct);
                service.checkCustomerFunds(selectedProduct, currentMoney);
                selectedProduct.decrementProductQuantity();
                service.updateProduct(selectedProduct.getProductName(), selectedProduct);
                Change change = new Change(enteredMoney);
                returnedChange = service.buildChangeText(change);
                enteredMoney = null;
                productIndex = null;
                vendingMessages = "Thank you!!!";
            } catch (VendingNoItemInventoryException e) {
                vendingMessages = e.getMessage();
            } catch (VendingInsufficientFundsException e) {
                vendingMessages = e.getMessage();
            }
        } else {
            vendingMessages = "You must first select an item.";
            returnedChange = "";
        }

        return "redirect:/";
    }

}
