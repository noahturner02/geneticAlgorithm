package com.pottersfieldap.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    // Initialize all data from the problem description. Make them final, since this data will not change during algorithm.
    final Facilitator lock = new Facilitator("Lock");
    final Facilitator glen = new Facilitator("Glen");
    final Facilitator banks = new Facilitator("Banks");
    final Facilitator richards = new Facilitator("Richards");
    final Facilitator shaw = new Facilitator("Shaw");
    final Facilitator singer = new Facilitator("Singer");
    final Facilitator uther = new Facilitator("Uther");
    final static Facilitator tyler = new Facilitator("Tyler");
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
    final List<Activity> activities = List.of(SLA100A, SLA100B, SLA191A, SLA191B, SLA201, SLA291, SLA303, SLA304, SLA394, SLA449, SLA451);
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
    // Complete history of generations. Add a generation once it is done.
    List<List<Schedule>> generation_list = new ArrayList<>();
    Comparator<Schedule> scheduleComparator = (Schedule s1, Schedule s2) -> {
        if (s1.getProbability() > s2.getProbability()) {
            return -1;
        } else if (s1.getProbability() < s2.getProbability()) {
            return 1;
        } else {
            return 0;
        }
    };
    int mutation_rate = 100; // 1/100 chance for activity to mutate
    public List<Activity> getActivities() {
        return activities;
    }
    public List<Room> getRooms() {
        return rooms;
    }
    public List<Facilitator> getFacilitators() {
        return facilitators;
    }
    private void halveMutationRate() {
        mutation_rate = mutation_rate * 2;
    }

    public void geneticAlgorithm() {
        FitnessFunction f = new FitnessFunction();
        for (int i = 0; i < 100; i++) {
            List<Schedule> generation = new ArrayList<>();
            if (i == 0) {
                generation = firstGeneration();
            } else {
                generation = generation_list.get(generation_list.size() - 1);
            }
            for (Schedule s : generation) {
                s.setFitness(f.fitnessFunction(s));
            }
            generation = softMaxNormalize(generation);
            generation = rankGeneration(generation);
            System.out.println(generation.get(0));
            System.out.println(generation.get(0).getFitness());
            System.out.println(generation.get(0).getProbability());
            generation = cullGeneration(generation);
            generation = nextGeneration(generation);
            generation_list.add(generation);
        }
    }
    // Makes the first generation completely random. No parents to 'cross over'
    private List<Schedule> firstGeneration() {
        Random r = new Random();
        List<Schedule> generation = new ArrayList<>();
        // Make 100 children
        for (int i = 0; i < 500; i++) {
            List<Activity> activityList = new ArrayList<>();
            for (Activity a : activities) { // iterate through all activities offered
                Activity a_clone = (Activity) a.clone(); // Clone the activity. copies existing info so we can edit the rest
                a_clone.setActive_facilitator(facilitators.get(r.nextInt(0, 10))); // Select random facilitator
                a_clone.setRoom(rooms.get(r.nextInt(0, 9))); // select random room
                a_clone.setTime(times.get(r.nextInt(0, 6))); // select random time
                activityList.add(a_clone); // add the clone to activity list
            }
            generation.add(new Schedule(activityList)); // child is now complete. add it to the generation
        }
        return generation;
    }
    // sort the generation by fitness
    private List<Schedule> rankGeneration(List<Schedule> generation) {
        generation.sort(scheduleComparator);
        return generation;
    }
    // Kill off the lowest half of the generation
    private List<Schedule> cullGeneration(List<Schedule> generation) {
        List<Schedule> new_generation = new ArrayList<>();
        for(int i = 0; i < generation.size() / 2; i++) {
            new_generation.add(generation.get(i));
        }
        return new_generation;
    }
    private List<Schedule> nextGeneration(List<Schedule> generation) {
        List<Schedule> offspring = new ArrayList<>();
        Random r = new Random();
        int dividing_line;
        for (int i = 0; i < generation.size(); i++) {
            Schedule father = generation.get(i);
            Schedule mother = generation.get(r.nextInt(0, 250));
            List<Activity> childActivityList = new ArrayList<>();
            dividing_line = r.nextInt(0, 11);
            for (int j = 0; j < father.getActivityList().size(); j++) {
                Activity newActivity;
                if (j < dividing_line) {
                    newActivity = (Activity) father.getActivityList().get(j).clone();
                } else {
                    newActivity = (Activity) mother.getActivityList().get(j).clone();
                }
                newActivity = mutate(newActivity);
                childActivityList.add(newActivity);
            }
            offspring.add(new Schedule(childActivityList));
        }
        generation.addAll(offspring);
        return generation;
    }

    private Activity mutate(Activity activity) {
        Random r = new Random();
        if (r.nextInt(0, mutation_rate) == 0) {
            int attribute_selection = r.nextInt(0, 3);
            if (attribute_selection == 0) {
                activity.setActive_facilitator(facilitators.get(r.nextInt(0, 10)));
            } else if (attribute_selection == 1) {
                activity.setRoom(rooms.get(r.nextInt(0, 9)));
            } else {
                activity.setTime(times.get(r.nextInt(0, 6)));
            }
        }
        return activity;
    }
    public List<Schedule> softMaxNormalize(List<Schedule> generation) {
        for (Schedule s1 : generation) {
            double total = 0; // denominator of softmax
            for (Schedule s2 : generation) {
                total += Math.exp(s2.getFitness());
            }
            s1.setProbability(Math.exp(s1.getFitness()) / total);
        }
        return generation;
    }
    private double averageFitness(List<Schedule> generation) {
        double sum = 0;
        for (Schedule s : generation) {
            sum += s.getFitness();
        }
        return sum / generation.size();
    }
}
