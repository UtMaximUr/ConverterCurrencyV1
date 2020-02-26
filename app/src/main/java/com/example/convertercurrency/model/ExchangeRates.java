package com.example.convertercurrency.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "ValCurs")
public class ExchangeRates {
    public ExchangeRates() {
        exchangeRates = new ArrayList<>();
    }

    private String date;
    private String name;
    private List<Currency> exchangeRates;

    @Attribute(name = "Date")
    public void setDate(String date) {
        this.date = date;
    }

    @Attribute(name = "Date")
    public String getDate() {
        return date;
    }

    @Attribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @Attribute(name = "name")
    public String getName() {
        return name;
    }

    @ElementList(inline = true, name = "Valute")
    public void setExchangeRates(List<Currency> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    @ElementList(inline = true, name = "Valute")
    public List<Currency> getExchangeRates() {
        return exchangeRates;
    }
}
