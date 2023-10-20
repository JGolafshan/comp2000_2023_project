public interface CraftInterface {
    void craft(ItemDefinition item) throws ItemNotAvailableException;;
    void unCraft(Item item ) throws ItemNotAvailableException;;
}