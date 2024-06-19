package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private FloatingActionButton fab;
    private DatabaseHelper db;
    private List<Task> taskList;
    private List<Task> filteredTaskList;
    private ArrayAdapter<Task> adapter;
    private Spinner statusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        taskListView = findViewById(R.id.taskListView);
        fab = findViewById(R.id.fab);
        statusSpinner = findViewById(R.id.status_spinner);

        taskList = db.getAllTasks();
        filteredTaskList = new ArrayList<>(taskList);

        adapter = new ArrayAdapter<>(this, R.layout.item_task, filteredTaskList);
        taskListView.setAdapter(adapter);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        taskListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Task task = filteredTaskList.get(i);
            Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
            intent.putExtra("task_id", task.getId());
            startActivity(intent);
        });

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedStatus = (String) parentView.getItemAtPosition(position);
                filterTasks(selectedStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private void filterTasks(String status) {
        if (status.equals("All")) {
            filteredTaskList.clear();
            filteredTaskList.addAll(taskList);
        } else {
            filteredTaskList.clear();
            filteredTaskList.addAll(taskList.stream()
                    .filter(task -> task.getStatus().equals(status))
                    .collect(Collectors.toList()));
        }
        adapter.notifyDataSetChanged();
    }
}