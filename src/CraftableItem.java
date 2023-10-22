import java.util.ArrayList;
import java.util.Optional;

public class CraftableItem extends Item {
    public CraftableItem(ItemDefinition itemDefinition) {
        super(itemDefinition);
    }

    public CraftableItem create() {
        CraftableItem item = new CraftableItem(getDefinition());
        generateSubComponents();
        return item;
    }

    public void generateSubComponents(){ 
        for (String componentName : this.getDefinition().getComponent()) {
            Optional<ItemDefinition> componentDefOpt = ItemDictionary.get().defByName(componentName);
            if (componentDefOpt.isPresent()) {
                ItemDefinition componentDef = componentDefOpt.get();
                Item sub_item = componentDef.create();
                addSubComponent(sub_item);       
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
