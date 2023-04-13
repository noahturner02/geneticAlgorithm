import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pottersfieldap.geneticAlgorithm.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FitnessFunctionTest {
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
    FitnessFunction fitnessFunction = new FitnessFunction();

    // Supply with 3 lists, each the size of activities: facilitators, rooms, and times. Each row in this set of lists is the data for an activity.
    private Schedule initializeTestSchedule(List<Facilitator> facilitatorList, List<Room> roomList, List<Integer> timeList) {
        List<Activity> activityList = new ArrayList<>();
        for (int i = 0; i < activities.size(); i++) {
            Activity a = (Activity) activities.get(i).clone();
            a.setActive_facilitator(facilitatorList.get(i));
            a.setRoom(roomList.get(i));
            a.setTime(timeList.get(i));
            activityList.add(a);
        }
        return new Schedule(activityList);
    }

    @Test
    @DisplayName("Double Booked Activity")
    void testDoubleBooked() {
        List<Facilitator> fList = List.of(glen, uther, uther, uther, uther, richards, lock, lock, singer, shaw, singer);
        List<Room> rList = List.of(beach201, beach201, beach301, roman216, beach301, beach301, loft310, beach301, roman216, roman201, frank119);
        List<Integer> tList = List.of(2, 2, 10, 1, 1, 11, 1, 3, 10, 11, 1);
        Schedule s = initializeTestSchedule(fList, rList, tList);
        System.out.println(s);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA100A")), -0.25);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA100B")), -0.25);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA191A")), 0);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA191B")), 0);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA201")), 0);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA291")), 0);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA303")), 0);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA304")), 0);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA394")), 0);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA449")), 0);
        assertEquals(fitnessFunction.doubleBookedActivity(s, s.getActivityByName("SLA451")), 0);
    }
    @Test
    @DisplayName("Room Size Analysis")
    void testRoomSizeAnalysis() {
        List<Facilitator> fList = List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, tyler, lock, zeldin);
        List<Room> rList = List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301);
        List<Integer> tList = List.of(10, 11, 12, 1, 2, 3, 3, 2, 1, 3, 4);
        Schedule s = initializeTestSchedule(fList, rList, tList);
        System.out.println(s);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA100A")),0.3);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA100B")),0.3);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA191A")),-0.5);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA191B")),0.3);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA201")),-0.4);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA291")),-0.4);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA303")),-0.5);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA304")),-0.2);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA394")),-0.2);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA449")),0.3);
        assertEquals(fitnessFunction.roomSizeAnalysis(s.getActivityByName("SLA451")),-0.5);
    }
    @Test
    @DisplayName("Preferred Facilitator")
    void testPreferredFacilitator() {
        List<Facilitator> fList = List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, tyler, lock, zeldin);
        List<Room> rList = List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301);
        List<Integer> tList = List.of(10, 11, 12, 1, 2, 3, 3, 2, 1, 3, 4);
        Schedule s = initializeTestSchedule(fList, rList, tList);
        System.out.println(s);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA100A")), 0.5);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA100B")), 0.2);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA191A")), -0.1);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA191B")), -0.1);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA201")), -0.1);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA291")), 0.2);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA303")), 0.2);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA304")), -0.1);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA394")), 0.5);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA449")), -0.1);
        assertEquals(fitnessFunction.preferredFacilitator(s.getActivityByName("SLA451")), 0.2);
    }
    @Test
    @DisplayName("Double Booked Facilitators")
    void testDoubleBookedFacilitators() {
        List<Facilitator> fList = List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, singer, lock, zeldin);
        List<Room> rList = List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301);
        List<Integer> tList = List.of(10, 11, 12, 1, 2, 3, 3, 2, 1, 3, 4);
        Schedule s = initializeTestSchedule(fList, rList, tList);
        System.out.println(s);
        assertEquals(fitnessFunction.doubleBookedFacilitators(s), 0.6, 0.0001);
    }

    @Test
    @DisplayName("Num of Activities")
    void testNumOfActivities() {
        List<Facilitator> fList = List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, singer, lock, zeldin);
        List<Room> rList = List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301);
        List<Integer> tList = List.of(10, 11, 12, 1, 2, 3, 3, 2, 1, 3, 4);
        Schedule s = initializeTestSchedule(fList, rList, tList);
        System.out.println(s);
        assertEquals(fitnessFunction.numOfActivities(s), -2.4, .0001);
    }
    @Test
    @DisplayName("Consecutive Activities Test")
    void testConsecutiveActivities() {
        List<Facilitator> fList = List.of(glen, glen, shaw, singer, uther, shaw, shaw, lock, singer, lock, zeldin);
        List<Room> rList = List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301);
        List<Integer> tList = List.of(10, 2, 12, 1, 2, 1, 2, 2, 1, 3, 4);
        Schedule s = initializeTestSchedule(fList, rList, tList);
        System.out.println(s);
        assertEquals(fitnessFunction.consecutiveActivities(s), 1.5);
    }
    @Test
    @DisplayName("SLA100 Sections")
    void testSLA100Sections() {
        List<Facilitator> fList = new ArrayList<>(List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, singer, lock, zeldin));
        List<Room> rList = new ArrayList<>(List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301));
        List<Integer> tList = new ArrayList<>(List.of(10, 3, 12, 1, 2, 3, 3, 2, 1, 3, 4));
        Schedule s = initializeTestSchedule(fList, rList, tList);
        System.out.println(s);
        // Two sections are over 4 hours apart, so function should return 0.5
        assertEquals(fitnessFunction.SLA100Sections(s), 0.5);
        // Set SLA100B to 10. Same time as 100A
        tList.set(1, 10);
        Schedule s2 = initializeTestSchedule(fList, rList, tList);
        // Two sections are same time. Function should return -0.5
        assertEquals(fitnessFunction.SLA100Sections(s2), -0.5);
        // Set SLA100B to 12
        tList.set(1, 12);
        Schedule s3 = initializeTestSchedule(fList, rList, tList);
        // Neither case applies. Return 0
        assertEquals(fitnessFunction.SLA100Sections(s3), 0);
    }
    @Test
    @DisplayName("SLA191 Sections")
    void testSLA191Sections() {
        List<Facilitator> fList = new ArrayList<>(List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, singer, lock, zeldin));
        List<Room> rList = new ArrayList<>(List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301));
        List<Integer> tList = new ArrayList<>(List.of(10, 3, 10, 3, 2, 3, 3, 2, 1, 3, 4));
        Schedule s = initializeTestSchedule(fList, rList, tList);
        System.out.println(s);
        // Two sections are 4 hours apart. Should return 0.5
        assertEquals(fitnessFunction.SLA191Sections(s), 0.5);
        tList.set(3, 10);
        Schedule s2 = initializeTestSchedule(fList, rList, tList);
        // Two sections are at the same time. Should return -0.5
        assertEquals(fitnessFunction.SLA191Sections(s2), -0.5);
        tList.set(3, 12);
        Schedule s3 = initializeTestSchedule(fList, rList, tList);
        // Two sections are at diff times. Should return 0
        assertEquals(fitnessFunction.SLA191Sections(s3), 0);
    }
    @Test
    @DisplayName("Consecutive SLA100 & SLA191 Sections")
    void testConsecutiveSLA100191() {
        List<Facilitator> fList = new ArrayList<>(List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, singer, lock, zeldin));
        List<Room> rList = new ArrayList<>(List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301));
        List<Integer> tList = new ArrayList<>(List.of(10, 3, 12, 11, 2, 3, 3, 2, 1, 3, 4));
        Schedule s = initializeTestSchedule(fList, rList, tList);
        // SLA100A and SLA191B are consecutive, and they are both in Roman and Beach, so it should yield 0.5
        assertEquals(fitnessFunction.consecutiveSLA100191(s), 0.5);
        rList.set(0, logos325);
        Schedule s2 = initializeTestSchedule(fList, rList, tList);
        // SLA100A and SLA 191B are consecutive, but one is in Beach while the other is in Logos. Should return 0.1
        assertEquals(fitnessFunction.consecutiveSLA100191(s2), 0.1);
        tList.set(0, 2);
        Schedule s3 = initializeTestSchedule(fList, rList, tList);
        // Two sections are not consecutive. Should return 0
        assertEquals(fitnessFunction.consecutiveSLA100191(s3), 0);
    }
    @Test
    @DisplayName("One Hour Gap Between SLA100 and SLA191")
    void testOneHourGapSLA100191() {
        List<Facilitator> fList = new ArrayList<>(List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, singer, lock, zeldin));
        List<Room> rList = new ArrayList<>(List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301));
        List<Integer> tList = new ArrayList<>(List.of(10, 11, 12, 3, 2, 3, 3, 2, 1, 3, 4));
        Schedule s = initializeTestSchedule(fList, rList, tList);
        // SLA100A is 1 hour gap from SLA191A. Should return 0.25
        assertEquals(fitnessFunction.oneHourGapSLA100191(s), 0.25);
        tList.set(0, 11);
        Schedule s2 = initializeTestSchedule(fList, rList, tList);
        // There is no 1 hr gap between sections. Return 0
        assertEquals(fitnessFunction.oneHourGapSLA100191(s2), 0);
    }

}
