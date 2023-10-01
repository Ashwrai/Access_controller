package baseNoStates.state;

import baseNoStates.Door;

public class Unlocked extends State {

    Unlocked(Door door) {
        super(door);
    }



    @Override
    public void unlocked() {
        System.out.println("already unlocked");
    }

    @Override
    public void lock() {
        door.setState(new Locked(door));
    }
    @Override
    public String asString() {
        return "unlocked";
    }


}
