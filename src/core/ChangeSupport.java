package core;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ChangeSupport<T> {

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void fireEvent(String name, Object oldValue, Object newValue) {
        pcs.firePropertyChange(name, oldValue, newValue);
    }

    public void fireEvent(String name, Object newValue) {
        fireEvent(name, null, newValue);
    }
}
