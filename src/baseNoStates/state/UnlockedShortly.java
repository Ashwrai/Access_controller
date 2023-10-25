package baseNoStates.state;

import baseNoStates.Door;

import java.util.Observable;
import java.util.Observer;



// Represents the UnlockedShortly state of a door, observing changes from a Clock instance.

public class UnlockedShortly extends State implements Observer {

    private int timer; // Timer counter.
    private static Clock clock; // Shared clock instance.
    private static final int MAX_TIME = 10; // Maximum time before changing the door state.

    // Constructor that initializes the state and registers with the clock.
    UnlockedShortly(Door door) {
        super(door);
        timer=0;
        if(UnlockedShortly.clock==null){
            UnlockedShortly.clock=new Clock(1);
            UnlockedShortly.clock.start();
        }
        UnlockedShortly.clock.addObserver(this);
    }

    // Called when the observed Clock notifies of changes.
    public void update(Observable observable, Object arg){
        if(timer<MAX_TIME){
            timer++;
        } else {
            if(!door.isClosed()) {
                door.setState(new Propped(door));
            } else {
                door.setState(new Locked(door));
            }
            UnlockedShortly.clock.deleteObserver(this);
        }
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
    public void unlock() {
        System.out.println("already unlocked");
    }

    @Override
    public void lock() {
        if (door.isClosed())
            door.setState(new Locked(door));
        else
            System.out.println(door.getId() + " can't be locked, it's open");
    }


    @Override
    public String getName() {
        return "unlocked_shortly";
    }
}
