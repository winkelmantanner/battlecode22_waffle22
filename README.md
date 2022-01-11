To run this, first get the BattleCode client working.  Then clone this next to examplefuncsplayer in the src folder in the scaffold.  Then name the new folder tannerplayer.  This is the folder structure you should have:
```
battlecode22-scaffold
    src
        examplefuncsplayer
        tannerplayer
```
I have multiple versions of the AI cloned in my src folder.  The client sees them as separate AIs so I can play them against each other.  To do that, you will need to make the name of the AI's folder match the package statement at the top of the AI's Java files.
