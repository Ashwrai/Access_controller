package baseNoStates.state;

import baseNoStates.Door;

import java.util.Timer;
import java.util.TimerTask;

// This class represents the unlocked state of a door.

public class Unlocked extends State {

    Unlocked(Door door) {
        super(door);
    }

    @Override
    public void open() {
        if (door.isClosed())
            door.setClose(false);
        else
            System.out.println("Can't open door " + door.getId() + " because it's already open");
    }

    @Override
    public void close() {
        if (!door.isClosed())
            door.setClose(true);
        else
            System.out.println(door.getId() + " is already closed");
    }

    @Override
    public void unlockShortly() {
        door.setState(new UnlockedShortly(door));
    }

    @Override
    public void lock() {
        if (door.isClosed())
            door.setState(new Locked(door));
        else
            System.out.println(door.getId() + " can't be locked, it's open");
    }

    @Override
    public void unlock() {
        System.out.println("already unlocked");
    }


    @Override
    public String getName() {
        return "unlocked";
    }


}
