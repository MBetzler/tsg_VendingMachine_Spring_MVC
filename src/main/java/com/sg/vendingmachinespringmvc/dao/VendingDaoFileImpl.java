/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dao;

import com.sg.vendingmachinespringmvc.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author betzler
 */
@Component
public class VendingDaoFileImpl implements VendingDao {

    public static final String INVENTORY_FILE = "inventory.txt";
    public static final String DELIMITER = "::";

    private Map<String, Product> inventory = new HashMap<>();

    @Override
    public List<Product> getAllProducts() throws VendingPersistenceException {
        loadInventory();
        List<Product> productList = new ArrayList<>(inventory.values());

        Collator productCollator = Collator.getInstance();
        return productList.stream()
                .sorted((p1, p2) -> productCollator.compare(p1.getProductName(), p2.getProductName()))
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductByIndex(int index) throws VendingPersistenceException {
        return getAllProducts().get(index);
    }

    @Override
    public void updateProduct(String name, Product product) throws VendingPersistenceException {
        loadInventory();
        inventory.put(name, product);
        writeInventory();
    }

    private void loadInventory() throws VendingPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(getClass().getClassLoader().getResource(INVENTORY_FILE).getFile())));
        } catch (FileNotFoundException e) {
            throw new VendingPersistenceException(
                    "--> Could not load inventory data into memory.", e);
        }

        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            Product currentProduct = new Product(currentTokens[0]);
            //explicitly set scale in case prices are not entered with two decimal places
            currentProduct.setProductPrice(new BigDecimal(currentTokens[1]).setScale(2));
            currentProduct.setProductQuantity(Integer.parseInt(currentTokens[2]));

            inventory.put(currentProduct.getProductName(), currentProduct);
        }
        scanner.close();
    }

    private void writeInventory() throws VendingPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(getClass().getClassLoader().getResource(INVENTORY_FILE).getFile()));
        } catch (IOException e) {
            throw new VendingPersistenceException(
                    "--> Could not save inventory data.", e);
        }

        //instantiate new to ensure all changes are written out
        List<Product> productList = new ArrayList<>(inventory.values());

        for (Product currentProduct : productList) {
            out.println(currentProduct.getProductName() + DELIMITER
                    + currentProduct.getProductPrice() + DELIMITER
                    + currentProduct.getProductQuantity());
            out.flush();
        }

        out.close();
    }
}
