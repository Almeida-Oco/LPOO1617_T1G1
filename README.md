# INTERMEDIATE CHECK-POINT

---
## Architecture Design
<br />

![UML Diagram](https://github.com/Almeida-Oco/LPOO1617_T1G1/blob/finalproject/UML.png "UML Diagram")



### **Packages**
### Controller
<dl>
   <dt>Logic</dt>
      <dd>Will be responsible for all the logic of the game, including collisions between entities, updating and verifying the positions of every entity to name a few.</dd>
   <dt>Map</dt>
      <dd>Extends TiledMap to be able to load maps created with Tiled software. Map will hold a collection of Stairs and Floor which will check collisions with the foreground of the Map.</dd>
   <dt>Floor </dt>
      <dd>Represents a single block of the floor, the representation ad position may change depending on which level the Player is in.</dd>
   <dt>Stairs</dt>
      <dd>Represents a single block of stairs, all stairs are climbable by Mario.</dd>
   <dt>Sounds</dt>
      <dd>Will hold the music and sounds that the game will have.</dd>
</dl>

### Model
<dl>
   <dt>Entity</dt>
     <dd>Merely an interface which will contain the "rules" for adding a new Entity to the game.</dd>
   <dt>Fire</dt>
      Represents a single Fire of the game, the fires are able to go up and down a Stair and depending on the level may or may not try to follow Mario.
   <dt>Mario</dt>
      <dd>The Hero of the game, as such there can only be one instance of him. Mario can get PowerUps namely its famous Hammer and we are pondering whether to add more PowerUps or not.</dd>
   <dt>Donkey Kong</dt>
      <dd>The Villain of the game, will mostly hold the animations of him.</dd>
   <dt>Barrel</dt>
      <dd>Represents a single barrel which is thrown by DonkeyKong, these can have different behaviours and representation based on the current level, hence the Strategy pattern.</dd>
   <dt>Strategy</dt>
      <dd>Class which will hold the different strategies of various entities.</dd>
   <dt>Equip</dt>
      <dd>Represents a PowerUp that Mario can get which might represent either a Hammer or a different PowerUp</dd>
</dl>
### Menu
<dl>
   <dt>Menu</dt>
      <dd>Responsible for the Menus present in the game, namely Main Menu and Network Menu.</dd>
</dl>

### NetworkIO
<dl>
   <dt>NetIO</dt>
      <dd>Used for communications between clients.</dd>
</dl>

### Test
<dl>
   <dt>LogicTests</dt>
      <dd>Tests of the Logic package</dd>
   <dt>ModelTests</dt>
      <dd>Tests of the Model package</dd>
</dl>

### View
<dl>
   <dt>GameView</dt>
      <dd>Responsible for drawing all the elements on the screen</dd>
</dl>

<br />

### **Design Patterns**
<dl>
   <dt>FLYWEIGHT</dt>
      <dd>Barrels share the same sprites between them</dd>
   <dt>SINGLETON</dt>
      <dd>There can only exist one Mario, one Donkey Kong and one GameLogic</dd>
   <dt>STRATEGY</dt>
      <dd>Both the Barrels and the Fires have different behaviours throughout the game</dd>
   <dt>STATE</dt>
      <dd>Mario PowerUps are a State themselves</dd>
</dl>


<br />

----
## GUI Design

The GUI will be very simple with the Main Menu and Multiplayer Menu:

[Mockup Diagram](https://github.com/Almeida-Oco/LPOO1617_T1G1/blob/finalproject/Mockup.jpg "GUI Mockup")

### Main Menu
-  **Single Player**
   Allows the player to start a single player game much alike the arcade one.

- **Multiplayer** 
Switches to Multiplayer Menu which allows the user to choose whether to host a game or to join one. 

- **Exit** 
Simply exit the game.

### Multiplayer Menu
- **Host** Allows the player to host a multiplayer game which will display an IP which the other player must insert.

- **Join** Allows the player to join a pending game by inserting the IP shown in the other player display.
----
## Test Design
### Logic
* Win and lose scenarios
* Colisions with floor/ceiling
* Mario gets Power Ups
* Mario goes up ladder or fails to do so
* Barrels fall in all possible places
* Fires are able to move up and down stairs
* All types of barrels created and thrown
* Fire follows Mario
* Update score
* PowerUp effects

### Entities 
* Moves entity correctly

### Map
* Map loads correctly

### NetworkIO
* Packets sent correctly
