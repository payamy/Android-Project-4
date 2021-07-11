package com.example.rhodium.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.rhodium.data.entity.ParameterDao;
import com.example.rhodium.data.model.Parameter;

@Database(entities = {Parameter.class}, version = 8, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase{

    public abstract ParameterDao parameterDao();


}
