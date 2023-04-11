package com.pottersfieldap.geneticAlgorithm;
import java.util.List;
// Object form of the activities offered by SLA
import java.util.HashMap;
import java.util.List;
public class Activity {
    String name;
    int expected_enrollment;
    List<Facilitator> preferred_facilitators;
    List<Facilitator> other_facilitators;
    private Facilitator active_facilitator;
    private int time;
    private Room room;

    public Facilitator getActive_facilitator() {
        return active_facilitator;
    }

    public void setActive_facilitator(Facilitator active_facilitator) {
        this.active_facilitator = active_facilitator;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    public Activity(String name, int expected_enrollment, List<Facilitator> preferred_facilitators, List<Facilitator> other_facilitators) {
        this.name = name;
        this.expected_enrollment = expected_enrollment;
        this.preferred_facilitators = preferred_facilitators;
        this.other_facilitators = other_facilitators;
    }

    public String toString() {
        return name + " at " + ((10 + time) % 12) + " o'clock in " + room.getRoom_name() + " taught by instructor " + active_facilitator.getName() + ".";
    }
}
