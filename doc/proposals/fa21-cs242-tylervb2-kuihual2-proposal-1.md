# Graphical RPG
Tyler Barton (tylervb2) | Moderator: Kyle Liu (kuihual2)

A client-server java RPG which utilizes MVC concepts to properly manage both data management and visuals.

## Abstract
### Project Purpose
The purpose of the project is meant to elaborate on the complexity and coding styles learned throughout CS 242, making a big enough project to magnify proper project structure, MVC usage, and coding style.

The project mixes a visual client that a user can interact with to control the game via a TCP connection to a server which manages each individual player's actions in an online environment while also serializing neccesary data to a database. 

### Project Motivation
I found myself most intrigued by Assignment 1: the game of Uno. I anticipated what future assignments could hold, build the project around that, and had a good time doing it. 

## Technical Specification
- Platform: Cross-platform (Linux, MacOS, Windows)
- Programming Languages: Java (Python for Flask or SqLite3 may be used for backend)
- Stylistic Conventions: Standard Java Conventions
- IDE: Visual Studio Code, Deco (IDE for React Native, acquired by Airbnb)
- Target Audience: Bored highschool students 

## Functional Specification
### Features
- Client displays game state for a specific user
- Client displays other users in the game world
- Server manages multiple connections
- Server saves data to a database so that game progress is not lost

### Scope of the project
- Limitations include ...
- Assumptions include ...

## Brief Timeline
- Week 1: Client

Client will be constructed in a local-host environment

- Week 2: Server

Clients will be linked to the server which manages inputs and states of multiple users synchronously. 

- Week 3: Database

Server will incrementally save user data and server state to a database for later access. 

- Week 4: Login & Deployment

Server will accept a username and password to log into the game world. 


## Rubrics
### Week 1 - Client
| Category  | Total Score Allocated | Detailed Rubrics                                                            |
|-----------|:---------:|-------------------------------------------------------------------------------|
|  Display |  4  |  0: Didn't implement anything <br> 1: Client launches <br> 3: Client displays visuals <br> 4: completed function a |
|  User Input |  2  |  0: Didn't implement anything <br> 1: Partial input control <br> 2: Full input control |
|  Function c |  4  |  0: Didn't implement anything <br> 1: implemented ... <br> 3: implemented .... <br> 4: completed function c |
|  Function d |  5  |  0: Didn't implement anything <br> 1: implemented ... <br> 3: implemented .... <br> 5: completed function d |
|  Test: Client |  6  |  0: Didn't implement tests <br> 1: implemented ... <br> 3: implemented .... <br> 6: completed test a |
|  Test: Input |  4  |  0: Didn't implement tests  <br> 4: All input test cases supplied and functional |

### Week 2 - Server
| Category  | Total Score Allocated | Detailed Rubrics                                                            |
|-----------|:---------:|-------------------------------------------------------------------------------|
|  User Network Connection |  4  |  0: Didn't implement anything <br> 1: User can temporarily connect (with bugs) <br> 3: User connects <br> 4: completed function a |
|  User model |  2  |  0: Didn't implement anything <br> 1: User module & associated data <br> 2: User model appropriately handles respective functions inside of scope |
|  User State Change |  4  |  0: Didn't implement anything <br> 2: Server has some ability to change a user state <br> 4: Server can accurately manage user objects |
|  Function d |  5  |  0: Didn't implement anything <br> 1: implemented ... <br> 3: implemented .... <br> 5: completed function d |
|  Test Connection |  6  |  0: Didn't implement tests <br> 1: implemented ... <br> 3: implemented .... <br> 6: completed test a |
|  Test User |  4  |  0: Didn't implement tests <br> 1: some tests implemented <br> 3: most user functionality (server-side) is accounted for <br> 4: completed test b |
|  Test State Change |  4  |  0: Didn't implement tests <br> 1: Server state change tests implemented <br> 3: User affecting server state tests <br> 4: Server succesfully changes states based on its own functions and its users |


### Week 3 - Database
| Category  | Total Score Allocated | Detailed Rubrics                                                            |
|-----------|:---------:|-------------------------------------------------------------------------------|
|  Data Management |  4  |  0: Didn't implement anything <br> 1: Some type of data serialization added <br> 3: Database connection successful <br> 4: Full database functionality |
|  Data Saving |  5  |  0: Didn't implement anything <br> 1: Data is saved at the end  <br> 2: All data is saved succesfully <br> 3: *All* data is saved at regular intervals |
|  Data Loading |  2  |  0: Didn't implement anything <br> 1: Some data is able to be loaded  <br> 2: Data is loaded (some state information missing or bugs occur) <br> 4: Data is loaded successfully |
|  Function d |  5  |  0: Didn't implement anything <br> 1: implemented ... <br> 3: implemented .... <br> 5: completed function d |
|  Test Database |  6  |  0: Didn't implement tests <br> 1: Database succesfully connects <br> 3: Database holds some data <br> 6: Database correctly manages & saves server data |
|  Test Users Saved |  4  |  0: Didn't implement tests <br> 1: Some information related to users is saved  <br> 3: Most information related to users is saved <br> 4: All user state information is able to be saved & recalled |


### Week 4 - Login & Deployment
| Category  | Total Score Allocated | Detailed Rubrics                                                            |
|-----------|:---------:|-------------------------------------------------------------------------------|
|  Client Login Screen |  4  |  0: Connection login attempt can be made to server  <br> 3: implemented .... <br> 4: completed function a |
|  Server Login validation |  4  |  0: Didn't implement anything <br> 2: Server is able to handle login requests <br> 4: Server verifies correct information, returns false for bad information|
|  Test Login |  6  |  0: Didn't implement tests <br> 1: Wrong information is denied <br> 3: Correct information returns a successful login <br> 6: Providing a proper username and password changes the client state and allows the player access to the server |
|  Test multi-user connection |  4  |  0: Didn't implement tests <br> 1: Server handles single users ok <br> 3: Server is able to handle a few users ok <br> 4: Client experience is smooth when multiple connections are made to the server |