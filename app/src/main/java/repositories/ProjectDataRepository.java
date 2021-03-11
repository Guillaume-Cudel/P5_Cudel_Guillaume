package repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(Application application){
        TodocDatabase db = TodocDatabase.getInstance(application);
        projectDao = db.projectDao();

    }

    //public ProjectDataRepository(ProjectDao projectDao) { this.projectDao = projectDao; }

    // --- GET PROJECT ---
    public LiveData<Project> getProject(long projectId) {
        return this.projectDao.getProject(projectId);
             }



    private static class insertAsyncTask extends AsyncTask<Project, Void, Void> {

        private ProjectDao mAsyncTaskdao;

        insertAsyncTask(ProjectDao dao){
            mAsyncTaskdao = dao;
        }

        @Override
        protected Void doInBackground(final Project... projects) {
            mAsyncTaskdao.insertProject(projects[0]);
            return null;
        }
    }

}
