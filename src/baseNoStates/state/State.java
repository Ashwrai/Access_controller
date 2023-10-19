package baseNoStates.state;

import baseNoStates.Door;

public abstract class State {
    // Class State is used to create new States to add to the door, each action allowed to perform have its method in
    // each state to handle it.
    protected Door door;

    State(Door door){
        this.door=door;
    }

    public abstract void open();
    public abstract void close();
    public abstract void unlockShortly();
    public abstract void lock();
    public abstract void unlock();
    public abstract String getName();


}
