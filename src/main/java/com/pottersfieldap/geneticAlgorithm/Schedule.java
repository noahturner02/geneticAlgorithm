package com.pottersfieldap.geneticAlgorithm;


import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<Activity> activityList = new ArrayList<>();

    public List<Activity> getActivityList() {
        return activityList;
    }
    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Activity a : activityList) {
            sb.append(a);
            sb.append("\n");
        }
        return sb.toString();
    }
}
