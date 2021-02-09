package injections;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;

import java.lang.reflect.Executable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import repositories.ProjectDataRepository;
import repositories.TaskDataRepository;
import todoList.TaskViewModel;

public class Injection {

    public static TaskDataRepository providedTaskDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static ProjectDataRepository providedProjectDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelfactory(Context context){
        TaskDataRepository dataSourceTask = providedTaskDataSource(context);
        ProjectDataRepository dataSourceProject = providedProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }

}
