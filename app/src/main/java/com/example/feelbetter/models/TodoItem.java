package com.example.feelbetter.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author mgh
 * this is a model of todoTask item
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class TodoItem {
    private String startTime;
    private String dueTime;
    private String task;
    private String created;
    private String owner;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");


    public TodoItem(String startTime, String dueTime, String task, String owner) {
        this.dueTime = dueTime;
        this.startTime = startTime;
        this.created = dtf.format(LocalDateTime.now());
        this.task = task;
        this.owner = owner;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getTask() {
        return task;
    }

    public String getCreated() {
        return created;
    }

    public String getOwner() {
        return owner;
    }
}
