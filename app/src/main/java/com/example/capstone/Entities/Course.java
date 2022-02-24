package com.example.capstone.Entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table",
        foreignKeys = @ForeignKey(entity = Term.class,
                parentColumns = "id",
                childColumns = "termID",
                onDelete = CASCADE))

public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int termID;

    private String title;
    private String start;
    private String end;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private int status;
    private boolean startAlert;
    private boolean endAlert;

    public Course(int termID, String title, String start, String end, String mentorName,
                  String mentorPhone, String mentorEmail, int status, boolean startAlert,
                  boolean endAlert) {
        this.termID = termID;
        this.title = title;
        this.start = start;
        this.end = end;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.status = status;
        this.startAlert = startAlert;
        this.endAlert = endAlert;
    }

    /*
    public Course(int termID, String title, String start, String end, String mentorName,
     String mentorPhone, String mentorEmail, int status, boolean alert) {
        this.termID = termID;
        this.title = title;
        this.start = start;
        this.end = end;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.status = status;
        this.alert = alert;
    }
*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public boolean isStartAlert() {
        return startAlert;
    }

    public void setStartAlert(boolean startAlert) {
        this.startAlert = startAlert;
    }

    public boolean isEndAlert() {
        return endAlert;
    }

    public void setEndAlert(boolean endAlert) {
        this.endAlert = endAlert;
    }
}