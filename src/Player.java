import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
public class Player {
    private String name;
    private Inventory inventory;
    private double carryWeightCapacity;
    private EventManager eventManager;
    private Inventory storageView;

    public Player(String playerName, double carryCapacity, Inventory sInventory, EventManager eventManager) {
        name = playerName;
        carryWeightCapacity = carryCapacity;
        inventory = sInventory;
        this.eventManager = eventManager;
        subscribeToEvents(); // every time a player is created it subscribed to both Item: add and remove
    }

    public void setStorageView(Inventory storageInventory) {
        storageView = storageInventory;
    }

    public Inventory getStorageView() {
        return storageView;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public double getCarryCapacity() {
        return carryWeightCapacity;
    }

    public double getCurrentWeight() {
        double carrying = 0;
        for (ItemInterface item : getInventory().searchItems("")) {
            carrying += item.getWeight();
        }
        return carrying;
    }
    
    public void store(ItemInterface item, Storage storage) throws ItemNotAvailableException {
        // Do we have the item we are trying to store
        if (!inventory.searchItems("").contains(item)) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        inventory.remove(item);
        System.out.println(name + " added " + item.getName() + " to their storage.");
        //let the subscribes know something should be updated
        eventManager.publishEvent("itemAdded", null, item);
        
        
    }

    public void retrieve(ItemInterface item, Storage storage) throws ItemNotAvailableException, ExceedWeightCapacity {
        // Does the Storage have the item we are trying to retrieve
        if (!storageView.searchItems("").contains(item)) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        if (getCurrentWeight() + item.getWeight() > getCarryCapacity()) {
            throw new ExceedWeightCapacity(this, item);
        }
        inventory.addOne(item);
        System.out.println(name + " removed " + item.getName() + " to their storage.");
        //let the subscribes know something should be updated
        eventManager.publishEvent("itemRemoved", null, item);
        
        
    }

    private void subscribeToEvents() {
        eventManager.subscribe("itemAdded", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                ItemInterface newItem = (ItemInterface) evt.getNewValue();
                System.out.println(name + " received itemAdded event: " + newItem.getName());
                storageView.addOne(newItem);
    
   
                
            }
        });
        eventManager.subscribe("itemRemoved", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                ItemInterface item = (ItemInterface) evt.getNewValue();
                try{
                    storageView.removeOne(item.getDefinition());
                } catch(ItemNotAvailableException e){
                    System.out.println("Item already Removed");
                }
                System.out.println(name + " received itemRemoved event: " + item.getName());
                
            }
        });
    }
}
