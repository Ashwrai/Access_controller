package baseNoStates.state;

import baseNoStates.Door;

import java.util.Timer;
import java.util.TimerTask;

public class Propped extends State {

    Propped(Door door) {
        super(door);
    }

    @Override
    public void open() {
        System.out.println(door.getId() + " is open, it's propped");
    }

    @Override
    public void close() {
        door.setClose(true);
        door.setState(new Locked(door));
    }

    @Override
    public void unlockShortly() {
        System.out.println("Door " + door.getId() + " is open, it's propped");
    }

    @Override
    public void lock() {
        System.out.println("Can't lock, door " + door.getId() + "is open because it's propped");
    }

    @Override
    public void unlock() {
        System.out.println("Door " + door.getId() + "is open, it's propped");
    }

    @Override
    public String getName() {
        return "propped";
    }


}
