package baseNoStates.building;

import baseNoStates.Door;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Space extends Area{
    // - Purpose:
    //    Represents a single room or space
    //    in the building which can be accessed through one or more doors.
    private Set<Door> doors;

    public Space(String name) {
        super(name);
        this.doors = new HashSet<>();
    }

    public void accept(final AreaVisitor visitor) {
        visitor.visit(this);
    }
    public void addDoor(Door door){
        this.doors.add(door);
    }
    public Set<Door> getDoors(){
        return this.doors;
    }
}
