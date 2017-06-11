
# INTERMEDIATE CHECK-POINT

---
## Architecture Design
<br />

### **Packages**
### Controller
<dl>
   <dt>GameLogic</dt>
      <dd>Translates input from Android to commands to be given to model</dd>
</dl>

### Model
<dl>
   <dt>Entity</dt>
     <dd>An abstract class which represents a single entity of the game, has what an Entity should implement and common methods between them</dd>
   <dt>Mario</dt>
      <dd>Abstract class which represents Mario, classes which extend this one are the various states Mario could be in</dd>
   <dt>MarioRun</dt>
      <dd>State in which Mario is running, meaning it can transition into every other state</dd>
   <dt>MarioClimb</dt>
      <dd>State in which Mario is climbing, meaning it can only transition to the death state and run state after finishing climbing the ladder</dd>
   <dt>MarioFall</dt>
      <dd>State in which Mario is falling, meaning it can only transition to the death state and run state after finishing the fall</dd>
   <dt>MarioJump</dt>
      <dd>State in which Mario is jumping, meaning it can only transition to the death state and run state after finishing the jump</dd>
   <dt>MarioJump</dt>
      <dd>State in which Mario is dying, meaning it can not transition into any other state, merely used for animations purposes</dd>
      <dt>Fire</dt>
      <dd>Represents a single Fire from the game, the fire has 2 different behaviours which can be swapped at run-time, making it either smarter or dumber</dd>
   <dt>MovementStrategy</dt>
      <dd>Abstract class which represents what a movement strategy of the Fire should contain. It implements methods common between strategies such as moving horizontally and vertically</dd>
   <dt>SimpleMovement</dt>
      <dd>Extends MovementStrategy, used to make the Fire movement as simple as possible, meaning most of its movements are in a random direction and of random duration</dd>
   <dt>SmartMovement</dt>
      <dd>Extends MovementStrategy, used to make Fire movement follow Mario wherever he is on the map</dd>
   <dt>Donkey Kong</dt>
      <dd>The Villain of the game, will mostly hold the animations of him.</dd>
   <dt>Barrel</dt>
      <dd>Abstract class which represents what a barrel should implement, has implemented methods which are shared by all child classes, such as collision detection</dd>
   <dt>BarrelFall</dt>
      <dd>State in which barrel is falling, meaning it can only transition to Rolling state after finishing the fall. The fall can be free-falling meaning it falls all the way to the end of the map, or a simple fall which is a transition between crane levels</dd>
   <dt>BarrelRolling</dt>
      <dd>State in which barrel is rolling, while it rolls every time it passes a ladder it may or may not choose to go down that ladder, in which case it transitions to the Fall state</dd>
   <dt>DonkeyKong</dt>
      <dd>Represents the DonkeyKong, there can only be a single instance of it per program run. Used for collisions with Mario and type updates</dd>
   <dt>Map</dt>
      <dd>Represents a single game map. Has many methods used both to know when an objects collides with the map and whether that object is in ladder, jumping, near a crane etc.</dd>
   <dt>Pair</dt>
      <dd>Represents pair of values, mostly used for coordinate storing</dd>
</dl>
### View
<dl>
   <dt>PlayScreen</dt>
      <dd>Abstract class which represents a game screen</dd>
   <dt>MainMenu</dt>
      <dd>One of the possible game screens, holds the buttons for the main menu of the game</dd>
   <dt>Play</dt>
      <dd>The other possible game screen, represents the actual game screen</dd>
   <dt>ScoreTimer</dt>
      <dd>Class which holds labels to show current score, how many lives Mario has left and how much time has passed</dd>

   #### Entity
   <dl>
      <dt>ElementView</dt>
          <dd>Abstract class which represents what a view of an object should have, implements methods such as draw, set position and update sprite</dd>

</dl>

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

[Model UML Diagram](https://github.com/Almeida-Oco/LPOO1617_T1G1/blob/master/UML/Model.png "Model UML Diagram")

[View UML Diagram](https://github.com/Almeida-Oco/LPOO1617_T1G1/blob/master/UML/View.png "View UML Diagram")

### Main Menu
-  **Single Player**
   Allows the player to start a single player game much alike the arcade one.

- **Exit**
Simply exit the game.

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


# FINAL DELIVERY

## USER MANUAL

* Menu: very simple with only two buttons, the play button to start playing and the exit button to exit the game
[Main Menu]:https://github.com/Almeida-Oco/LPOO1617_T1G1/blob/master/images/menu.png "Main Menu"
