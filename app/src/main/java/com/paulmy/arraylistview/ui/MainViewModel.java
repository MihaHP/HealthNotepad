package com.paulmy.arraylistview.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.paulmy.arraylistview.data.Product;
import com.paulmy.arraylistview.data.ProductDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private final ProductDatabase database;
    private final MutableLiveData <List<Product>> products = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = ProductDatabase.newInstance(application);
    }
    public LiveData<List<Product>> getProducts() {
        return products;
    }


    public void refreshList(){
        Disposable disposable= database.productDao()
                .getProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> productDB) throws Throwable {
                        products.setValue(productDB);
                    }
                });
        compositeDisposable.add(disposable);
    }
    public void remove(Product product){
        Disposable disposable = database.productDao()
                .remove(product.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        refreshList();
                        Log.d("MainViewModel", "Remove product "+ product.getId());
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
