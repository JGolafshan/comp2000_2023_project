# Bugs/Thigs that need fixing
1. You can change the text on retrive and store
2. allow n players to be added
3. create some kind of default inventery for players

# Task 1
Behavioural pattern - Options: *Strategy* or *Observer* pattern.
You chose: <Observer>

## Itemise changes made (which class files were modified)
1. The Player class now has an interface (PropertyChangeListener), which checks for changes in the storage 
2. Storage now used PropertyChangeSupport to send changes to each players storage, also added two functions add and remove Observer.
3. AddStorageObserver is used in Reader every time a players file is read it add that player to the storage

# Task 2
Structural pattern - *Composite* pattern.

## Itemise changes made (which class files were modified)
1. getCompositionDescription() now returns a componentString instead of "", this populates the component box the item is not a base item.
2. create functions craft and uncraft in the player class
3. updated app.java functions setCraftAction and setUncraftAction to the respective functions craft() and uncraft()
4. updated getWeight in player Item class so it returns both the wieght it is a base object or a sum of all object weights

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
1. 

## Why it was changed
1. 

## The benefits of the change
1. 



## Itemised changes or new files
1. 

## What was changed
1. 

## Why it was changed
1. 

## The benefits of the change
1. 