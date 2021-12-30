# Graphical RPG
Tyler Barton (tylervb2) | Moderator: Kuihau Liu (kuihual2)

A client-server java game which utilizes MVC concepts to properly manage both data management and visuals. The game utilizes a server to allow multiple players at a single time, surviving in a top down world.

## Abstract
### Project Purpose
The purpose of the project is meant to elaborate on the complexity and coding styles learned throughout CS 242, making a big enough project to magnify proper project structure, MVC usage, and coding style.

The project mixes a visual client that a user can interact with to control the game via a TCP connection to a server which manages each individual player's actions in an online environment while also serializing neccesary data to a database. 

### Project Motivation
I found myself most intrigued by Assignment 1: the game of Uno. I anticipated what future assignments could hold, build the project around that, and had a good time doing it. 

## Technical Specification
- Platform: Cross-platform (Linux, MacOS, Windows) running on JVE 
- Programming Languages: Java (Client + Server) (Python for Flask or SqLite3 may be used for backend )
- Stylistic Conventions: Standard Java Conventions
- IDE: Visual Studio Code, Deco (IDE for React Native, acquired by Airbnb)
- Target Audience: Bored highschool students 

## Functional Specification
`User` refers to the person running the client application. 
`Player` refers to the entity in the game world.
`NPC` refers to an entity that is not player controlled in the game world.

### Features
Client:
- Opens a new window on the user's desktop
- Is able to interpret user inputs and relay that information as a world event (ie: mouse position to game coordinates)
- Displays game state for the user's player
- Displays entities in the world including other players
- Updates entity states based on the server 

Server:
- CLI based to show errors & information
- Should be able to manage multiple connections
- Loads data from previously saved state
- Server saves data to a database so that game progress is not lost

### Scope of the project
- Limitations include 
Game is able to accept multiple connections from any internet connection in the world. 

Due to time, some aspects of the game may be 'hacked' to implement functionality and show features. One of these foreseen hacks is to forego any type of packet verification or encryption. This leaves the game open to packet modification if actually deployed.

Clients will be allowed to send an arbitrary string representing a player that wish to access on the server. This string would normally act as a username. Due to time, no login system will be implemented and no checks will be implemented to verify proper ownership of player accounts.

For testing of client-sided graphics, snapshot testing would be ideal but we have only been introduced to that via JavaScript (jest created by Facebook). It would be ideal to use a similar testing method for graphical components to ensure functionality. Research has been done into this functionality and it is being sidelined as to not spend too much time when visual verification will be sufficient due to time. Testing server-sided would be strictly unit-test case based and easy to implement. 

Gameplay will likely be clicking on entities in the game world to do some sort of instant 'combat' interaction such as damaging their health based on a random number generator. While not engaging, this gameplay will suffice and will show that ability to expand on the behavior if the project were to be modified.

- Assumptions include 
Assumes user has networking available to them unless they control both the client and the server then set the client to point to 127.0.0.1:PORT. This would disable the multiplayer functionality.

Also under the impression the game design world is very similar from game to game such like our game of Uno when done right. Components are like that of real-world objects and code layout can be segmented as such.

Code will most likely not be written to expressly and explicitly demonstrate Model/View/Controller behavior but code documentation and layout will attempt to show the purpose of each class. 

## Brief Timeline
- Week 1: Client

1.1 Running the application creates a new window on the user's desktop to be interacted with.

1.2 Hooks user input to be parsed locally (in the client).

1.3 Displays graphical entities to the user (view component).

1.4 Depending on game-camera location, does not draw entities that would not be visible on screen

1.5 Draws Player entity in the middle of the screen

1.6 Clicking on an entity indicates some type of interaction (will be implemented in later weeks)

1.7 Draws tiles as map with (x,y) positioning


- Week 2: Server

2.1 Running the server launches a command-line-interface that displays information

2.2 Server accepts connections on a pre-determined port

2.3 Server verifies and accepts client connections

2.4 Create an Entity object (for all interactable world objects)

* 2.4.1 Create a player object, child of entity

2.5 Each connected client creates a player entity in the server

2.6 Server sends packets to clients

- Week 3: Server + Client Relations

3.1 Is able to maintain a connection to multiple clients without dropping

3.2 Sends entity position updates to each individual client

3.3 Saves world state and player information

3.4 Implements a* or dijkstra's algorithm for entity movement 

3.5 Draws a username above each player entity 

3.6 Draws a health bar above each entity; health is managed by the server 

- Week 4: Gameplay Mechanics
By this point, the framework for the gameplay should be implemented and should now just be linked together to be managed by the server and then displayed to the player. 

4.1 Clicking on an entity in the client sends a packet to the server, indicating some sort of 'attack' action

4.2 A dead NPC is removed from the world

4.3 A dead player receives some penalty and is relocated to a grave yard

4.4 Graveyard location (likely to be a square) does not accept damage events

4.5 Damage to entities is calculated via some state

4.6 An indicator for player progress. Some games commonly use experience, money, or items. Since time is an issue, this indicator will be simple.



## Rubrics
For these rubrics, I indicate the points that can be earned through each task. I leave the summation of the points to the grading calculator on Google Sheets.

### Week 1 - Client
| Category  | Total Score Allocated | Detailed Rubrics                                                            |
|-----------|:---------:|-------------------------------------------------------------------------------|
|  Application |  1  |  0: Application does not run (This is really bad) <br> +1: Client launches
|  User Input |  3  |  0: User is not able to interact with client <br> +1: Client registers mouse input <br> +1: Client registers keyboard input | + 1 Client is able to convert mouse position to world coordinate |
|  Draws entity |  3  |  0: No graphics drawn <br> +1: Able to display an entity <br> +1 Displays player in the middle of the screen |
|  Draw map |  3  |  0: No map drawn <br> +1: Map drawn (but layout is in front of entities) <br> +1 Map is properly drawn layered below all entities |
|  
|  Test: Client Input |  5  |  0: Didn't implement any input tests <br> +3: Tests user input using an automated java class (See: Robot) <br> +2 Tests that input is properly interacts with application screen |
|  Test: World Parsing |  5  |  0: Didn't implement any parse tests  <br> +2: Client translates mouse position to world coordinates <br> +2: Mouse clicks on entities indicate interaction <br> +1: client gives an indicator for entity interaction  |




### Week 2 - Server
| Category  | Total Score Allocated | Detailed Rubrics                                                            |
|-----------|:---------:|-------------------------------------------------------------------------------|
|  Server-Client Connection |  2  |  0: Didn't implement anything <br> +1: Server registers clients attempt to connect <br> +1: Server holds multiple client connections |
|  Entity model |  2  |  0: Didn't implement anything <br> +1: Implements entity model <br> +1: Implements player model |
|  Player State Change |  3  |  0: Didn't implement anything <br> +1: Client connection creates a player <br> 4: Server can accurately manage user objects |
|  Game Loop |  3  |  0: Server is static and does not allow for state change <br> +2: Server implements a game loop located in an appropriate file |
|  Save & Load state |  5  |  0: Server is not able to load or save game state <br> +1: Can save a single player state <br> +1: Can save NPC data including location and health <br> +1: Can load a single player state  <br> +1: Can put that loaded player state into the game world <br> +1 can load NPC data into the game world |
|  Test Connection |  2  |  0: Didn't implement tests <br> +1: Server is open on predetermined port <br> +1: Server can accept incoming connections |
|  Test Model |  3  |  0: Didn't implement any entities <br> +1: Tested entity functionality and state changing <br> +2: Tests player functionality and state changing  |
|  Test State Change |  5  |  0: Didn't implement tests <br> +2: Tests that the game loop updates state <br> +2: Tests saving and loading data <br> +1 Tests saving and loading NPC data |


### Week 3 - Client + Server Relations
| Category  | Total Score Allocated | Detailed Rubrics                                                            |
|-----------|:---------:|-------------------------------------------------------------------------------|
|  Server Player Management |  2  |  0: Server doesn't care about players <br> +1: Server creates a player entity in the game world for each client connection <br> +1: Server gives each player their own thread  |
|  Server-Client Network |  5  |  0: Server and Client do not communicate properly <br> +1: Server sends packets to client  <br> +1: Client sends packets to server <br> +1 Server properly parses client data and modifies game state <br> +1: Client interprets server packet and modifies view state <br> +1 Server and Client properly communicate and update all states  |
|  Movement |  3  |  0: Player cannot move <br> +1: Player is able to move  <br> +2: Entities move based using a path-finding algorithm |
|  State display |  5  |  0: Client does not display any server information <br> +1: Client displays user's player information <br> +1 Client displays entity health information <br> +1: Client displays Player names <br> +1 Client displays Players' health information <br> +1: Client displays entity positions in the world view |
|  Test Player |  3  |  0: Server does not properly manage player <br> +1 Server creates a new player based on each connection <br> +1: Server sends back world information to the client when a player is created <br> +1 Server properly loads player data from a file |
|  Test Networking |  4  |  0: Didn't implement tests for networking <br> 1: Some information related to users is saved  <br> 3: Most information related to users is saved <br> 4: All user state information is able to be saved & recalled |
| Test Movement | 3 | 0: Didn't implement any entity movement tests <br> +1 Entity positions are changed in the model <br> +1 Entity path finding returns the shortest path |


### Week 4 - Gameplay Mechanics
| Category  | Total Score Allocated | Detailed Rubrics                                                            |
|-----------|:---------:|-------------------------------------------------------------------------------|
|  User Progress Indicator |  2 |  0: User cannot see progress  <br> +1: User can see their own progress <br> +1: User can see the progress of other players |
|  'Dead' Entities |  5  |  0: No death mechanic or penalty <br> +1: NPCs are removed from game world <br> +1: Players are penalized for their health reaching 0 <br> +1: Player is relocated to a `graveyard` in the gameworld <br> +2 Player is reward for NPC death in which they interacted with |
|  Combat |  5  |  0: No interaction between entities managed in game loop <br> +1: Tests entities combat-interact with proper targets <br> +1: Damage calculation based on 'progress' <br>+1: Combat causes state change (damage applied to health)  <br> +2: Combat is relayed to the Client in a meaningful way (ie: damage above entities are show, a projectile displayed, an in-combat indicator drawn) |
| Client Login | 3 | 0: Client has no ability to obtain a unique player from the server <br> +1: Client can send a username packet to the server <br> +2 Server is able to load a specific username file and let the client play that character |
|  Test 'combat' mechanics |  8  |  0: Didn't implement tests <br> +2: Entities are able to interact with each other in some way (NPC->Player and Player->NPC) <br> +1: Implements a test that demonstrates some sort of damage calculation <br> +1 Entities with health of 0 are moved to a death state <br> +1: Test Interactions with Entity in death state creating no adverse effects <br> +1 Client test to send proper combat packet to server <br> +1 Server receives packet and sends a reply based on interaction attempt |
| Test Client display | 2 | 0: Client has no tests <br> +1: Test client's ability to see User Player state (position, health, progress) <br> +1 Test client's ability to see other entity states |



## Quick References 
1. https://docs.oracle.com/javase/7/docs/api/java/awt/Robot.html
2. http://jfcunit.sourceforge.net/