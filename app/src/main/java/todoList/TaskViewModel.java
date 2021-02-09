package todoList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;

import repositories.ProjectDataRepository;
import repositories.TaskDataRepository;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init(long projectId){
        if(this.currentProject != null){
            return;
        }
        currentProject = projectDataSource.getProject(projectId);
    }

    // --------------
    // FOR PROJECT
    // --------------

    public LiveData<Project> getProject(long projectId){
        return this.currentProject;
    }

    //-----------
    // FOR TASK
    //-----------

    public LiveData<List<Task>> getTasks(long projectId){
        return taskDataSource.getTasks(projectId);
    }

    public void createTask(Task task){
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId){
        executor.execute(() -> {
            taskDataSource.deleteTask(taskId);
        });
    }

    public void updateTask(Task task){
        executor.execute(() -> {
            taskDataSource.updateTask(task);
        });
    }


}
