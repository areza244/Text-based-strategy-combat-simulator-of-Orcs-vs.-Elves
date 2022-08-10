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
// Class for Debug Mode
public class GameStatus
{
	// Boolean variable to turn the Debug mode On/Off
    public static boolean debugModeOn = false;
    // Features: Method for turning the debug mode On/Off when called
    // If the debugModeOn variable is false it will turn the debug mode On
    // If the debugModeOn variable is true it will turn the debug mode Off
    public void debug()
    {
    	if (debugModeOn == false){
    	debugModeOn = true;
	  	System.out.println("");
	  	System.out.println("DebugMode = ON");
	  	System.out.println("");
	  	}
	  	else if (debugModeOn == true){
    	debugModeOn = false;
	  	System.out.println("");
	  	System.out.println("DebugMode = OFF");
	  	System.out.println("");
	  	} 
    }
    // Features: Method for debug mode displaying the information about the Entity and its location
    // Limitations: Needs parameters to run
    public void checkAttack(char appearance, int row, int column)
    {
    	System.out.println("");
    	System.out.println("<<< Entity " + "'"+appearance+"'" + " at " +"("+row+"/"+column+") >>>");
    	System.out.println("  <<< Checking Actions for Entity " + "'"+appearance+"'" + " at (r/c) = " +"("+row+"/"+column+") >>>");

    }
    // Features: Method for debug mode displaying the information about the Orc Entity 
    // When there are no enemies nearby then showing the information of the Entity's destination 
    // and displaying if the Entity can move to that location
    // Limitations: Needs parameters to run
    public void checkMoveOrc(int rowOrc, int columnOrc, String condition)
    {
    	System.out.println("  <<< No adjacent enemies Found! >>>");
    	System.out.println("  <<< Checking next destination to move at (r/c) = " +"("+rowOrc+"/"+columnOrc+") >>>");
    	if (condition == "OutofBounds"){
    		System.out.println("  <<< Destination at (r/c) = " +"("+rowOrc+"/"+columnOrc+") is out of bound Entity is not moving! >>>");
    		System.out.println("...........");
    	}
        if (condition == "Full"){
        	System.out.println("  <<< Another Entity found at destination (r/c) = " +"("+rowOrc+"/"+columnOrc+") Entity is not moving! >>>");
        	System.out.println("...........");
        }
        if (condition == "Empty"){
        	System.out.println("  <<< Destination is empty Entity is moving to (r/c) = " +"("+rowOrc+"/"+columnOrc+") >>>");
        	System.out.println("...........");
        }

	}
	// Features: Method for debug mode displaying the information about the Elf Entity 
    // When there are no enemies nearby then showing the information of the Entity's destination 
    // and displaying if the Entity can move to that location
    // Limitations: Needs parameters to run
	public void checkMoveElf(int rowElf, int columnElf, String condition)
    {
    	System.out.println("  <<< No nemies Found! >>>");
    	System.out.println("  <<< Checking next destination to move at (r/c) = " +"("+rowElf+"/"+columnElf+") >>>");
    	if (condition == "OutofBounds"){
        	System.out.println("  <<< Destination at (r/c) = " +"("+rowElf+"/"+columnElf+") is out of bound Entity is not moving! >>>");
    		System.out.println("...........");
    	}
        if (condition == "Full"){
        	System.out.println("  <<< Another Entity found at destination (r/c) = " +"("+rowElf+"/"+columnElf+") Entity is not moving! >>>");
        	System.out.println("...........");
        }
        if (condition == "Empty"){
        	System.out.println("  <<< Destination is empty Entity is moving to (r/c) = " +"("+rowElf+"/"+columnElf+") >>>");
        	System.out.println("...........");
        }
	}
	// Features: Method for debug mode displaying the information about the Entity and its adjacent enemy Entity 
    // When there are enemies nearby it will show the information of the enemy Entity and its location 
    // then displaying the enemy old hitpoint and new hitpoint after getting damaged 
    // Limitations: Needs parameters to run
    public void attack(int enemyRow, int enemyColumn, int selfRow, int selfColumn, char selfAppearance,char enemyAppearance, int damage, int oldHp, int newHp )
    {
    	System.out.println("  <<< Enemy Found! at (r/c) = " +"("+enemyRow+"/"+enemyColumn+") Movement is not possible! >>>");
    	System.out.println("  <<< Entity " + "'"+selfAppearance+"'" + " at (r/c) = " +"("+selfRow+"/"+selfColumn+") " + " is attacking the enemy for " + damage + " Damage! >>>");
        System.out.println("  <<< Enemy " + "'"+enemyAppearance+"'" + " at (r/c) = " +"("+enemyRow+"/"+enemyColumn+") Previous HP = " + oldHp + " --> New HP = " + newHp + " >>>");
    	if (newHp <= 0)
    		System.out.println("  <<< Entity " + "'"+enemyAppearance+"'" + " at (r/c) = " +"("+enemyRow+"/"+enemyColumn+") is dead!");
    	System.out.println("...........");

    }
    // Features: Method for debug mode displaying the information about the rounds passed after the last attack 
    // Limitations: Needs parameter to run
    public void checkRounds(int rounds)
    {
    	System.out.println("<<< Rounds since the last attack occured = " + rounds + " >>>");
    }
}
