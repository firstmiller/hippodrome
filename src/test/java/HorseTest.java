import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    @Test
    void constructorNullException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 10);
        });
    }
    @Test
    void constructorNullMsgException() {
        try {
            new Horse(null, 10);
        }
        catch (Exception ex) {
            assertEquals("Name cannot be null.", ex.getMessage());
        }
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "\n", "\t", " "})
    void constructorBlankStringException(String value) {
        assertThrows(IllegalArgumentException.class, () -> {new Horse(value, 10);});
    }
    @ParameterizedTest
    @ValueSource(strings = {"", "\n", "\t", " "})
    void constructorBlankStringMsgException(String value) {
        try {
            new Horse(value, 10);
        }
        catch (Exception e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }
    @Test
    void constructorNegativeSpeedException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Bucephalus", -34);
        });
    }
    @Test
    void constructorNegativeSpeedMsgException() {
        try {
            new Horse("Bucephalus", -34);
        }
        catch (Exception e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }
    @Test
    void constructorNegativeDistanceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Bucephalus", 60, -100);
        });
    }
    @Test
    void constructorNegativeDistanceMsgException() {
        try {
            new Horse("Bucephalus", 60, -100);
        }
        catch (Exception e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }
    @Test
    void getName() {
        assertEquals("Bucephalus", new Horse("Bucephalus", 60).getName());
        assertEquals("Bucephalus", new Horse("Bucephalus", 60, 100).getName());
    }
    @Test
    void getSpeed() {
        assertEquals(60, new Horse("Bucephalus", 60).getSpeed());
        assertEquals(60, new Horse("Bucephalus", 60, 100).getSpeed());
    }
    @Test
    void getDistance() {
        assertEquals(100, new Horse("Bucephalus", 60, 100).getDistance());
        assertEquals(0, new Horse("Bucephalus", 60).getDistance());
    }
    @Test
    void getRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic =  Mockito.mockStatic( Horse.class)) {
            Horse horse = new Horse("Bucephalus", 60, 100);
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @CsvSource({"60, 100, 0.2", "65, 40, 0.3", "58, 20, 0.65", "76, 35, 0.8"})
    void move(double speed, double distance, double rezRandomDouble) {
        try(MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(rezRandomDouble);
            Horse horse = new Horse("Bucephalus", speed, distance);
            horse.move();
            assertEquals(distance + speed * rezRandomDouble, horse.getDistance());
        }
    }

}