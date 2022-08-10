/*
Author: Amirreza Pazira 
UCID: 30133500 
Tutorial: 08

Version: 12-March-2021

Program: Text-based strategy combat simulator of Orcs vs. Elves

Features: 
1: Can create 10x10 combat simulation World using a 2D array with each Orc and Elf positions read from a text file .txt  
2: Creates an ArrayList referencing all the Entities in the world 
3: Can Display the world for each round with each Entity's position shown  
4: Can check every adjacent elements of each Entity to see if the Entity needs to attack or keep moving 
5: If there is an enemy adjacent the Entity will stop moving and attack that enemy Entity 
6: If there are no enemies nearby the Entity will keep moving diagonally (Elves only from bottom left to top right and Orcs top right to bottom left) if after checking the path is not blocked by another friendly Entity 
8: All Entity's have Damage/Hitpoints(Different for each faction)  
9: If Entity's hitpoint goes below zero it dies and gets removed 
10: There is a Debug Mode which can be turned On/Off when turned "On" by the user it will display the rounds information and each action that happened on that round 
11: If there are only Entities from one faction left that faction has won and the simulation will end
12: There is an automatic cease fire after 10 rounds of no attack

Limitations: 
1: Cannot create a simulated world bigger than 10x10 and read a text file bigger than 10x10 characters  
2: Entities can only move diagonally(Elves only from bottom left to top right and Orcs top right to bottom left)
3: Entities can only attack adjacent enemies
4: Can't change the Factions Damage or Hitpoints
5: Can't change the number of rounds for cease fire
6: Can't change the factions appearance
*/
// Main class to run the program
public class Simulator
{
    public static void main(String [] args)
    {
    	// Creating an object of class World to call the start method in that class and start the program
        World aWorld = new World();
        aWorld.start();
    }
}