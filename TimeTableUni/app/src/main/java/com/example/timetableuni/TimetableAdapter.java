package com.example.timetableuni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {

    private Context context;
    private List<TimetableModel> timetableList;
    private OnTimetableItemClickListener listener;

    // Constructor for the adapter
    public TimetableAdapter(Context context, List<TimetableModel> timetableList, OnTimetableItemClickListener listener) {
        this.context = context;
        this.timetableList = timetableList;
        this.listener = listener;
    }

    @Override
    public TimetableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timetable, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimetableViewHolder holder, int position) {
        TimetableModel timetable = timetableList.get(position);
        holder.tvDay.setText(timetable.getDay());
        holder.tvTime.setText(timetable.getTime());
        holder.tvSubject.setText(timetable.getSubject());
        holder.tvLecturer.setText(timetable.getLecturer());
        holder.tvVenue.setText(timetable.getVenue());

        // Set Edit button listener
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(timetable);  // Notify the activity to edit the timetable
            }
        });

        // Set Delete button listener
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(timetable.getId());  // Notify the activity to delete the timetable
            }
        });
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }

    // ViewHolder class to represent each item in the RecyclerView
    public static class TimetableViewHolder extends RecyclerView.ViewHolder {

        TextView tvDay, tvTime, tvSubject, tvLecturer, tvVenue;
        Button btnEdit, btnDelete;

        public TimetableViewHolder(View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvLecturer = itemView.findViewById(R.id.tvLecturer);
            tvVenue = itemView.findViewById(R.id.tvVenue);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Define the interface to handle item click events (edit and delete)
    public interface OnTimetableItemClickListener {
        void onEditClick(TimetableModel timetableModel);
        void onDeleteClick(int id);
    }
}
