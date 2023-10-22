
# Task 1
Behavioural pattern - Options: *Strategy* or *Observer* pattern.
You chose: <Observer>

## Itemise changes made (which class files were modified)
1. created a new class EventManager
2. added import statements to Player.java
3. Created a new field in Player.java callede EventManager
4. constructor has a new variable that type a eventManager object.
5. constructor also calls subscribeToEvents();
6. store now remove the item from the inventory of the person who pressed the store button and send an event to the eventManger ask all the other players to up their storage.
7. Retreive now removes the item from the storage of the person that presses Retreive and sends an event to the event. Manager to ask all the other players to update their storage
8. subscribeToEvents receives the events and then asks all players subscribed to the event to do something.
9. In the reader, there is a new field called eventMaager that is used by all players.
10. The player name is now "Player" plus a random integer value.
11. The player contruct has now been updated with the eventManager field.


# Task 2
Structural pattern - *Composite* pattern.

## Itemise changes made (which class files were modified)
1. getCompositionDescription() now returns a componentString instead of ""; this populates the component box; the item is not a base item.
2. created a craft interface
3. added an arraylist in Item
4. added a field, ArrayList<ItemInterface> subComponents in item.java
3. created a class Craft.java to handle craft and uncraft
4. created a CraftableItem.java that inherits from Item
5. Both SetupCrafting and SetupUncrafting now use the methods from Craft.java in App.java.

# Task 3 

## First Improvement

## Itemised changes or new files
1. defined and initialized the boolean variables in the same line.
2. created constant values for "base items", "craftable items", "player" and "store".
3. created functions parseString, parseDouble, parseInteger.
4. moved variables declaration of item values togather (name, desc, qty, e.c.t.) for clarity.
5. refactored readStartingItems function removed reduntent if statment converted while loop to do-while.
7. created a new custom exception, DatabaseReaderException.java
6. added a DatabaseReaderException to code blocks that use system.exit(0) and removed the system.exit(0) from code.


## What was changed
1. Reader.java


## Why it was changed
1. Had a lot of large functions, which isnt necessarily bad, but in those functions, I where doing lots of different things

## The benefits of the change
1. improved readability 
2. improved reuseage  

##   Secound Improvement

## Itemised changes or new files
1. readStartingItems in reader were changed so that if the Item def is not a base item, it creates a craftable item instead.
2. new method in CraftableItem.java called generateSubComponents, it creates the subcomponents when a crafted item is added at the start of the application.

## What was changed
1. Reader.java
2. CraftableItem.java

## Why it was changed
1. because it was a bug 

## The benefits of the change
1. Items can now be loaded into a player's inventory of applications and uncrafted.
2. craftable items are created as craftableItems, not items.
3. weight is also calculated at the start of the application for crafted items.