package baseNoStates.state;

import baseNoStates.Door;

import java.util.Timer;
import java.util.TimerTask;


// This class represents the UnlockedShortly state of a door.

public class UnlockedShortly extends State{
    UnlockedShortly(Door door2) {
        super(door2);
        Timer timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (door.isClosed())
                    door.setState(new Locked(door));
                else {
                    door.setState(new Propped(door));
                    System.out.println("Can't lock door " + door.getId() + "Because is open/propped");
                }
            }
        }, 10000);
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
