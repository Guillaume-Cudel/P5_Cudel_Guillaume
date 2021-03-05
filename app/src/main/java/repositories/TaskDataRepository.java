package repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;
    private LiveData<List<Task>> mAllTasks;

    public TaskDataRepository(Application application){
        TodocDatabase db = TodocDatabase.getInstance(application);
        taskDao = db.taskDao();
        mAllTasks = taskDao.getAllTasks();
    }


    // --- GET ---

    public LiveData<List<Task>> getTasksInProject(long projectId){ return this.taskDao.getTasksWithProjectId(projectId); }

    public LiveData<List<Task>> getOneTask(long taskId){ return this.taskDao.getOneTask(taskId);}

    public LiveData<List<Task>> getAllTasks(){
        return mAllTasks;}

    // --- CREATE ---

   // public void insertTask(Task task){ taskDao.insertTask(task); }
   public void insertTask(Task task){
        new insertAsyncTask(taskDao).execute(task);
   }

    // --- DELETE ---
    //public void deleteTask(Task task){ taskDao.deleteTask(task); }
    public void deleteTask(Task task){
        new deleteTaskAsyncTask(taskDao).execute(task);
    }

    // --- UPDATE ---
   // public void update(Task task){ taskDao.updateTask(task); }
    public void update(Task task){
        new updateTaskAsyncTask(taskDao).execute(task);
         }


         private static class insertAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao mAsyncTaskdao;

        insertAsyncTask(TaskDao dao){
            mAsyncTaskdao = dao;
        }

             @Override
             protected Void doInBackground(final Task... tasks) {
            mAsyncTaskdao.insertTask(tasks[0]);
                 return null;
             }
         }


    private static class deleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        deleteTaskAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.deleteTask(params[0]);
            return null;
        }
    }

    private static class updateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        updateTaskAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.updateTask(params[0]);
            return null;
        }
    }



}
