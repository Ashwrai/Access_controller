package baseNoStates.state;

import baseNoStates.Door;

public abstract class State {

    protected Door door;

    State(Door door){
        this.door=door;
    }

    public abstract void open();
    public abstract void close();
    public abstract void unlockShortly();
    public abstract void lock();
    public abstract void unlock();
    public abstract String asString();


}
