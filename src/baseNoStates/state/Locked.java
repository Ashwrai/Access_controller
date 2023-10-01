package baseNoStates.state;

import baseNoStates.Door;

public class Locked extends State {
    public Locked(Door door) {
        super(door);
    }

    @Override
    public void lock() {
        System.out.println("already locked");
    }

    @Override
    public void unlocked() {
        door.setState(new Unlocked(door));
    }

    @Override
    public String asString() {
        return "locked";
    }
}
