# Bugs/Thigs that need fixing
1. You can change the text on retrive and store
2. allow n players to be added
3. create some kind of default inventery for players

# Task 1
Behavioural pattern - Options: *Strategy* or *Observer* pattern.
You chose: <Observer>

## Itemise changes made (which class files were modified)
1. created a new class EventManager
2. added import statements to Player.java
3. created a new field in Player.java callede EventManager
4. constructor has a new variable that type a eventManager object.
5. constructor also calls subscribeToEvents();
6. store now remove the item from the inventory of the person that pressed the store button and send an event to the eventManger ask all the other players to up their storages
7. retreive now remove the item from the players storage of the person that press the retreive and and sends an event to the eventManager to ask all the other player to update there storage
8. subscribeToEvents receives the events and then asks all players subscribed to the event to do something.
9. In reader there is a new feild called eventMaager it is used by all players 
10. the player name is now "Player" plus a random int value. 
11. The Player contruct has now be updated with eventManager field


# Task 2
Structural pattern - *Composite* pattern.

## Itemise changes made (which class files were modified)
1. getCompositionDescription() now returns a componentString instead of "", this populates the component box the item is not a base item.
2. created craftInterface 
3. created a class craft to handle craft and uncraft
4. Item now has a new method called add which adds a item to a sub-compoent array
5. ItemDefinition has two new methods and a new field, subComponets and a get and set for the new field.

# Task 3 

## First Improvement

## Itemised changes or new files
1. defined and intialized the boolean variables in the same line.
2. created constant values for "base items", "craftable items", "player" and "store".
3. created functions parseString, parseDouble, parseInteger. 
4. moved variables declaration of item values togather (name, desc, qty, e.c.t)   for clarity.
5. refactored readStartingItems function removed reduntent if statment converted while loop to do-while.
6. added IllegalStateException to code blocks that use system.exit(0) and removed the system.exit(0) from code.

## What was changed
1. Rewrite Reader.java


## Why it was changed
1. Had a lot of large functions which isnt nessaraly bad but it with in those functions thay where doing lots of different things

## The benefits of the change
1. improved readability 
2. improved reuseage  

##   Secound Improvement

## Itemised changes or new files
1. 

## What was changed
1. Used the decorate pattern on on weight.

## Why it was changed
1. Since itemDefinition is a composite function, its just used to create to pass the items values into Item.

## The benefits of the change
1. Makes Maintainability easier
2. Improves consistency 

