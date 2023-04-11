package com.pottersfieldap.geneticAlgorithm;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Facilitator {
    private String name;
    private List<Pair<Integer, Activity>> schedule = new ArrayList<>();
    public Facilitator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Pair<Integer, Activity>> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Pair<Integer, Activity>>) {
        this.schedule = schedule;
    }
}
