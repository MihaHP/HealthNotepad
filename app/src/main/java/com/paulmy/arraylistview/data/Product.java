package com.paulmy.arraylistview.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "products")
public class Product implements Serializable {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "pulse")
    public String pulse;
    @ColumnInfo(name = "presup")
    public String presup;
    @ColumnInfo(name = "presdown")
    public String presdown;
    @ColumnInfo(name = "drt")
    public String drt;
    @ColumnInfo(name = "temp")
    public String temp;
    @ColumnInfo(name = "weig")
    public String weig;
    @ColumnInfo(name = "description")
    private final String description;

    public Product(String title,String pulse,String presup,String presdown,String drt,String temp,String weig,String description) {
        this(0,title,pulse,presup,presdown,drt,temp,weig,description);
    }

    @Ignore
    public Product(int id, String title, String pulse, String presup, String presdown, String drt, String temp, String weig, String description) {
        this.id = id;
        this.title = title;
        this.pulse = pulse;
        this.presup = presup;
        this.presdown = presdown;
        this.drt = drt;
        this.temp = temp;
        this.weig = weig;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public String getPulse() {return pulse;}
    public String getPresup() {return presup;}
    public String getPresdown() {return presdown;}
    public String getDrt() {return drt;}
    public String getTemp() {return temp;}
    public String getWeig() {return weig;}
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(title, product.title) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}

