package baseNoStates.state;

import baseNoStates.Door;

public class Locked extends State {
    public Locked(Door door) {
        super(door);
    }

    @Override
    public State lock() {
        System.out.println("already locked");
        return this;
    }

    @Override
    public State unlocked() {
        return new Unlocked(door);
    }

    @Override
    public String asString() {
        return "locked";
    }
}
