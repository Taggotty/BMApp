package core;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;

public class Ownership implements Serializable {

    private static final long serialVersionUID = -7661351367355134140L;
    private State state;
    private String lend;

    public Ownership() {
        this.state = State.UNOWNED;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setLend(String lend) {
        this.lend = lend;
    }

    public State getState() {
        return state;
    }

    public String getLend() {
        return lend;
    }

    @Override
    public String toString() {
        if (state.equals(State.OWNED) || state.equals(State.UNOWNED))
            return state.toString();
        else
            return state.toString() + this.lend;
    }


    public enum State {
        OWNED {
            @Override
            public String toString() {
                return "Im Besitz.";
            }
        }, LEND {
            @Override
            public String toString() {
                return "Geliehen von ";
            }
        }, UNOWNED {
            @Override
            public String toString() {
                return "Nicht im Besitz.";
            }
        }, LENT {
            @Override
            public String toString() {
                return "Entliehen an.";
            }
        }
    }
}
