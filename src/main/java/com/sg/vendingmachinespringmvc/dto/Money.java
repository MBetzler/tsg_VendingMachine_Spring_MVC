/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachinespringmvc.dto;

import java.math.BigDecimal;

/**
 *
 * @author betzler
 */
public enum Money {
    DOLLAR(new BigDecimal("1.00"), "one dollar bills"),
    QUARTER(new BigDecimal("0.25"), "quarters"),
    DIME(new BigDecimal("0.10"), "dimes"),
    NICKEL(new BigDecimal("0.05"), "nickels"),
    PENNY(new BigDecimal("0.01"), "pennies");

    private BigDecimal valueCurrency;
    private String valuePrint;

    Money(BigDecimal value, String print) {
        this.valueCurrency = value;
        this.valuePrint = print;
    }

    public BigDecimal getCurrencyValue() {
        return valueCurrency;
    }

    public String getPrint() {
        return valuePrint;
    }
}
