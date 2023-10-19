package baseNoStates.building;

import baseNoStates.Door;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Space extends Area{
    // A Space is a room which has door(s) that access to it.
    private Set<Door> doors;

    public Space(String name) {
        super(name);
        this.doors = new HashSet<>();
    }
    public void addDoor(Door door){
        this.doors.add(door);
    }
    public Set<Door> getDoors(){
        return this.doors;
    }
}
