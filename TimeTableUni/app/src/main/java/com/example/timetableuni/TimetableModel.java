package com.example.timetableuni;

public class TimetableModel {

    private int id;
    private String day;
    private String time;
    private String subject;
    private String lecturer;
    private String venue;

    public TimetableModel(int id, String day, String time, String subject, String lecturer, String venue) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.lecturer = lecturer;
        this.venue = venue;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getSubject() {
        return subject;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getVenue() {
        return venue;
    }
}
