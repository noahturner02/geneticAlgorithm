package com.pottersfieldap.geneticAlgorithm;

<<<<<<< Updated upstream
import java.util.List;
// Object form of the activities offered by SLA
=======
import java.util.HashMap;
import java.util.List;

>>>>>>> Stashed changes
public class Activity {
    String name;
    int expected_enrollment;
    List<Facilitator> preferred_facilitators;
    List<Facilitator> other_facilitators;
    Facilitator active_facilitator;
<<<<<<< Updated upstream

=======
    int time;
    Room room;
>>>>>>> Stashed changes
    public void Activity(String name, int expected_enrollment, List<Facilitator> preferred_facilitators, List<Facilitator> other_facilitators) {
        this.name = name;
        this.expected_enrollment = expected_enrollment;
        this.preferred_facilitators = preferred_facilitators;
        this.other_facilitators = other_facilitators;
    }
}
