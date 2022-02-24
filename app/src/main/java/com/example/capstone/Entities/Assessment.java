package com.example.capstone.Entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table", foreignKeys = @ForeignKey(entity = Course.class,
        parentColumns = "id",
        childColumns = "courseID", onDelete = CASCADE))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int courseID;

    private String name;
    private int type;
    private String startDate;
    private String endDate;
    private boolean startAlert;
    private boolean endAlert;


    public Assessment(int courseID, String name, int type, String startDate, String endDate, boolean startAlert, boolean endAlert) {
        this.courseID = courseID;
        this.name = name;
        this.type = type;
        this.endDate = endDate;
        this.startDate = startDate;
        this.startAlert = startAlert;
        this.endAlert = endAlert;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}