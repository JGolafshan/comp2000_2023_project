/*
 *
 * Reads in the input configuration file, setting up and returning an App instance.
 * This file does not need to be modified, and is here if you wish to look around.
 * 
 * File format:
 * 
 * - base items
 * {NAME}, {DESCRIPTION}, {WEIGHT}
 * 
 * - craftable items
 * {NAME}, {DESCRIPTION}, {COMPONENT 1}, {COMPONENT 2}, ...
 * 
 * - store
 * {STORAGE NAME}
 * {ITEM NAME}, {QTY}
 * {ITEM NAME}, {QTY}
 * ...
 * 
 * - player
 * {WEIGHT CAPACITY}
 * {ITEM NAME}, {QTY}
 * {ITEM NAME}, {QTY}
 * ...
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Reader {
    private static boolean baseItemsRead = false;
    private static boolean craftableItemsRead = false;
    private static boolean storeRead = false;
    private static boolean playerRead = false;

    private static final String BASE_ITEMS_HEADER = "base items";
    private static final String CRAFTABLE_ITEMS_HEADER = "craftable items";
    private static final String STORE_HEADER = "store";
    private static final String PLAYER_HEADER = "player";
    private static EventManager eventManager = new EventManager();

    public static App read(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        ArrayList<ItemDefinition> itemDefinitions = ItemDictionary.get().getDefs();
        ArrayList<Storage> stores = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();

        String line = "";
    
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (!line.isEmpty() && line.charAt(0) == '-') {
                if (line.endsWith(BASE_ITEMS_HEADER)) {
                    readBaseItemDefinitions(scanner, itemDefinitions);
                } else if (line.endsWith(CRAFTABLE_ITEMS_HEADER)) {
                    readCraftableItemDefinitions(scanner, itemDefinitions);
                } else if (line.endsWith(STORE_HEADER)) {
                    stores.add(readStorage(scanner, itemDefinitions));
                } else if (line.endsWith(STORE_HEADER+"2")) {
                    stores.add(readStorage(scanner, itemDefinitions));
                }else if (line.endsWith(STORE_HEADER+"3")) {
                    stores.add(readStorage(scanner, itemDefinitions));
                } else if (line.endsWith(PLAYER_HEADER)) {
                     players.add(readPlayer(scanner, itemDefinitions));
                } else if (line.endsWith(PLAYER_HEADER+"2")) {
                     players.add(readPlayer(scanner, itemDefinitions));
                }else if (line.endsWith(PLAYER_HEADER+"3")) {
                     players.add(readPlayer(scanner, itemDefinitions));
                }
            }
        }

        for(int i=0; i < players.size(); i++){
            new App(players.get(i), stores.get(i));
        }

        return null;
    }

    private static boolean duplicateItemName(String name, ArrayList<ItemDefinition> defs) {
        for (ItemDefinition def : defs) {
            if (def.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static Optional<ItemDefinition> getItemDef(String itemName, ArrayList<ItemDefinition> defs) {
        Optional<ItemDefinition> result = Optional.empty();
        for (ItemDefinition def : defs) {
            if (def.getName().equals(itemName)) {
                result = Optional.of(def);
                break;
            }
        }
        return result;
    }

    private static String parseString(String str_value){
        return str_value.trim();
    }

    private static Double parseDouble(String double_value){
        return Double.parseDouble(double_value.trim());
    }

    private static Integer parseInteger(String integer_value){
        return Integer.valueOf(integer_value.trim());
    }


    // line format
    // {NAME}, {DESCRIPTION}, {WEIGHT}, ...
    private static void readBaseItemDefinitions(Scanner sc, ArrayList<ItemDefinition> defs) {
        if (Reader.baseItemsRead) {
            throw new IllegalStateException("Base Items in data file are not stored together");
        }
        Reader.baseItemsRead = true;
        
        String itemLine = sc.nextLine();
        do {
            String[] parts = itemLine.split(",");

            String name = parseString(parts[0]);
            String description = parseString(parts[1]);
            double weight = parseDouble(parts[2]);

            // Disallow duplicate item names
            if (Reader.duplicateItemName(name, defs)) {
                throw new IllegalStateException("The item name" + name + "is used multiple times");
            }

            ItemDefinition def = new ItemDefinition(name, description, Optional.of(weight), new String[0]);
            defs.add(def);
            itemLine = sc.nextLine();
        } while (sc.hasNextLine() && !itemLine.isEmpty());

    }

    // line format
    // {NAME}, {DESCRIPTION}, {COMPONENT 1}, {COMPONENT 2}, ...
    private static void readCraftableItemDefinitions(Scanner sc, ArrayList<ItemDefinition> defs) {
        if (Reader.craftableItemsRead) {
            throw new IllegalStateException("Craftable in data file are not stored together");
        }
        Reader.craftableItemsRead = true;

        String itemLine = sc.nextLine();
        do {
            String[] parts = itemLine.split(",");
            
            String name = parseString(parts[0]);
            String description = parseString(parts[1]);
            String[] components = new String[parts.length - 2];

            for (int i = 2; i < parts.length; i++) {
                components[i - 2] = parseString(parts[i]);
            }

            // Disallow duplicate item names
            if (Reader.duplicateItemName(name, defs)) {
                throw new IllegalStateException("The item"+name+"is defined multiple times");
            }

            ItemDefinition itemDefinition = new ItemDefinition(name, description, Optional.empty(), components);
            defs.add(itemDefinition);
            itemLine = sc.nextLine();
        } while (sc.hasNextLine() && !itemLine.isEmpty());

    }

    // line format: {STORAGE NAME}, {ITEM NAME}, {QTY}, {ITEM NAME}, {QTY}, ...
    private static Storage readStorage(Scanner sc, ArrayList<ItemDefinition> itemDefinitions) {
        if (Reader.storeRead) {
            //throw new IllegalStateException("Store written twice or more in data file");
        }
        //Reader.storeRead = true;

        String name = sc.nextLine().trim();
        Inventory startingInventory = readStartingItems(sc, itemDefinitions);
        Storage storage = new Storage(name, startingInventory);
        return storage;
    }

    // line format; {WEIGHT CAPACITY}, {ITEM NAME}, {QTY}, {ITEM NAME}, {QTY}, ...
    private static Player readPlayer(Scanner sc, ArrayList<ItemDefinition> items) {
        if (Reader.playerRead) {
            //throw new IllegalStateException("Player written twice or more in data file");
        }
        //Reader.playerRead = true;
        int randomNum = ThreadLocalRandom.current().nextInt(0, 999);
        String name = "Player: "+ randomNum ;
        double carryCapacity = Double.valueOf(sc.nextLine());
        Inventory startingInventory = readStartingItems(sc, items);
        return new Player(name, carryCapacity, startingInventory, eventManager);
    }

    /**
     * Line format:
     * {NAME | WEIGHT CAPACITY}, {ITEM NAME}, {QTY}, {ITEM NAME}, {QTY}, ...
     * @param data - The result of splitting the `player` or `store` line of the config by ","
     * @return
     */
    private static Inventory readStartingItems(Scanner sc, ArrayList<ItemDefinition> itemDefinitions ) {
        Inventory startingInventory = new Inventory();
        String line = sc.nextLine();
        
        do {
            String[] data = line.split(",");
            String name = parseString(data[0]);
            int qty = parseInteger(data[1]);
    
            getItemDef(name, itemDefinitions).ifPresentOrElse(
                (def) -> {
                    for (int i = 0; i < qty; i++) {
                        startingInventory.addOne(def.create());
                    }
                },
                () -> {
                    throw new IllegalStateException("Bad starting item '" + name + "' was read. Exiting early");
                });
            line = sc.nextLine();
        } while (sc.hasNextLine() && !line.isEmpty());
        return startingInventory;
    }
}