import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Test
    void constructorNullException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
    }
    @Test
    void constructorNullMsgException() {
        try {
            new Hippodrome(null);
        }
        catch (Exception ex) {
            assertEquals("Horses cannot be null.", ex.getMessage());
        }
    }
    @Test
    void constructorEmptyListException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(Collections.emptyList());
        });
    }
    @Test
    void constructorEmptyListMsgException() {
        try {
            new Hippodrome(Collections.emptyList());
        }
        catch (Exception e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }
    @Test
    void getHorses() {
        List<Horse> list = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            list.add(new Horse("Horse" + i, Math.random() * (80 - 60) + 60, Math.random() * 100));
        }
        Hippodrome hippodrome = new Hippodrome(list);
        assertEquals(list, hippodrome.getHorses());
    }
    @Test
    void move() {
        List<Horse> list = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            Horse horse = Mockito.mock(Horse.class);
            list.add(horse);
        }
        Hippodrome hippodrome = new Hippodrome(list);
        hippodrome.move();
        for(Horse horse: hippodrome.getHorses()) {
            Mockito.verify(horse).move();
        }
    }
    @Test
    void getWinner() {
        Horse horse1 = new Horse("Horse1", 65, 54);
        Horse horse2 = new Horse("Horse2", 55, 0);
        Horse horse3 = new Horse("Horse3", 75, 656);
        Horse horse4 = new Horse("Horse4", 70, 344);
        List<Horse> list = List.of(horse1, horse2, horse3, horse4);
        Hippodrome hippodrome = new Hippodrome(list);
        assertEquals(656, hippodrome.getWinner().getDistance());
    }
}