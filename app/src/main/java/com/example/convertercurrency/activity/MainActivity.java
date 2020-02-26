package com.example.convertercurrency.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.convertercurrency.R;
import com.example.convertercurrency.presenter.Converter;
import com.example.convertercurrency.view.ConverterView;
import com.example.convertercurrency.view.CurrencyView;
import com.example.convertercurrency.view.ExchangeList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExchangeList.IOnDialogListItemClick, ConverterView{

    private EditText firstSum;
    private EditText secondSum;
    private TextView sourceCharCode;
    private TextView destCharCode;
    private Button buttonFirstSum;
    private Button buttonSecondSum;

    private Converter converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Конвертер валют");

        converter = new Converter();

        firstSum = (EditText) findViewById(R.id.source_sum);
        firstSum.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String txt = firstSum.getText().toString();

                    if (!txt.equals("")) {
                        converter.onFirstSumClick(Double.parseDouble(txt));
                    }
                    return true;
                }
                return false;
            }
        });
        firstSum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    firstSum.setText("");
                }
            }
        });
        sourceCharCode =  findViewById(R.id.source_char_code_text);


        destCharCode =  findViewById(R.id.dest_char_code_text);
        secondSum = (EditText) findViewById(R.id.dest_sum);
        secondSum.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String txt = secondSum.getText().toString();

                    if (!txt.equals("")) {
                        converter.onSecondSumClick(Double.parseDouble(txt));
                    }
                    return true;
                }
                return false;
            }
        });
        secondSum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    secondSum.setText("");
                }
            }
        });

        buttonFirstSum = (Button) findViewById(R.id.source_currency_place);
        buttonSecondSum = (Button) findViewById(R.id.dest_currency_place);

        buttonFirstSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                converter.onFirstCurrencyClick();
            }
        });
        buttonSecondSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                converter.onSecondCurrencyClick();
            }
        });

    }

    public void setTitle(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.NORMAL);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.black));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        converter.bindView(this);
        converter.onInit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        converter.onStop();
        converter.unbindView();
    }

    @Override
    public void dialogListClickCallback(int pos) {
        converter.onListItemClick(pos);
    }

    @Override
    public void setFirstSum(double sum) {
        firstSum.setText(String.format("%.1f", sum));
    }

    @Override
    public void setFirstCurrency(String charCode, String name) {
        sourceCharCode.setText(charCode);
    }

    @Override
    public void setSecondCurrency(String charCode, String name) {
        destCharCode.setText(charCode);
    }

    @Override
    public void setResultSum(double sum) {
        secondSum.setText(String.format("%.1f", sum));

    }

    @Override
    public void setResultSummSource(double summ) {
        firstSum.setText(String.format("%.1f", summ));
    }

    private final static String CURRENCY_LIST = "list";
    private final static String FIRST_POS = "first_pos";
    private final static String SECOND_POS = "second_pos";
    private final static String FIRST_SUM = "first_sum";
    private final static String SECOND_SUM = "second_sum";
    public static final String DIALOG_TAG = "dialog";

    @Override
    public void showCurrencyList(ArrayList<CurrencyView> currencyList) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = FragmentFabric.newDialogInstance(currencyList);
        dialogFragment.show(ft, DIALOG_TAG);
    }

    @Override
    public int loadFirstPos() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getInt(FIRST_POS, 0);
    }

    @Override
    public int loadSecondPos() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getInt(SECOND_POS, 0);
    }

    @Override
    public double loadFirstSum() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return Double.longBitsToDouble(sp.getLong(FIRST_SUM, 1));
    }

    @Override
    public double loadSecondSum() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return Double.longBitsToDouble(sp.getLong(SECOND_SUM, 0));
    }

    @Override
    public void savePrefs(String xml, int fPos, int sPos, double firstSum, double secondSum) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(FIRST_POS, fPos);
        editor.putInt(SECOND_POS, sPos);
        editor.putLong(FIRST_SUM, Double.doubleToRawLongBits(firstSum));
        editor.putLong(SECOND_SUM, Double.doubleToRawLongBits(secondSum));
        editor.putString(CURRENCY_LIST, xml);
        editor.apply();
    }

    @Override
    public void enableControls() {
        firstSum.setEnabled(true);
        sourceCharCode.setEnabled(true);
        destCharCode.setEnabled(true);
        secondSum.setEnabled(true);
    }

    @Override
    public String loadXml() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getString(CURRENCY_LIST, "");
    }


    public static class FragmentFabric extends Fragment {
        private static DialogFragment dialogFragmentInstance;
        public static final String CURRENCY_LIST = "currency_list";

        public static DialogFragment newDialogInstance(ArrayList<CurrencyView> currencyList) {
            dialogFragmentInstance = new ExchangeList();
            Bundle args = new Bundle();
            args.putParcelableArrayList(CURRENCY_LIST, currencyList);
            dialogFragmentInstance.setArguments(args);
            return dialogFragmentInstance;
        }
    }

    @Override
    public boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void cancel(View view) {
        finish();
        startActivity(new Intent("com.example.convertercurrency.activity.MainActivity"));
    }
}
