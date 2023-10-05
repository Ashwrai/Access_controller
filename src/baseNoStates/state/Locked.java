package baseNoStates.state;

import baseNoStates.Door;

import java.util.Timer;
import java.util.TimerTask;

public class Locked extends State {
    public Locked(Door door) {
        super(door);
    }

    public  void open(){
        System.out.println("Can't open door " + door.getId() + " because it's locked");
    }

    public void close(){
        System.out.println("Can't close door " + door.getId() + " because it's already closed");
    }

    public void unlockShortly(){
        door.setState(new UnlockedShortly(door));
    }

    @Override
    public void lock() {
        System.out.println(door.getId() + " is already locked");
    }

    @Override
    public void unlock() {
        door.setState(new Unlocked(door));
    }

    @Override
    public String getName() {
        return "locked";
    }
}
