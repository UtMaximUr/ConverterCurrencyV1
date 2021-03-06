package com.example.convertercurrency.presenter;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class CommonPresenter<M, V> {
    protected M model;
    private WeakReference<V> view;

    public void setModel(M model) {
        this.model = model;
    }

    public void bindView(@NonNull V view) {
        this.view = new WeakReference<>(view);
    }

    public void unbindView() {
        this.view = null;
    }

    protected V view() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }
}