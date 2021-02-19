package com.cleanup.todoc.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task_table WHERE projectId = :projectId")
    LiveData<List<Task>> getTasksWithProjectId(long projectId);

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    LiveData<Task> getTask(long taskId);

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    void updateTask(Task... task);

    @Delete
    void deleteTask(Task task);

   /* @Query("DELETE FROM task_table WHERE id = :taskId")
    void deleteTask(long taskId);*/



}
