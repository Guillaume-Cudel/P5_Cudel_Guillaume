package todoList;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

import repositories.ProjectDataRepository;
import repositories.TaskDataRepository;

public class TaskViewModel extends AndroidViewModel {

    private TaskDataRepository taskDataSource;
    private ProjectDataRepository projectDataSource;

    public TaskViewModel(Application application){
        super(application);
        taskDataSource = new TaskDataRepository(application);
        projectDataSource = new ProjectDataRepository(application);
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDataSource.getAllTasks();
    }

    public LiveData<List<Task>> getTasksInProject(long projectId){
        return taskDataSource.getTasksInProject(projectId);
    }

    public LiveData<List<Task>> getOneTask(long taskId){
        return taskDataSource.getOneTask(taskId);
    }

    public void insert(Task task){ taskDataSource.insertTask(task);}

    public void deleteTask(Task task){ taskDataSource.deleteTask(task); }

    public void update(Task task){ taskDataSource.update(task);}



}
