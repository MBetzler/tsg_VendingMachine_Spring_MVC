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
public class Change {

    private final String[] coins = {
        Money.QUARTER.getPrint(), Money.DIME.getPrint(), Money.NICKEL.getPrint(), Money.PENNY.getPrint()
    };
    private int coinQuantities[] = new int[4];

    public Change(BigDecimal totalMoney) {
        BigDecimal quarters[] = totalMoney.divideAndRemainder(Money.QUARTER.getCurrencyValue());
        this.coinQuantities[0] = quarters[0].intValue();

        BigDecimal dimes[] = quarters[1].divideAndRemainder(Money.DIME.getCurrencyValue());
        this.coinQuantities[1] = dimes[0].intValue();

        BigDecimal nickels[] = dimes[1].divideAndRemainder(Money.NICKEL.getCurrencyValue());
        this.coinQuantities[2] = nickels[0].intValue();

        this.coinQuantities[3] = nickels[1].divide(Money.PENNY.getCurrencyValue()).intValue();
    }

    public String[] getCoins() {
        return this.coins;
    }

    public int[] getCoinQuantities() {
        return this.coinQuantities;
    }
}
