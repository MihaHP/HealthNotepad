package com.paulmy.arraylistview.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.paulmy.arraylistview.data.Product;
import com.paulmy.arraylistview.data.ProductDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNewProductViewModel extends AndroidViewModel {

    private final ProductDatabase database;

    public LiveData<Boolean> getShouldClose() {
        return shouldClose;
    }

    private final MutableLiveData<Boolean> shouldClose = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AddNewProductViewModel(@NonNull Application application) {
        super(application);
        database = ProductDatabase.newInstance(application);
    }

    public void saveProduct(Product product) {
        Disposable disposable = database.productDao().add(product)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        shouldClose.setValue(true);
                    }
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
