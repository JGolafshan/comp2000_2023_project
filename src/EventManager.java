import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EventManager {
    private PropertyChangeSupport propertyChangeSupport;

    public EventManager() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void subscribe(String eventName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(eventName, listener);
    }

    public void unsubscribe(String eventName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(eventName, listener);
    }

    public void publishEvent(String eventName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(eventName, oldValue, newValue);
    }
}
