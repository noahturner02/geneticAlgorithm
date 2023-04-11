package com.pottersfieldap.geneticAlgorithm;

import org.javatuples.Pair;

import java.util.*;

// This is the class that contains most of the code for the genetic algorithm
public class Reproduce {
// Initialize all data from the problem description. Make them final, since this data will not change during algorithm.
    final Facilitator lock = new Facilitator("Lock");
    final Facilitator glen = new Facilitator("Glen");
    final Facilitator banks = new Facilitator("Banks");
    final Facilitator richards = new Facilitator("Richards");
    final Facilitator shaw = new Facilitator("Shaw");
    final Facilitator singer = new Facilitator("Singer");
    final Facilitator uther = new Facilitator("Uther");
    final Facilitator tyler = new Facilitator("Tyler");
    final Facilitator numen = new Facilitator("Numen");
    final Facilitator zeldin = new Facilitator("Zeldin");
    final List<Facilitator> facilitators = List.of(lock, glen, banks, richards, shaw, singer, uther, tyler, numen, zeldin);
    final Activity SLA100A = new Activity("SLA100A", 50, List.of(glen, lock, banks, zeldin), List.of(numen, richards));
    final Activity SLA100B = new Activity("SLA100B", 50, List.of(glen, lock, banks, zeldin), List.of(numen, richards));
    final Activity SLA191A = new Activity("SLA191A", 50, List.of(glen, lock, banks, zeldin), List.of(numen, richards));
    final Activity SLA191B = new Activity("SLA191B", 50, List.of(glen, lock, banks, zeldin), List.of(numen, richards));
    final Activity SLA201 = new Activity("SLA201", 50, List.of(glen, banks, zeldin, shaw), List.of(numen, richards, singer));
    final Activity SLA291 = new Activity("SLA291", 50, List.of(lock, banks, zeldin, singer), List.of(numen, richards, shaw, tyler));
    final Activity SLA303 = new Activity("SLA303", 60, List.of(glen, zeldin, banks), List.of(numen, singer, shaw));
    final Activity SLA304 = new Activity("SLA304", 25, List.of(glen, banks, tyler), List.of(numen, singer, shaw, richards, uther, zeldin));
    final Activity SLA394 = new Activity("SLA394", 20, List.of(tyler, singer), List.of(richards, zeldin));
    final Activity SLA449 = new Activity("SLA449", 60, List.of(tyler, singer, shaw), List.of(zeldin, uther));
    final Activity SLA451 = new Activity("SLA451", 100, List.of(tyler, singer, shaw), List.of(zeldin, uther, richards, banks));
    final List<Activity> activities = List.of(SLA100A, SLA100B, SLA191A, SLA191B, SLA201, SLA291, SLA303, SLA394, SLA449, SLA451);
    final Room slater003 = new Room("Slater", "Slater 003", 45);
    final Room roman216 = new Room("Roman", "Roman 216", 30);
    final Room loft206 = new Room("Loft", "Loft 206", 75);
    final Room roman201 = new Room("Roman", "Roman 201", 50);
    final Room loft310 = new Room("Loft", "Loft 310", 108);
    final Room beach201 = new Room("Beach", "Beach 201", 60);
    final Room beach301 = new Room("Beach", "Beach 301", 75);
    final Room logos325 = new Room("Logos", "Logos 325", 450);
    final Room frank119 = new Room("Frank", "Frank 119", 60);
    final List<Room> rooms = List.of(slater003, roman216, roman201, loft206, loft310, logos325, beach201, beach301, frank119);
    final List<Integer> times = List.of(10, 11, 12, 1, 2, 3);
    // Current generation. Contains all offspring schedules in the generation.
    List<Schedule> generation = new ArrayList<>();
    // Complete history of generations. Add a generation once it is done.
    List<List<Schedule>> generation_list = new ArrayList<>();


    public void geneticAlgorithm() {
        firstGeneration();

        // Printing only
        for (Schedule s : generation) {
            System.out.println(s);
            createFacilitatorSchedules(s);
            System.out.println(fitnessFunction(s));
        }
    }
    // Populate the facilitator's schedules with Schedule information. Makes fitness function easier.
    private void createFacilitatorSchedules(Schedule schedule) {
        for (Activity a : schedule.getActivityList()) {
            a.getActive_facilitator().getSchedule().add(new Pair<>(a.getTime(), a));
        }
    }
    // Makes the first generation completely random. No parents to 'cross over'
    private void firstGeneration() {
        Random r = new Random();
        // Make 100 children
        for (int i = 0; i < 100; i++) {
            Schedule s = new Schedule();
            List<Activity> activityList = new ArrayList<>();
            for (Activity a : activities) { // iterate through all activities offered
                Activity a_clone = (Activity) a.clone(); // Clone the activity. copies existing info so we can edit the rest
                a_clone.setActive_facilitator(facilitators.get(r.nextInt(0, 10))); // Select random facilitator
                a_clone.setRoom(rooms.get(r.nextInt(0, 9))); // select random room
                a_clone.setTime(times.get(r.nextInt(0, 6))); // select random time
                activityList.add(a_clone); // add the clone to activity list
            }
            s.setActivityList(activityList); // add activity list to schedule object
            generation.add(s); // child is now complete. add it to the generation
        }
    }

    private double fitnessFunction(Schedule schedule) {
        double total_fitness = 0;
        for (Activity a : schedule.getActivityList()) {
            double activity_fitness = 0;
            activity_fitness += doubleBookedActivity(schedule, a);
            activity_fitness += roomSizeAnalysis(a);
            activity_fitness += preferredFacilitator(a);
            total_fitness += activity_fitness;
        }
        return total_fitness;
    }
    // Check to see if activity is scheduled in the same room and same time as another activity
    private double doubleBookedActivity(Schedule s, Activity activity) {
        int time = activity.getTime();
        Room room = activity.getRoom();
        for (Activity a: s.getActivityList()) {
            // Skip over 'activity'
            if (a.equals(activity)) {
                continue;
            }
            // If a has the same room and time as activity, return a negative fitness
            if ((a.getRoom() == room) && (a.getTime() == time)) {
                // NOTE: if there are conflicting activities, this penalty will happen twice: once for each activity.
                return -0.5;
            }
        }
        return 0;
    }
    // Analyzes the size of the room. penalty for absurdly large rooms, or for too small rooms, benefit otherwise;
    private double roomSizeAnalysis(Activity a) {
        Room room = a.getRoom();
        if (room.getCapacity() >= (a.getExpected_enrollment() * 6)) {
            return -0.4;
        } else if (room.getCapacity() >= (a.getExpected_enrollment() * 3)) {
            return -0.2;
        } else if (room.getCapacity() < a.getExpected_enrollment()) {
            return -0.5;
        } else {
            return 0.3;
        }
    }
    // Assesses fitness of the choice for facilitator. bonus if preferred or other, penalty otherwise
    private double preferredFacilitator(Activity a) {
        if (a.preferred_facilitators.contains(a.getActive_facilitator())) {
            return 0.5;
        } else if (a.other_facilitators.contains(a.getActive_facilitator())) {
            return 0.2;
        } else {
            return -0.1;
        }
    }
    // Checks to see if facilitators are double booked or not. If not, it's a bonus. If they are, it's a penalty


}
