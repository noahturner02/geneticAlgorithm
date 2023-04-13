package com.pottersfieldap.geneticAlgorithm;

import java.util.*;

// This is the class that contains most of the code for the genetic algorithm
public class FitnessFunction {

    public double fitnessFunction(Schedule schedule) {
        double total_fitness = 0;
        for (Activity a : schedule.getActivityList()) {
            double activity_fitness = 0;
            activity_fitness += doubleBookedActivity(schedule, a);
            activity_fitness += roomSizeAnalysis(a);
            activity_fitness += preferredFacilitator(a);
            total_fitness += activity_fitness;
        }
        total_fitness += doubleBookedFacilitators(schedule);
        total_fitness += numOfActivities(schedule);
        total_fitness += consecutiveActivities(schedule);
        total_fitness += SLA100Sections(schedule);
        total_fitness += SLA191Sections(schedule);
        total_fitness += consecutiveSLA100191(schedule);
        total_fitness += oneHourGapSLA100191(schedule);
        total_fitness += sameTimeSLA100191(schedule);
        return total_fitness;
    }
    // Check to see if activity is scheduled in the same room and same time as another activity
    public double doubleBookedActivity(Schedule s, Activity activity) {
        int time = activity.getTime();
        Room room = activity.getRoom();
        for (Activity a: s.getActivityList()) {
            // Skip over 'activity'
            if (a.equals(activity)) {
                continue;
            }
            // If a has the same room and time as activity, return a negative fitness
            if ((a.getRoom() == room) && (a.getTime() == time)) {
                // NOTE: if there are conflicting activities, this penalty will happen twice: once for each activity. Sum of -0.5
                return -0.25;
            }
        }
        return 0;
    }
    // Analyzes the size of the room. penalty for absurdly large rooms, or for too small rooms, benefit otherwise;
    public double roomSizeAnalysis(Activity a) {
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
    public double preferredFacilitator(Activity a) {
        if (a.preferred_facilitators.contains(a.getActive_facilitator())) {
            return 0.5;
        } else if (a.other_facilitators.contains(a.getActive_facilitator())) {
            return 0.2;
        } else {
            return -0.1;
        }
    }
    // Checks to see if facilitators are double booked or not. If not, it's a bonus. If they are, it's a penalty
    public double doubleBookedFacilitators(Schedule s) {
        double doubleBookedBonus = 0;
        for (Facilitator f : s.getActive_facilitators()) {
            Set<Integer> time_set = new HashSet<>();
            boolean no_conflict = true;
            for (Activity a : s.getActivityList()) {
                if (a.getActive_facilitator().equals(f)) {
                    if (!time_set.contains(a.getTime())) {
                        time_set.add(a.getTime());
                    } else {
                        doubleBookedBonus -= 0.2;
                        no_conflict = false;
                        break;
                    }
                }
            }
            if (no_conflict) {
                doubleBookedBonus += 0.2;
            }
        }
        return doubleBookedBonus;
    }
    // Handles penalties for overloaded or underloaded facilitators.
    public double numOfActivities(Schedule s) {
        HashMap<Facilitator, Integer> activityCount = new HashMap<>();
        for (Activity a : s.getActivityList()) {
            Facilitator f = a.getActive_facilitator();
            if (activityCount.containsKey(f)) {
                activityCount.put(f, activityCount.get(f) + 1);
            } else {
                activityCount.put(f, 1);
            }
        }
        double total_penalty = 0;
        for (Facilitator f : activityCount.keySet()) {
            if (activityCount.get(f) > 4) {
                total_penalty -= 0.5;
            } else if (activityCount.get(f) <= 2) {
                if (!f.equals(GeneticAlgorithm.tyler)) {
                    total_penalty -= 0.4;
                }
            }
        }
        return total_penalty;
    }
    // Give a bonus for back to back classes for instructors.
    public double consecutiveActivities(Schedule s) {
        double consecutiveActivityBonus = 0;
        for (Facilitator f : s.getActive_facilitators()) {
            List<Integer> time_of_activities = new ArrayList<>();
            for (Activity a : s.getActivityList()) {
                if (a.getActive_facilitator().equals(f)) {
                    time_of_activities.add(a.getTime());
                }
            }

            for (int t : time_of_activities) {
                switch (t) {
                    case 10:
                        if (time_of_activities.contains(11)) {
                            consecutiveActivityBonus += 0.5;
                        }
                        break;
                    case 11:
                        if (time_of_activities.contains(12)) {
                            consecutiveActivityBonus += 0.5;
                        }
                        break;
                    case 12:
                        if (time_of_activities.contains(1)) {
                            consecutiveActivityBonus += 0.5;
                        }
                        break;
                    case 1:
                        if (time_of_activities.contains(2)) {
                            consecutiveActivityBonus += 0.5;
                        }
                        break;
                    case 2:
                        if (time_of_activities.contains(3)) {
                            consecutiveActivityBonus += 0.5;
                        }
                        break;
                }
            }
        }
        return consecutiveActivityBonus;
    }
    // Manage both sections of SLA100. If they are over 4 hours apart, bonus. If they are at the same time, penalty
    public double SLA100Sections(Schedule s) {
        Activity SLA100A = s.getActivityByName("SLA100A");
        Activity SLA100B = s.getActivityByName("SLA100B");

        if (SLA100A.getTime() == SLA100B.getTime()) {
            return -0.5;
        } else if ((SLA100A.getTime() == 10 && SLA100B.getTime() == 3) || (SLA100A.getTime() == 3 && SLA100B.getTime() == 10)) {
            return 0.5;
        }
        return 0;
    }
    // Manage SLA191 sections. Bonus if over 4 hours apart, penalty if at the same time
    public double SLA191Sections(Schedule s) {
        Activity SLA191A = s.getActivityByName("SLA191A");
        Activity SLA191B = s.getActivityByName("SLA191B");

        if (SLA191A.getTime() == SLA191B.getTime()) {
            return -0.5;
        } else if ((SLA191A.getTime() == 10 && SLA191B.getTime() == 3) || (SLA191A.getTime() == 3 && SLA191B.getTime() == 10)) {
            return 0.5;
        }
        return 0;
    }
    // returns bonus if one section of 100 is consecutive with another section of 191. penalty if one class is in roman or beach and the other not.
    public double consecutiveSLA100191(Schedule s) {
        Activity SLA100A = s.getActivityByName("SLA100A");
        Activity SLA100B = s.getActivityByName("SLA100B");
        Activity SLA191A = s.getActivityByName("SLA191A");
        Activity SLA191B = s.getActivityByName("SLA191B");


        if (SLA100A.consecutiveWith(SLA191A)) { // if these two activities are consecutive
            if ((romanOrBeach(SLA100A) && !romanOrBeach(SLA191A)) || (romanOrBeach(SLA191A) && !romanOrBeach(SLA100A))) { // if one is in roman or beach and the other isn't
                return 0.1; // 0.5 for consecutive, -0.4 for long walk
            } else {
                return 0.5; // just 0.5 for consecutive
            }
        } else if (SLA100B.consecutiveWith(SLA191A)) { // repeat for other combinations of 100 and 191 sections
            if ((romanOrBeach(SLA100B) && !romanOrBeach(SLA191A)) || (romanOrBeach(SLA191A) && !romanOrBeach(SLA100B))) {
                return 0.1;
            } else {
                return 0.5;
            }
        } else if (SLA191B.consecutiveWith(SLA100A)) {
            if ((romanOrBeach(SLA100A) && !romanOrBeach(SLA191B)) || (romanOrBeach(SLA191B) && !romanOrBeach(SLA100A))) {
                return 0.1;
            } else {
                return 0.5;
            }
        } else if (SLA100B.consecutiveWith(SLA191B)) {
            if ((romanOrBeach(SLA100B) && !romanOrBeach(SLA191B)) || (romanOrBeach(SLA191B) && !romanOrBeach(SLA100B))) {
                return 0.1;
            } else {
                return 0.5;
            }
        }
        return 0;
    }
    // give bonus if one section for 100 is an hour gap from a section of 191
    public double oneHourGapSLA100191(Schedule s) {
        List<Activity> SLA100 = new ArrayList<>(List.of(s.getActivityByName("SLA100A"), s.getActivityByName("SLA100B")));
        List<Activity> SLA191 = new ArrayList<>(List.of(s.getActivityByName("SLA191A"), s.getActivityByName("SLA191B")));
        for (Activity a100 : SLA100) {
            for (Activity a191 : SLA191) {
                if (Activity.convertToTime(a100.getTime()) == Activity.convertToTime(a191.getTime() + 2) || (Activity.convertToTime(a100.getTime()) == Activity.convertToTime(a191.getTime() - 2))) {
                    return 0.25;
                }
            }
        }
        return 0;
    }

    private boolean romanOrBeach(Activity activity) {
        String building = activity.getRoom().building;
        if (building.equals("Roman") || building.equals("Beach")) {
            return true;
        } else {
            return false;
        }
    }

    public double sameTimeSLA100191(Schedule s) {
        List<Activity> SLA100 = new ArrayList<>(List.of(s.getActivityByName("SLA100A"), s.getActivityByName("SLA100B")));
        List<Activity> SLA191 = new ArrayList<>(List.of(s.getActivityByName("SLA191A"), s.getActivityByName("SLA191B")));
        for (Activity a100 : SLA100) {
            for (Activity a191 : SLA191) {
                if (a100.getTime() == a191.getTime()) {
                    return -0.25;
                }
            }
        }
        return 0;
    }
}
