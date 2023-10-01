package baseNoStates.state;

import baseNoStates.Door;

public abstract class State {

    protected Door door;

    State(Door door){
        this.door=door;
    }


    public abstract void lock();
    public abstract void unlocked();
    public abstract String asString();


}
