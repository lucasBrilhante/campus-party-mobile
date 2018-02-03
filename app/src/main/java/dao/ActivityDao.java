package dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import model.Activity;

@Dao
public interface ActivityDao {
    @Query("SELECT * FROM activity")
    List<Activity> getAll();

    @Insert
    void insertAll(Activity... activities);

    @Query("SELECT count(*) FROM activity act where act.name = :name")
    int verify(String name);

    @Delete
    void delete(Activity... activities);
}
