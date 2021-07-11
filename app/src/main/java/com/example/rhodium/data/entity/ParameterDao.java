package com.example.rhodium.data.entity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rhodium.data.model.Parameter;

import java.util.List;

@Dao
public abstract class ParameterDao {

    @Query("SELECT * FROM Parameter")
    public  abstract  List<Parameter> getAll() ;

    @Query("SELECT * FROM Parameter WHERE cell_id = (:cell_id)")
    public abstract Parameter loadByID(int cell_id);

    @Query("SELECT * FROM Parameter WHERE cell_id = (:cell_id)")
    public abstract List<Parameter> loadByIDs(int cell_id);

    @Query("DELETE FROM Parameter")
    public  abstract  void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract  void insert(Parameter new_record);
}
