package com.example.timetableuni;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimetableAdapter adapter;
    private ArrayList<TimetableModel> timetableList;
    private TextView noTimetableMessage;
    private FloatingActionButton fabAddTimetable;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        recyclerView = findViewById(R.id.recyclerView);
        noTimetableMessage = findViewById(R.id.noTimetableMessage);
        fabAddTimetable = findViewById(R.id.fabAddTimetable);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        timetableList = new ArrayList<>();
        adapter = new TimetableAdapter(this, timetableList, new TimetableAdapter.OnTimetableItemClickListener() {
            @Override
            public void onEditClick(TimetableModel timetableModel) {
                // Start Edit Activity with selected timetable
                Intent intent = new Intent(MainActivity.this, AddTimetableActivity.class);
                intent.putExtra("timetable_id", timetableModel.getId()); // Pass ID for editing
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int id) {
                // Delete timetable from the database
                deleteTimetable(id);
            }
        });
        recyclerView.setAdapter(adapter);

        // Load timetable data
        loadTimetable();

        // Add timetable button action
        fabAddTimetable.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTimetableActivity.class);
            startActivity(intent);
        });
    }

    // Method to load timetable data from SQLite database
    private void loadTimetable() {
        timetableList.clear();
        Cursor cursor = databaseHelper.getAllTimetables();

        if (cursor.getCount() == 0) {
            // No data found
            recyclerView.setVisibility(View.GONE);
            noTimetableMessage.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                TimetableModel model = new TimetableModel(
                        cursor.getInt(0),  // ID
                        cursor.getString(1),  // Day
                        cursor.getString(2),  // Time
                        cursor.getString(3),  // Subject
                        cursor.getString(4),  // Lecturer
                        cursor.getString(5)   // Venue
                );
                timetableList.add(model);
            }
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            noTimetableMessage.setVisibility(View.GONE);
        }
    }

    // Method to delete a timetable entry
    private void deleteTimetable(int id) {
        int rowsAffected = databaseHelper.deleteTimetable(id);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Timetable deleted", Toast.LENGTH_SHORT).show();
            loadTimetable(); // Reload the timetable after deletion
        } else {
            Toast.makeText(this, "Error deleting timetable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTimetable(); // Reload timetable when returning to the activity
    }
}
