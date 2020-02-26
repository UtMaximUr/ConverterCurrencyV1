package com.example.convertercurrency.presenter;

import android.os.AsyncTask;

import com.example.convertercurrency.model.Currency;
import com.example.convertercurrency.model.ModelHolder;
import com.example.convertercurrency.model.Utils;
import com.example.convertercurrency.view.ConverterView;
import com.example.convertercurrency.view.CurrencyView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

public class Converter extends CommonPresenter<ModelHolder, ConverterView> {

    private final static String xmlUrl = "http://www.cbr.ru/scripts/XML_daily.asp";

    private class Downloader extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... txtUrl) {
            BufferedReader in;
            StringBuilder response;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(txtUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "windows-1251"));
                response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.append(inputLine);
                in.close();
            } catch (Exception e) {
                return e.toString();
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            model.setXml(result);
            Utils.genListFromXml(model);
            if (!(view() == null)) {
                view().enableControls();
                updateModel();
                updateControls();
            }
        }
    }
    public void onInit() {
        setModel(ModelHolder.getInstance());

        if (model.getXml().equals("")) {
            if (view().checkNetwork()) {
                final Downloader downloadTask = new Downloader();
                downloadTask.execute(xmlUrl);
            }
        } else {
            model.setXml(view().loadXml());
            Utils.genListFromXml(model);
            updateModel();
            updateControls();
            view().enableControls();
        }
    }

    public void updateModel() {
        model.setFirstSum(view().loadFirstSum());
        model.setSecondSum(view().loadSecondSum());
        model.setFirstCell(view().loadFirstPos());
        model.setSecondCell(view().loadSecondPos());
    }

    public void updateControls() {
        String currencyName = "";
        int nominal = model.getCurrencyList().get(model.getFirstCell()).getNominal();
        if (nominal != 1)
            currencyName += nominal + " ";
        currencyName += model.getCurrencyList().get(model.getFirstCell()).getName();

        view().setFirstCurrency(model.getCurrencyList().get(model.getFirstCell()).getCharCode(), currencyName);
        currencyName = "";
        nominal = model.getCurrencyList().get(model.getSecondCell()).getNominal();
        if (nominal != 1)
            currencyName += nominal + " ";
        currencyName += model.getCurrencyList().get(model.getSecondCell()).getName();
        view().setSecondCurrency(model.getCurrencyList().get(model.getSecondCell()).getCharCode(), currencyName);

        view().setFirstSum(model.getFirstSum());
        view().setResultSum(model.getSecondSum());
    }

    private final static int EMPTY = 0;
    private final static int FIRST = 1;
    private final static int SECOND = 2;

    public void onListItemClick(int pos) {
        switch (model.getDialogFlag()) {
            case FIRST:
                model.setFirstCell(pos);
                break;
            case SECOND:
                model.setSecondCell(pos);
                break;
        }
        model.setDialogFlag(EMPTY);
        Utils.convertSourceToDest(model);
        updateControls();
    }

    public void onFirstSumClick(double sum) {
        model.setFirstSum(sum);
        Utils.convertSourceToDest(model);
        view().setResultSum(model.getSecondSum());
    }

    public void onFirstCurrencyClick() {
        model.setDialogFlag(FIRST);
        view().showCurrencyList(getCurrencyViewList());
    }

    public void onSecondSumClick(double sum) {
        model.setFirstSum(sum);
        Utils.convertDestToSource(model);
        view().setResultSummSource(model.getSecondSum());
    }

    public void onSecondCurrencyClick() {
        model.setDialogFlag(SECOND);
        view().showCurrencyList(getCurrencyViewList());
    }

    public ArrayList<CurrencyView> getCurrencyViewList() {
        ArrayList<CurrencyView> viewList = new ArrayList<>();
        for (Currency c : model.getCurrencyList()) {
            CurrencyView cv = new CurrencyView(c.getCharCode(), c.getNominal(), c.getName());
            viewList.add(cv);
        }
        System.out.println(viewList);
        return viewList;
    }

    public void onStop() {
       view().savePrefs(model.getXml(), model.getFirstCell(), model.getSecondCell(), model.getFirstSum(), model.getSecondSum());
    }


}
