import java.util.Optional;

public class CraftableItem extends Item {
    public CraftableItem(ItemDefinition itemDefinition) {
        super(itemDefinition);
    }

    public CraftableItem create() {
        CraftableItem item = new CraftableItem(getDefinition());
        generateSubComponents(item);
        return item;
    }

    public void generateSubComponents(Item item_base){ 
        for (String componentName : this.getDefinition().getComponent()) {
            Optional<ItemDefinition> componentDefOpt = ItemDictionary.get().defByName(componentName);
            if (componentDefOpt.isPresent()) {
                ItemDefinition componentDef = componentDefOpt.get();
                Item sub_item = componentDef.create();
                item_base.addSubComponent(sub_item);       
            } else {
                throw new DatabaseReaderException(componentName+" Not Found");
            }
        }  
    }

    @Override
    public double getWeight() {
        double weight = super.getWeight();
        for (ItemInterface component : getSubComponents()) {
            weight += component.getWeight();
        }
        return weight;
    }
}
