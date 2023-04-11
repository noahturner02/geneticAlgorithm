package com.pottersfieldap.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reproduce {

    Facilitator lock = new Facilitator("Lock");
    Facilitator glen = new Facilitator("Glen");
    Facilitator banks = new Facilitator("Banks");
    Facilitator richards = new Facilitator("Richards");
    Facilitator shaw = new Facilitator("Shaw");
    Facilitator singer = new Facilitator("Singer");
    Facilitator uther = new Facilitator("Uther");
    Facilitator tyler = new Facilitator("Tyler");
    Facilitator numen = new Facilitator("Numen");
    Facilitator zeldin = new Facilitator("Zeldin");
    List<Facilitator> facilitators = List.of(lock, glen, banks, richards, shaw, singer, uther, tyler, numen, zeldin);
    Activity SLA100A = new Activity("SLA100A", 50, List.of(glen, lock, banks, zeldin), List.of(numen, richards));
    Activity SLA100B = new Activity("SLA100B", 50, List.of(glen, lock, banks, zeldin), List.of(numen, richards));
    Activity SLA191A = new Activity("SLA191A", 50, List.of(glen, lock, banks, zeldin), List.of(numen, richards));
    Activity SLA191B = new Activity("SLA191B", 50, List.of(glen, lock, banks, zeldin), List.of(numen, richards));
    Activity SLA201 = new Activity("SLA201", 50, List.of(glen, banks, zeldin, shaw), List.of(numen, richards, singer));
    Activity SLA291 = new Activity("SLA291", 50, List.of(lock, banks, zeldin, singer), List.of(numen, richards, shaw, tyler));
    Activity SLA303 = new Activity("SLA303", 60, List.of(glen, zeldin, banks), List.of(numen, singer, shaw));
    Activity SLA304 = new Activity("SLA304", 25, List.of(glen, banks, tyler), List.of(numen, singer, shaw, richards, uther, zeldin));
    Activity SLA394 = new Activity("SLA394", 20, List.of(tyler, singer), List.of(richards, zeldin));
    Activity SLA449 = new Activity("SLA449", 60, List.of(tyler, singer, shaw), List.of(zeldin, uther));
    Activity SLA451 = new Activity("SLA451", 100, List.of(tyler, singer, shaw), List.of(zeldin, uther, richards, banks));
    List<Activity> activities = List.of(SLA100A, SLA100B, SLA191A, SLA191B, SLA201, SLA291, SLA303, SLA394, SLA449, SLA451);
    Room slater003 = new Room("Slater", "Slater 003", 45);
    Room roman216 = new Room("Roman", "Roman 216", 30);
    Room loft206 = new Room("Loft", "Loft 206", 75);
    Room roman201 = new Room("Roman", "Roman 201", 50);
    Room loft310 = new Room("Loft", "Loft 310", 108);
    Room beach201 = new Room("Beach", "Beach 201", 60);
    Room beach301 = new Room("Beach", "Beach 301", 75);
    Room logos325 = new Room("Logos", "Logos 325", 450);
    Room frank119 = new Room("Frank", "Frank 119", 60);
    List<Room> rooms = List.of(slater003, roman216, roman201, loft206, loft310, logos325, beach201, beach301, frank119);
    List<Schedule> generation = new ArrayList<>();
    public void firstGeneration() {
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            Schedule s = new Schedule();
            List<Activity> activityList = new ArrayList<>();
            for (Activity a : activities) {
                a.setActive_facilitator(facilitators.get(r.nextInt(0, 10)));
                a.setRoom(rooms.get(r.nextInt(0, 9)));
                a.setTime(r.nextInt(0, 6));
                activityList.add(a);
            }
            s.setActivityList(activityList);
            generation.add(s);
        }

        // Printing only
        for (Schedule s : generation) {
            System.out.println(s);
        }
    }
    

}
