package com.example.convertercurrency.model;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class Utils {

    private static ExchangeRates rates;

    private static void addRuble(){
        if (rates == null) {
            rates = new ExchangeRates();
        }
        rates.getExchangeRates().add(new Currency("R09999", "643", "RUB", 1, "Российский рубль", "1"));
        Collections.sort(rates.getExchangeRates(), new Comparator<Currency>() {
            @Override
            public int compare(Currency o1, Currency o2) {
                return o1.getCharCode().compareTo(o2.getCharCode());
            }
        });
    }

    public static void genListFromXml(ModelHolder holder){
        Reader reader = new StringReader(holder.getXml());
        Persister serializer = new Persister();
        try
        {
            rates = serializer.read(ExchangeRates.class, reader, false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            rates = new ExchangeRates();
        }
        addRuble();

        holder.setCurrencyList(rates.getExchangeRates());
    }

    public static double getNumValue(Currency currency){
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number = null;
        try {
            number = format.parse(currency.getValue());
        } catch (ParseException e) {
            e.printStackTrace();
            number = 0;
        }
        return number.doubleValue();
    }

    public static void convertSourceToDest(ModelHolder holder){
        int source = holder.getFirstCell();
        int dest = holder.getSecondCell();

        holder.setSecondSum(holder.getFirstSum() *
                getNumValue(holder.getCurrencyList().get(source)) /
                getNumValue(holder.getCurrencyList().get(dest)));
    }

    public static void convertDestToSource(ModelHolder holder){
        int source = holder.getSecondCell();
        int dest = holder.getFirstCell();

        holder.setSecondSum(holder.getFirstSum() *
                getNumValue(holder.getCurrencyList().get(source)) /
                getNumValue(holder.getCurrencyList().get(dest)));
    }
}
