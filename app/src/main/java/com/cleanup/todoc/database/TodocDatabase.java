package com.cleanup.todoc.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;

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
                            .addCallback(prePopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    private static Callback prePopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
              //   new PopulateDbAsync(INSTANCE).execute();

                ContentValues contentValues = new ContentValues();
               // contentValues.put("id", 1);
                contentValues.put("projectId", 1L);
                contentValues.put("name", "Passer la serpillère");
                contentValues.put("creationTimestamp", new Date().getTime());

                db.insert("task_table", OnConflictStrategy.IGNORE, contentValues);

                ContentValues projectValues1 = new ContentValues();
                projectValues1.put("id", 1L);
                projectValues1.put("name", "Project Tartampion");
                projectValues1.put("color", 0xFFEADAD1);

                ContentValues projectValues2 = new ContentValues();
                projectValues2.put("id", 2L);
                projectValues2.put("name", "Project Lucidia");
                projectValues2.put("color", 0xFFB4CDBA);

                ContentValues projectValues3 = new ContentValues();
                projectValues3.put("id", 3L);
                projectValues3.put("name", "Project Circus");
                projectValues3.put("color", 0xFFA3CED2);

                db.insert("project_table", OnConflictStrategy.IGNORE, projectValues1);
                db.insert("project_table", OnConflictStrategy.IGNORE, projectValues2);
                db.insert("project_table", OnConflictStrategy.IGNORE, projectValues3);
            }
        };
    }

  /*  private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final TaskDao mDao;

        PopulateDbAsync(TodocDatabase db){
            mDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            if(mDao.getAnyTask().length < 1){
                for(int i =0; i < tas)
            }
            return null;
        }
    }*/
}
