package com.paulmy.arraylistview.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products ORDER BY title")
    Single<List<Product>> getProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(Product product);

    @Query("DELETE FROM products WHERE id =:id")
    Completable remove(int id);

    @Query("UPDATE products SET description =:description, weig =:weig, `temp` =:temp, drt =:drt, presdown =:presdown, presup =:presup, pulse =:pulse, title =:title WHERE id =:id")
    void update(String title,String pulse,String presup,String presdown,String drt,String temp,String weig,String description, int id);

}
