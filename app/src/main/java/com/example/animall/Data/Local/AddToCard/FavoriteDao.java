package com.example.animall.Data.Local.AddToCard;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.animall.Data.Remote.Models.Product.Product;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Product note);

    @Update
    void update(Product note);

    @Delete
    void delete(Product note);

    @Query("DELETE from product_table")
    void deleteAllNotes();

    @Query("select * from product_table")
    LiveData<List<Product>> getAllNotes();

    @Query("select * from product_table where id = :id")
    LiveData<Product> getNote(Long id);


}
