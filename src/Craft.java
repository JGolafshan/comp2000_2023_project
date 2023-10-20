import java.util.Optional;
import java.util.ArrayList;

public class Craft implements CraftInterface {
    private Inventory inventory;

    public Craft(Inventory select_inventory) {
        inventory = select_inventory;  
    }

    @Override
    public void craft(ItemDefinition item) throws ItemNotAvailableException {
        ArrayList<ItemInterface> items = item.getSubComponents();
        ArrayList<ItemInterface> transcation = new ArrayList<>();

        for (ItemInterface sub_item : items) {
            try{
                System.out.println(sub_item.toString());
                transcation.add(inventory.removeOne(sub_item.getDefinition()));

            } catch (ItemNotAvailableException e){
                for (ItemInterface transcation_item : transcation) {
                    inventory.addOne(transcation_item);
                }
                throw new ItemNotAvailableException(sub_item.getDefinition());
            }
        }
        inventory.addOne(item.create());
    }    

    @Override
    public void unCraft(Item item) throws ItemNotAvailableException {
        for (ItemInterface item_sub : item.getDefinition().getSubComponents()) {
            inventory.addOne(item_sub); 
            
        }
        inventory.removeOne(item.getDefinition());
    }
}



    // @Override
    // public void craft(ItemDefinition item) throws ItemNotAvailableException {
    //     ArrayList<ItemInterface> items = item.getSubComponents();
    //     ArrayList<ItemInterface> transcation = new ArrayList<>();

    //     for (ItemInterface sub_item : items) {
    //         try{
    //             transcation.add(inventory.removeOne(sub_item.getDefinition()));

    //         } catch (ItemNotAvailableException e){
    //             for (ItemInterface transcation_item : transcation) {
    //                 inventory.addOne(transcation_item);
    //             }
    //             throw new ItemNotAvailableException(sub_item.getDefinition());
    //         }
    //     }
    //     inventory.addOne(item.create());
    // }    
