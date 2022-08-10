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
// Class for Entity's information
public class Entity
{
    // Variables for each factions attributes
    public static final char DEFAULT_APPEARANCE = 'X';
    public static final char ELF = 'E';
    public static final char EMPTY = ' ';
    public static final char ORC = 'O';
    public static final int DEFAULT_HP = 1;
    public static final int ORC_DAMAGE = 3;
    public static final int ELF_DAMAGE = 7;
    public static final int ORC_HP = 10;
    public static final int ELF_HP = 15;
    private int row;
    private int column;

    private char appearance;
    private int hitPoints;
    private int damage;
    // Constructor with no parameters for creating a no faction default appearance Entity
    public Entity()
    {
        setAppearance(DEFAULT_APPEARANCE);
        setHitPoints(DEFAULT_HP);
    }
    // Constructor with one parameter for creating a custom appearance Entity with default hp and orcs damage value
    public Entity(char newAppearance)
    {
        appearance = newAppearance;
        hitPoints = DEFAULT_HP;
        damage = ORC_DAMAGE;
    }
    // Constructor with three parameters for creating a custom appearance Entity with custom hp value and custom damage value
    public Entity(char newAppearance, int newHitPoints, int newDamage)
    {
        setAppearance(newAppearance);
        setDamage(newDamage);
        setHitPoints(newHitPoints);
    }
    // Features: Accessor method for getting the Entity's appearance
    // Limitations: Can't change the Entity's appearance
    public char getAppearance()
    {
        return(appearance);
    }
    // Features: Accessor method for getting the Entity's damage
    // Limitations: Can't change the Entity's damage
    public int getDamage()
    {
        return(damage);
    }
    // Features: Accessor method for getting the Entity's hitpoints
    // Limitations: Can't change the Entity's hitpoints
    public int getHitPoints()
    {
        return(hitPoints);
    }
    // Features: Mutator method for setting the Entity's location
    // Limitations: Can't change the Entity's location without parameters
    public void setLocation(int newRow, int newColumn)
    {
        row = newRow;
        column = newColumn;
    }
    // Features: Accessor method for getting the row of Entity's location
    // Limitations: Can't change the row of Entity's location
    public int getRow()
    {
        return(row);
    }
    // Features: Accessor method for getting the column of Entity's location
    // Limitations: Can't change the column of Entity's location
    public int getColumn()
    {
        return(column);
    }
    // Features: Mutator method for setting the Entity's appearance
    // Limitations: Can't change the Entity's appearance without parameters
    // It's a private method
    private void setAppearance(char newAppearance)
    {
        appearance = newAppearance;
    }
    // Features: Mutator method for setting the Entity's damage
    // Limitations: Can't change the Entity's damage without parameters
    // New damage needs to be greater than 1
    // It's a private method
    private void setDamage(int newDamage)
    {
        if (newDamage < 1) 
        {
            System.out.println("Damage must be 1 or greater");
        }
        else
        {
            damage = newDamage;
        }        
    }
    // Features: Mutator method for setting the Entity's hitpoints
    // Limitations: Can't change the Entity's hitpoints without parameters
    // It's a private method
    public void setHitPoints(int newHitPoints)
    {
        hitPoints = newHitPoints;
    }
}