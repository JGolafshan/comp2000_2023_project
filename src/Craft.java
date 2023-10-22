import java.util.Optional;
public class Craft implements CraftInterface {
    private Inventory inventory;

    public Craft(Inventory select_inventory) {
        inventory = select_inventory;
    }

    public void craft(ItemDefinition itemDefinition) throws ItemNotAvailableException {
        // Create a CraftableItem from the item definition
        CraftableItem craftableItem = new CraftableItem(itemDefinition);
    
        // Get the sub-components required to craft the item
        String[] componentNames = itemDefinition.getComponent();
    
        for (String componentName : componentNames) {
            // Find the corresponding item definition for the component
            Optional<ItemDefinition> componentDefOpt = ItemDictionary.get().defByName(componentName);
            if (componentDefOpt.isPresent()) {
                ItemDefinition componentDef = componentDefOpt.get();
                try{
                    // Retrieve the component item from the player's inventory
                    ItemInterface componentItem = inventory.removeOne(componentDef);
                    // Add the component to the craftable item
                    craftableItem.addSubComponent(componentItem);
                    // Remove the component from the inventory

                } catch (ItemNotAvailableException e){
                    for (ItemInterface item : craftableItem.getSubComponents()) {
                        inventory.addOne(item);
                    }
                    throw new ItemNotAvailableException(componentDef);
                }
            }
        }
        // Add the crafted item to the inventory
        inventory.addOne(craftableItem);
    }

    @Override
    public void unCraft(Item item) throws ItemNotAvailableException {
        CraftableItem craftableItem = (CraftableItem) item;
        // Remove sub-components from the crafted item and add them back to the inventory
        for (ItemInterface component : craftableItem.getSubComponents()) {

            inventory.addOne(component);
        }
        // Remove the crafted item from the inventory
        inventory.remove(item);
    }
}
