package baseNoStates.state;

import baseNoStates.Door;

public abstract class State {

    Door door;

    State(Door door){
        this.door=door;
    }


    public abstract State lock();
    public abstract State unlocked();
    public abstract String asString();

}
