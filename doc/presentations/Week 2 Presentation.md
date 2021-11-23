# Final Project Week 2 presentation
    by Tyler Barton (tylervb2)

## Demonstration (SERVER)
In this demonstration:

We want to create a server to handle the game

1. Configuration
2. Global async logging
3. Entity Drawing
4. Hit detection
5. Show the client loop

# Explanation
| Class | Purpose |
|-----------|:---------|
| Game | Launcher | 
| ClientWindow | [View] Visual Output |
| GameApplet | [Controller] Input & Game | 
| GameClient | Actual Game Logic

*Game* launches *ClientWindow* creates *GameApplet* 
*GameClient* controls logic and outputs to *GameApplet*

## Next Week:
Server