
# FINAL PROJECT DELIVERY

## Architecture Design
<br />

### **Packages**
<br />


### Controller
---
<dl>
   <dt>GameLogic</dt>
      <dd>Translates input from Android to commands to be given to model</dd>
</dl>

<br />


### Model
---
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
<br />

### View
---
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
      <dt>ViewFactory</dt>
          <dd>Used to hold the various possible views and its sprite. When correctly used guarantees no more than 1 object of each view is created</dd>
      <dt>MarioView</dt>
          <dd>Holds the sprites to be drawn for mario, as well as their position and scale</dd>
      <dt>BarrelView</dt>
          <dd>Holds the sprites to be drawn for a barrel, as well as their position and scale</dd>
      <dt>FireView</dt>
          <dd>Holds the sprites to be drawn for a fire, as well as their position and scale</dd>
      <dt>DonkeyKongView</dt>
          <dd>Holds the sprites to be drawn for the DonkeyKong, as well as their position and scale</dd>
      <dt>BarrelFireView</dt>
          <dd>Holds the sprites to be drawn for the flaming barrel, as well as their position and scale</dd>
      <dt>PrincessView</dt>
          <dd>Holds the sprites to be drawn for the princess, as well as their position and scale</dd>
   </dl>
</dl>

### **Design Patterns**
<dl>
   <dt>FLYWEIGHT</dt>
      <dd>Sprites are only loaded once so every time we need to use that sprite we simply go get it. Implemented thanks to LibGdx AssetManaget</dd>
   <dt>SINGLETON</dt>
      <dd>There can only be a single GameLogic and a single DonkeyKong</dd>
   <dt>STRATEGY</dt>
      <dd>Fires can change their strategy mid-game, meaning they can either get smarter or dumber based on current strategy</dd>
   <dt>STATE</dt>
      <dd>Mario has a number of states that transition between themselves as well as Barrel</dd>
</dl>

### **Design Decisions**
   For this project we decided to use the Model View Controller architectural pattern to help maintain the loose coupling between packages as well as providing an easy to comprehend and high mutability of the Project.
   
### **Conclusions**
   Looking back there are some aspects of the project that we perhaps would change. First and foremost we would definitely not use physics in our game since arcade games have such an unique physics which is hard to mimic using actual correct physics. Due to this divergence between game physics and real physics we had some difficulties implementing both the collisions and a portable multi-device way to represent everything that involves gravity, such as jumps or falls.
   Other aspect we would reconsider is the use of a ViewPort in the game. When we first started implementing the game we were aware that we had ViewPorts available to us. However these would either not fill the whole screen as we wanted or would deform the game map in a way that did not seem pleasant to the eye. Therefore we decided not to use one. This made us have to worry what would happen in case screens of different size were used to play the game, so we added a scaling functionability, however it is not working properly. With a ViewPort all of this could be avoided and we could have honed the project even further.
   
   In regards to how much time was spent on the project, we counted approximately 180 hours of work. This counting started when we finally managed to work properly with Android Studio, which by itself took a while.
   The work distribution is the same between group member, which means :
   
   João Francisco Barreiros de Almeida (up201505866) 50%
   Zé Pedro Machado (up201504779) 50%



## USER MANUAL

* Menu: very simple with only two buttons, the play button to start playing and the exit button to exit the game

![alt text](https://github.com/Almeida-Oco/LPOO1617_T1G1/blob/master/images/menu.png)

* PlayScreen
   Tilt the phone to move Mario, tilt left to move Mario to the left, tilt right to move Mario to the right
   and if Mario is aligned with the stairs tilting up the phone will allow Mario to climb the stairs.
   Only completes stairs allows Mario to go next floor but the breaked ones can be used to avoid barrels.


