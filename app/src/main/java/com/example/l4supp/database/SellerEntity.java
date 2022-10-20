package com.example.l4supp.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SellerEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String name;
    private String product;

    public SellerEntity(int userId, String name, String product) {
        this.userId = userId;
        this.name = name;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}


