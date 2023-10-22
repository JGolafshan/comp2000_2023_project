import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

public class Craft implements CraftInterface {
    private final Inventory inventory;

    public Craft(Inventory inventory) {
        this.inventory = inventory;
    }

    public void craft(ItemDefinition itemDefinition) throws ItemNotAvailableException {
        CraftableItem craftableItem = new CraftableItem(itemDefinition);
        
        for (String componentName : itemDefinition.getComponent()) {
            ArrayList<ItemInterface> items_in_inventory = inventory.searchItems(componentName);
            if(!items_in_inventory.isEmpty()){
                ItemDefinition item_found = items_in_inventory.get(0).getDefinition();
                ItemInterface componentItem = inventory.removeOne(item_found);
                craftableItem.addSubComponent(componentItem);
            } else {
                returnItemsToInventory(craftableItem.getSubComponents());
                throw new DatabaseReaderException(componentName+" Not Found");
            }
        }
        inventory.addOne(craftableItem);
    }

    @Override
    public void unCraft(Item item) throws ItemNotAvailableException {
        CraftableItem craftableItem = (CraftableItem) item;
        returnItemsToInventory(craftableItem.getSubComponents());
        inventory.remove(item);
    }

    private void returnItemsToInventory(Iterable<ItemInterface> items) {
        for (ItemInterface item : items) {
            inventory.addOne(item);
        }
    }
}
