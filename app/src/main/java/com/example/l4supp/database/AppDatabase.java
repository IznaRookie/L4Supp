package com.example.l4supp.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class, SellerEntity.class, FavouriteSellerEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract SellerDao sellerDao();
    public abstract FavouriteSellerDao favouriteSellerDao();
}
