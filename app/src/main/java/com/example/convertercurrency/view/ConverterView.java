package com.example.convertercurrency.view;

import java.util.ArrayList;

public interface ConverterView {

    int loadFirstPos();

    int loadSecondPos();

    double loadFirstSum();

    double loadSecondSum();

    void setFirstSum(double sum);

    void setFirstCurrency(String charCode, String name);

    void setSecondCurrency(String charCode, String name);

    void setResultSum(double sum);

    void showCurrencyList(ArrayList<CurrencyView> currencyList);

    void setResultSummSource(double summ);

    void savePrefs(String xml, int fPos, int sPos, double firstSum, double secondSum);

    String loadXml();

    boolean checkNetwork();

    void enableControls();

}
