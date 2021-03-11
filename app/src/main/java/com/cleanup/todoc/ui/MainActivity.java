package com.cleanup.todoc.ui;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//import injections.Injection;
//import injections.ViewModelFactory;
import todoList.TaskViewModel;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    private final Project[] allProjects = Project.getAllProjects();
    @NonNull
    private final ArrayList<Task> tasks = new ArrayList<>();
    private TasksAdapter adapter = new TasksAdapter(tasks, this);
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;
    @Nullable
    public AlertDialog dialog = null;
    @Nullable
    private EditText dialogEditText = null;
    @Nullable
    private Spinner dialogSpinner = null;
    @NonNull
    private RecyclerView listTasks;
    @NonNull
    private TextView lblNoTasks;

    private TaskViewModel taskViewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        // configure RecyclerView & ViewModel
        this.configureRecyclerView();
        this.configureViewModel();

        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listTasks.setAdapter(adapter);

        findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }

        updateTasks();

        return super.onOptionsItemSelected(item);
    }


    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {

            String taskName = dialogEditText.getText().toString();

             Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            else if (taskProject != null) {

               Task task = new Task(generateId(), taskProject.getId(), taskName, new Date().getTime());
               /* Utilisation methode terminee
                addTask(task);*/
                // ajoute la tache dans le viewModel
                taskViewModel.insert(task);
                dialogInterface.dismiss();
            }
            else{
                dialogInterface.dismiss();
            }
        }
        else {
            dialogInterface.dismiss();
        }
    }

    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();
        dialog.show();
        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

   // Utilisation methode terminee
    /*private void addTask(@NonNull Task task) {
        tasks.add(task);
        updateTasks();
    }*/

    private void updateTasks() {
        if (tasks.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasks, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new Task.TaskOldComparator());
                    break;

            }
            adapter.setTasks(tasks);
        }
    }

    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogEditText = null;
                dialogSpinner = null;
                dialog = null;
            }
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClick(dialog);
                    }
                });
            }
        });

        return dialog;
    }

    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    //@Override
    public void deleteTask(Task task) {
        this.taskViewModel.deleteTask(task);
    }


    @Override
    public void onClickDeleteTask(int position) {
        this.deleteTask(this.adapter.getTaskAtPosition(position));
    }

    private enum SortMethod {
        ALPHABETICAL,
        ALPHABETICAL_INVERTED,
        RECENT_FIRST,
        OLD_FIRST,
        NONE
    }

    private long generateId(){
        long id = 0;
        for(int i = 0; i < tasks.size(); i++){
            Task getTask = tasks.get(i);
            if (getTask.getId() == id)
                id++;
        }
        return id;
    }

    private void configureViewModel(){
       taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
       taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
           @Override
           public void onChanged(@Nullable List<Task> tasks) {
              MainActivity.this.tasks.clear();
              MainActivity.this.tasks.addAll(tasks);
               updateTasks();
           }
       });
    }

    private void configureRecyclerView(){
        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.list_tasks);
        this.adapter = new TasksAdapter(tasks, this );
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }




}
