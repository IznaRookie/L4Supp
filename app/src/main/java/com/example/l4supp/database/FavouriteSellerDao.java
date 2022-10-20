package com.example.l4supp.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavouriteSellerDao {

    @Query("SELECT * FROM FavouriteSellerEntity")
    List<FavouriteSellerEntity> getAll();

    @Query("SELECT * FROM FavouriteSellerEntity WHERE userId = :userId")
    List<FavouriteSellerEntity> getAllByUserId(int userId);

    @Query("SELECT * FROM FavouriteSellerEntity WHERE id = :id")
    FavouriteSellerEntity getById(int id);

    @Query("SELECT * FROM FavouriteSellerEntity WHERE sellerId = :sellerId AND userId = :userId")
    FavouriteSellerEntity getBySellerAndUserId(int sellerId, int userId);

    @Insert
    void insert(FavouriteSellerEntity sellerEntity);

    @Update
    void update(FavouriteSellerEntity sellerEntity);

    @Delete
    void delete(FavouriteSellerEntity sellerEntity);

    @Query("DELETE FROM FavouriteSellerEntity WHERE id = :id")
    void deleteById(int id);
}
