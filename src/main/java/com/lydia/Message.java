package com.lydia;

import java.math.BigDecimal;

public class Message {

    private String userId;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal amountSell;
    private BigDecimal amountBuy;
    private BigDecimal rate;
    private String timePlaced;
    private String originatingCountry;


    public String getUserId(){return this.userId;}
    public Message setUserId(String id){
        this.userId = id;
        return this;
    }

    public String getCurrencyFrom(){return this.currencyFrom;}
    public Message setCurrencyFrom(String curr){
        this.currencyFrom = curr;
        return this;
    }

    public String getCurrencyTo(){return this.currencyTo;}
    public Message setCurrencyTo(String curr){
        this.currencyTo = curr;
        return this;
    }

    public BigDecimal getAmountSell(){return this.amountSell;}
    public Message setAmountSell(BigDecimal amt){
        this.amountSell = amt;
        return this;
    }

    public BigDecimal getAmountBuy(){return this.amountBuy;}
    public Message setAmountBuy(BigDecimal amt){
        this.amountBuy = amt;
        return this;
    }

    public BigDecimal getRate(){return this.rate;}
    public Message setRate(BigDecimal rate){
        this.rate = rate;
        return this;
    }

    public String getTimePlaced(){return this.timePlaced;}
    public Message setTimePlaced(String time){
        this.timePlaced = time;
        return this;
    }

    public String getOriginatingCountry(){return this.originatingCountry;}
    public Message setOriginatingCountry(String country){
        this.originatingCountry = country;
        return this;
    }
}
