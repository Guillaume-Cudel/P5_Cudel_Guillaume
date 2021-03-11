package com.cleanup.todoc.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project project);

    @Query("SELECT * FROM project_table WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);

}
