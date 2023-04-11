package com.pottersfieldap.geneticAlgorithm;


import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<Activity> activityList = new ArrayList<>();
    private List<Facilitator> active_facilitators = new ArrayList<>();
    public List<Activity> getActivityList() {
        return activityList;
    }
    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
    public List<Facilitator> getActive_facilitators() {
        return active_facilitators;
    }
    public void setActive_facilitators(List<Facilitator> active_facilitators) {
        this.active_facilitators = active_facilitators;
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
