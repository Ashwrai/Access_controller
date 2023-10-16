package baseNoStates.roles;
import java.time.LocalTime;
import java.util.Arrays;


public class Employee extends Role{
    public Employee() {
        permissions.add(new Permission(
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"),
                Arrays.asList("open", "close", "unlock shortly"),
                Arrays.asList(
                        "exterior",
                        "stairs",
                        "hall",
                        "room 1",
                        "room 2",
                        "corridor",
                        "room 3",
                        "IT"
                )
        ));
    }
}
