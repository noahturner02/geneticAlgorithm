package com.pottersfieldap.geneticAlgorithm;
import java.util.HashMap;

public class Room {
    String building;
    String room_name;
    int capacity;
    HashMap<Integer, Activity> schedule;
    public Room(String building, String room_name, int capacity) {
        this.building = building;
        this.room_name = room_name;
        this.capacity = capacity;
    }
}
