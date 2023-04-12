package com.pottersfieldap.geneticAlgorithm;
import java.util.List;
// Object form of the activities offered by SLA
import java.util.HashMap;
import java.util.List;
public class Activity implements Cloneable {
    String name;
    private int expected_enrollment;
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

    public boolean consecutiveWith(Activity a2) {
        switch(this.time) {
            case 12:
                if (a2.getTime() == 1 || a2.getTime() == 11) {
                    return true;
                }
                break;
            case 1:
                if (a2.getTime() == 12 || a2.getTime() == 2) {
                    return true;
                }
                break;
            default:
                if (a2.getTime() == (this.time - 1) || a2.getTime() == (this.time + 1)) {
                    return true;
                }
                break;
        }
        return false;
    }

    public String toString() {
        return name + " at " + time + " o'clock in " + room.getRoom_name() + " taught by instructor " + active_facilitator.getName() + ".";
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getExpected_enrollment() {
        return expected_enrollment;
    }

}
