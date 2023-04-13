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
//    @Test
//    @DisplayName("Preferred Facilitator")
//    void testPreferredFacilitator() {
//        List<Facilitator> fList = List.of(glen, richards, shaw, singer, uther, shaw, shaw, lock, tyler, lock, zeldin);
//        List<Room> rList = List.of(beach201, beach301, roman216, roman201, logos325, logos325, roman216, loft310, loft206, beach201, beach301);
//        List<Integer> tList = List.of(10, 11, 12, 1, 2, 3, 3, 2, 1, 3, 4);
//    }
}
