package com.pottersfieldap.geneticAlgorithm;
import java.util.HashMap;
import java.util.List;

public class Facilitator {
    private String name;
    HashMap<Integer, Activity> schedule;

    public Facilitator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
