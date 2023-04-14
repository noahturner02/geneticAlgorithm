import com.pottersfieldap.geneticAlgorithm.GeneticAlgorithm;
import com.pottersfieldap.geneticAlgorithm.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoftMaxTest {
    @Test
    @DisplayName("Softmax")
    public void testSoftMax() {
        GeneticAlgorithm g = new GeneticAlgorithm();
        List<Schedule> generation = new ArrayList<>(List.of(
                new Schedule(new ArrayList<>()),
                new Schedule(new ArrayList<>()),
                new Schedule(new ArrayList<>()),
                new Schedule(new ArrayList<>()),
                new Schedule(new ArrayList<>())
        ));
        generation.get(0).setFitness(6.6);
        generation.get(1).setFitness(8.5);
        generation.get(2).setFitness(14.15);
        generation.get(3).setFitness(14.15);
        generation.get(4).setFitness(14.15);
        generation = g.softMaxNormalize(generation);
        assertEquals(generation.get(0).getProbability(), .000175, .00001);
        assertEquals(generation.get(1).getProbability(), .001170, .00001);
        assertEquals(generation.get(2).getProbability(), .332884, .00001);
        assertEquals(generation.get(3).getProbability(), .332884, .00001);
        assertEquals(generation.get(4).getProbability(), .332884, .00001);
    }
}
