package baseNoStates.building;

import baseNoStates.Door;

import java.util.ArrayList;

public class Space extends Area{
    // A Space is a room which has door(s) that access to it.
    private Set<Door> doors;

    public Space(String name) {
        super(name);
        this.doors = new ArrayList<Door>();
    }
    public void addDoor(Door door){
        this.doors.add(door);
    }
    public ArrayList<Door> getDoors(){
        return this.doors;
    }
}
