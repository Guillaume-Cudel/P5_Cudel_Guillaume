package repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) { this.projectDao = projectDao; }

    // --- GET USER ---
    public LiveData<Project> getProject(long projectId) {
        return this.projectDao.getProject(projectId);
             }

}
