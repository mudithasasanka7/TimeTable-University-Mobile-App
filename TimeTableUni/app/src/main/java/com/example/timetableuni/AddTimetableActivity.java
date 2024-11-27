package com.example.timetableuni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ContentValues;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTimetableActivity extends AppCompatActivity {

    private EditText edtSubject, edtLecturer, edtVenue;
    private Spinner spinnerDay, spinnerTime;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timetable);
        // Initialize UI components
        edtSubject = findViewById(R.id.edtSubject);
        edtLecturer = findViewById(R.id.edtLecturer);
        edtVenue = findViewById(R.id.edtVenue);
        spinnerDay = findViewById(R.id.spinnerDay);
        spinnerTime = findViewById(R.id.spinnerTime);
        btnSave = findViewById(R.id.btnSave);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Save button click listener
        btnSave.setOnClickListener(v -> {
            String day = spinnerDay.getSelectedItem().toString();
            String time = spinnerTime.getSelectedItem().toString();
            String subject = edtSubject.getText().toString();
            String lecturer = edtLecturer.getText().toString();
            String venue = edtVenue.getText().toString();

            if (subject.isEmpty() || lecturer.isEmpty() || venue.isEmpty()) {
                Toast.makeText(AddTimetableActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                saveTimetable(day, time, subject, lecturer, venue);
            }
        });
    }

    // Method to save timetable data to the database
    private void saveTimetable(String day, String time, String subject, String lecturer, String venue) {
        ContentValues values = new ContentValues();
        values.put("day", day);
        values.put("time", time);
        values.put("subject", subject);
        values.put("lecturer", lecturer);
        values.put("venue", venue);

        long result = databaseHelper.insertTimetable(values);
        if (result != -1) {
            Toast.makeText(AddTimetableActivity.this, "Timetable added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after saving
        } else {
            Toast.makeText(AddTimetableActivity.this, "Error saving timetable", Toast.LENGTH_SHORT).show();
        }
    }
}