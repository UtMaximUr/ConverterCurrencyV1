package com.example.convertercurrency.model;

import java.util.List;

public class ModelHolder {
    private static final ModelHolder modelHolder = new ModelHolder();

    public static ModelHolder getInstance() {
        return modelHolder;
    }

    public ModelHolder() {
    }
    private String xml = "";
    private List<Currency> currencyList;
    private int firstCell;
    private int secondCell;
    private double firstSum;
    private double secondSum;
    private int dialogFlag = 0;

    public void setXml(String xml) {
        this.xml = xml;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public int getFirstCell() {
        return firstCell;
    }

    public int getSecondCell() {
        return secondCell;
    }

    public double getFirstSum() {
        return firstSum;
    }

    public double getSecondSum() {
        return secondSum;
    }

    public int getDialogFlag() {
        return dialogFlag;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public void setFirstCell(int firstCell) {
        this.firstCell = firstCell;
    }

    public void setSecondCell(int secondCell) {
        this.secondCell = secondCell;
    }

    public void setFirstSum(double firstSum) {
        this.firstSum = firstSum;
    }

    public void setSecondSum(double secondSum) {
        this.secondSum = secondSum;
    }

    public void setDialogFlag(int dialogFlag) {
        this.dialogFlag = dialogFlag;
    }

    public String getXml() {
        return xml;
    }
}
