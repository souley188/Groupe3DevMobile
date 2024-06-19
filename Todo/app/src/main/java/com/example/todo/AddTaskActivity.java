package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Spinner statusSpinner;
    private Button saveButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        db = new DatabaseHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        statusSpinner = findViewById(R.id.statusSpinner);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String status = statusSpinner.getSelectedItem().toString();

                Task task = new Task(title, description, status);
                db.addTask(task);
                finish();
            }
        });
    }
}
