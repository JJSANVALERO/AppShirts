package com.svalero.appCamisetas.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.appCamisetas.domain.Shirt;

import java.util.List;

@Dao
public interface ShirtDao {

    @Query("SELECT * FROM shirt")
    List<Shirt> getAll();

    @Query("SELECT * FROM shirt WHERE model = :model")
    Shirt getByModel(String model);

    @Query("DELETE FROM shirt WHERE model = :model")
    void deleteByModel(String model);

    @Insert
    void insert(Shirt shirt);

    @Delete
    void delete(Shirt shirt);

    @Update
    void update (Shirt shirt);
}
