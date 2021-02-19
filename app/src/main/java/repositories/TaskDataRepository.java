package repositories;

import android.arch.lifecycle.LiveData;
import android.content.ClipData;

import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) { this.taskDao = taskDao; }

    // --- GET ---

    public LiveData<List<Task>> getTasks(long projectId){ return this.taskDao.getTasksWithProjectId(projectId); }

    // --- CREATE ---

    public void createTask(Task task){ taskDao.insertTask(task); }

    // --- DELETE ---
    public void deleteTask(Task task){ taskDao.deleteTask(task); }

    // --- UPDATE ---
    public void updateTask(Task task){ taskDao.updateTask(task); }
}
