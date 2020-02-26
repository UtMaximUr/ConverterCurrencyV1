package com.example.convertercurrency.view;

import android.os.Parcel;
import android.os.Parcelable;

public class CurrencyView implements Parcelable {
    private String charCode;
    private String name;
    private int nominal;

    public CurrencyView(String charCode, int nominal, String name) {
        this.charCode = charCode;
        this.name = name;
        this.nominal = nominal;
    }

    protected CurrencyView(Parcel in) {
        charCode = in.readString();
        name = in.readString();
        nominal = in.readInt();
    }

    public static final Creator<CurrencyView> CREATOR = new Creator<CurrencyView>() {
        @Override
        public CurrencyView createFromParcel(Parcel in) {
            return new CurrencyView(in);
        }

        @Override
        public CurrencyView[] newArray(int size) {
            return new CurrencyView[size];
        }
    };

    public String getCharCode() {
        return charCode;
    }

    public String getTxtName() {
        return name;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public void setTxtName(String name) {
        this.name = name;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(charCode);
        dest.writeString(name);
        dest.writeInt(nominal);
    }
}
