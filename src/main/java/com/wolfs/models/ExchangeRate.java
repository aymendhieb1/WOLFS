package com.wolfs.models;

public class ExchangeRate {
    private final String currency;
    private final double rate;

    public ExchangeRate(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }};
