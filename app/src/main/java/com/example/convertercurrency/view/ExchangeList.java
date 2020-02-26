package com.example.convertercurrency.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.convertercurrency.R;
import com.example.convertercurrency.activity.MainActivity;

import java.util.ArrayList;

public class ExchangeList extends DialogFragment implements AdapterList.IOnListItemClick {

    private IOnDialogListItemClick eventCallback;


    public interface IOnDialogListItemClick {
        void dialogListClickCallback(int pos);
    }


    @Override
    public void onClick(int pos) {
        eventCallback.dialogListClickCallback(pos);
        dismiss();
    }

    public void cancelClick(){
        dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<CurrencyView> currencyViewList = getArguments().getParcelableArrayList(MainActivity.FragmentFabric.CURRENCY_LIST);
        AdapterList adapterList = new AdapterListAll(this, currencyViewList);
        View rootView = inflater.inflate(R.layout.exchange_list, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.entirelist_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterList);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        eventCallback = (IOnDialogListItemClick) context;
    }
}
