package com.example.convertercurrency.view;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class AdapterList extends RecyclerView.Adapter<AdapterList.CommonListViewHolder>{


    private IOnListItemClick adapterListener;

    public interface IOnListItemClick {
        void onClick(int pos);
    }

    interface IOnViewHolderClick {
        void onListItemClick(View v, int position);
    }

    IOnViewHolderClick viewHolderClickCallback = new IOnViewHolderClick() {
        @Override
        public void onListItemClick(View v, int position) {
            adapterListener.onClick(position);
        }
    };

    AdapterList(IOnListItemClick callback) {
        adapterListener = callback;
    }

    abstract static class CommonListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int position;
        private IOnViewHolderClick clickListener;

        CommonListViewHolder(View v, IOnViewHolderClick listener) {
            super(v);
            clickListener = listener;
            findControls(v);
            v.setOnClickListener(this);
        }

        abstract void findControls (View v);

        @Override
        public void onClick(View v) {
            clickListener.onListItemClick(v, position);
        }
    }
}
