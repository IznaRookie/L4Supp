package com.example.l4supp.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SellerDao {

    @Query("SELECT * FROM SellerEntity")
    List<SellerEntity> getAll();

    @Query("SELECT * FROM SellerEntity WHERE id = :id")
    SellerEntity getById(int id);

    @Query("SELECT * FROM SellerEntity WHERE userId = :userId")
    SellerEntity getByUserId(int userId);

    @Insert
    void insert(SellerEntity sellerEntity);

    @Update
    void update(SellerEntity sellerEntity);

    @Delete
    void delete(SellerEntity sellerEntity);

    @Query("DELETE FROM SellerEntity WHERE id = :id")
    void deleteById(int id);
}
