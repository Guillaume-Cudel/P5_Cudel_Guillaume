package com.cleanup.todoc.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table WHERE projectId = :projectId")
    LiveData<List<Task>> getTasksWithProjectId(long projectId);

    @Query("SELECT * FROM task_table WHERE id = :id")
    LiveData<List<Task>> getOneTask(long id);

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    void updateTask(Task... task);

    @Delete
    void deleteTask(Task task);

   



}
