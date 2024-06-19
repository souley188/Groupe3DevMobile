package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Spinner statusSpinner;
    private Button updateButton;
    private DatabaseHelper db;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        db = new DatabaseHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        statusSpinner = findViewById(R.id.statusSpinner);
        updateButton = findViewById(R.id.updateButton);

        int taskId = getIntent().getIntExtra("task_id", -1);
        task = db.getTask(taskId);

        titleEditText.setText(task.getTitle());
        descriptionEditText.setText(task.getDescription());
        // Set the status spinner selection based on the task's status
        // Assuming statuses are "To Do", "In Progress", "Done"
        statusSpinner.setSelection(getStatusIndex(task.getStatus()));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setTitle(titleEditText.getText().toString());
                task.setDescription(descriptionEditText.getText().toString());
                task.setStatus(statusSpinner.getSelectedItem().toString());

                db.updateTask(task);
                finish();
            }
        });
    }

    private int getStatusIndex(String status) {
        switch (status) {
            case "To Do":
                return 0;
            case "In Progress":
                return 1;
            case "Done":
                return 2;
            default:
                return 0;
        }
    }
}
