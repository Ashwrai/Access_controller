package baseNoStates.roles;

import java.time.LocalTime;
import java.util.Arrays;

public class Manager extends Role {
    public Manager() {
        permissions.add(new Permission(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
                Arrays.asList("open", "close", "unlock", "lock"), // Suponiendo que estas son todas las acciones posibles
                Arrays.asList("room1", "room2", "room3", "hall", "parking", "stairs", "exterior", "corridor", "IT")
        ));
    }
}
