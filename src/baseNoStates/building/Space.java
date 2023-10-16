package baseNoStates.building;

import baseNoStates.Door;

import java.util.ArrayList;

public class Space extends Area{

    private ArrayList<Door> doors;

    public Space(String name) {
        super(name);
    }
}
