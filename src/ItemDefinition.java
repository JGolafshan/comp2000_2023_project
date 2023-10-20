import java.util.ArrayList;
import java.util.Optional;

public class ItemDefinition {
    private String name;
    private String description;
    private String[] componentNames;
    private boolean isBaseItem;
    private ItemDictionary dict;
    private Optional<Double> weight;
    private ArrayList<ItemInterface> subComponents;

    public ItemDefinition(String n, String desc, Optional<Double> weightIfBase, String[] components) {
        name = n;
        description = desc;
        componentNames = components;
        subComponents = new ArrayList<>();
        isBaseItem = weightIfBase.isPresent();
        weight = weightIfBase;
        dict = ItemDictionary.get();
        // This may be helpful for the compsite pattern to find the appropriate item definitions
        
    }    

    /**
     * Create an instance of the item described by this ItemDefinition.
     * If the Item is made up of other items, then each sub-item should also be created.
     * @return An Item instance described by the ItemDefinition
     */
    public Item create() {
        Item item = new Item(this);
        if(getSubComponents().size() == 0){
            setSubComponents();
        }
  
        // An ItemDefinition for a craftable item might follow a similar pattern
        // to how a craftable/composite item looks.
        return item;
    }

    // ItemDefinition might "craft" and return an item, using items from some source inventory.
    // You might use the Milestone 1 Basket transaction code as a guide

    public String getName() {
        return name;
    }

    public ItemDictionary getDictionary(){
        return dict;
    }

    public String[] getComponent(){
        return componentNames;
    }

    public String getDescription() {
        return description;
    }

    public void setSubComponents(){
        subComponents.clear();
        if (!isBaseItemDef()) {
                for (String componentName : getComponent()) {
                    ItemInterface component = getDictionary().defByName(componentName).get().create();
                    subComponents.add(component);
                }
            }
    }

    public ArrayList<ItemInterface> getSubComponents(){
        return subComponents;
    }

    /**
     * Format: {ITEM 1}, {ITEM 2}, ...
     * @return a String of sub-item/component names in the above format
     */
    public String componentsString() {
        String output = "";
        for (String componentName : componentNames) {
            output += componentName + ", ";
        }
        return output;
    }

    public boolean isBaseItemDef() {
        return isBaseItem;
    }

    public Optional<Double> getWeight() {
        return weight;
    }

    public boolean equals(Item other) {
        return isOf(other.getDefinition());
    }

	public boolean isOf(ItemDefinition def) {
		return getName().equals(def.getName());
	}

}
