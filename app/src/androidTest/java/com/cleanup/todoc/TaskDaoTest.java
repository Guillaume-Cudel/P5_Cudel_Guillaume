package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private TodocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 1;
    private static long TASK_ID = 1;
    private static long TASK_ID2 = 2;

    // todo récupérer un projet du tableau
    private static Project[] ARRAY_PROJECT_DEMO = Project.getAllProjects();
    private static Project FIRST_PROJECT_DEMO = ARRAY_PROJECT_DEMO[0];
    private static Task TASK_DEMO = new Task(TASK_ID, FIRST_PROJECT_DEMO.getId(),"Laver les vitres", new Date().getTime());
   // private static Task TASK_DEMO2 = new Task(TASK_ID2, TASK_DEMO.getProject(),"Passer l'aspirateur", new Date().getTime());


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception{
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class).allowMainThreadQueries().build();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException{
        // BEFORE : Adding a new task
        this.database.projectDao().createProject(FIRST_PROJECT_DEMO);
        //TEST
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(FIRST_PROJECT_DEMO.getId()));
        assertTrue(project.getName().equals(FIRST_PROJECT_DEMO.getName()) && project.getId() == FIRST_PROJECT_DEMO.getId());
    }

    @After
    public void closeDb() throws  Exception{
        database.close();
    }

}
