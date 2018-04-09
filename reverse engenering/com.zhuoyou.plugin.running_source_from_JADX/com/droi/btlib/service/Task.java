package com.droi.btlib.service;

public class Task {
    private int taskCode = -1;
    private Object taskDetail;
    private int taskSingal;

    public Task(int taskCode, int taskSingal, Object taskDetail) {
        this.taskCode = taskCode;
        this.taskSingal = taskCode;
        this.taskDetail = Integer.valueOf(taskCode);
    }

    public int getTaskSingal() {
        return this.taskSingal;
    }

    public void setTaskSingal(int taskSingal) {
        this.taskSingal = taskSingal;
    }

    public int getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(int taskCode) {
        this.taskCode = taskCode;
    }

    public Object getTaskDetail() {
        return this.taskDetail;
    }

    public void setTaskDetail(Object taskDetail) {
        this.taskDetail = taskDetail;
    }
}
