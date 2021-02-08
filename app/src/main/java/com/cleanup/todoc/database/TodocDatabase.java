package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Date;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // ----- SINGLETON-----
    public static volatile TodocDatabase INSTANCE;

    // ------- DAO -------
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // ---- INSTANCE ----
    public static TodocDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (TodocDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodocDatabase.class, "MyDatabase.db")
                            .addCallback(pretaskDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback pretaskDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("name", "Passer la serpillère");
                contentValues.put("creationTimestamp", 1253562405);

                db.insert("Task", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
