package baseNoStates.state;

import baseNoStates.Door;

public class Unlocked extends State {

    Unlocked(Door door) {
        super(door);
    }

    @Override
    public State lock() {
        return new Unlocked(door);
    }

    @Override
    public State unlocked() {
        System.out.println("already unlocked");
        return this;
    }

    @Override
    public String asString() {
        return "unlocked";
    }
}
