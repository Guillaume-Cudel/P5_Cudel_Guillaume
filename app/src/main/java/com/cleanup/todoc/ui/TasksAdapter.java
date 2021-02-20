package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    @NonNull
    private List<Task> tasks;

    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    TasksAdapter(@NonNull final List<Task> tasks, @NonNull final DeleteTaskListener deleteTaskListener) {
        this.tasks = tasks;
        this.deleteTaskListener = deleteTaskListener;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        if (tasks != null) {
            Task current = tasks.get(position);
            taskViewHolder.bind(tasks.get(position));
        }else {
            taskViewHolder.imgProject.setVisibility(View.INVISIBLE);
            taskViewHolder.lblProjectName.setText("");
        }
    }

    void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (tasks != null)
        return tasks.size();
        else return 0;
    }

    public interface DeleteTaskListener {
        void deleteTask(Task task);
    }

    // Ajout
    public Task getTaskAtPosition(int position){
        return this.tasks.get(position);
    }

   /* public void updateData(List<Task> tasks){
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }*/

    //-------------





    class TaskViewHolder extends RecyclerView.ViewHolder {

        // The circle icon showing the color of the project
        private final AppCompatImageView imgProject;
        // The TextView displaying the name of the task
        private final TextView lblTaskName;
       // The TextView displaying the name of the project
        private final TextView lblProjectName;

        // The delete icon
        private final AppCompatImageView imgDelete;

        private final DeleteTaskListener deleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.deleteTaskListener = deleteTaskListener;

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Object tag = view.getTag();
                    if (tag instanceof Task) {
                        TaskViewHolder.this.deleteTaskListener.deleteTask((Task) tag);
                    }
                }
            });
        }

        /**
         * Binds a task to the item view.
         *
         * @param task the task to bind in the item view
         */
        void bind(Task task) {
            lblTaskName.setText(task.getName());
            imgDelete.setTag(task);

            final Project taskProject = task.getProject();
            //if (taskProject != null) {
                imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                lblProjectName.setText(taskProject.getName());
           /* } else {
                imgProject.setVisibility(View.INVISIBLE);
                lblProjectName.setText("");
            }*/

        }
    }
}
