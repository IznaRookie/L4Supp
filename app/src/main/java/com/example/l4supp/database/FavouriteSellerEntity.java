package com.example.l4supp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavouriteSellerEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int sellerId;

    public FavouriteSellerEntity(int userId, int sellerId) {
        this.userId = userId;
        this.sellerId = sellerId;
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

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}
