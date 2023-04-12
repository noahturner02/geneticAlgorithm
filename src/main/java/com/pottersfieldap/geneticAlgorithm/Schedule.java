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

    public Schedule(List<Activity> activityList) {
        this.activityList = activityList;
        List<Facilitator> active_facilitators = new ArrayList<>();
        for (Activity a : activityList) {
            if (!active_facilitators.contains(a.getActive_facilitator())) {
                active_facilitators.add(a.getActive_facilitator());
            }
        }
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

    public Activity getActivityByName(String s) {
        for (Activity a : activityList) {
            if (a.name.equals(s)) {
                return a;
            }
        }
    }
}
