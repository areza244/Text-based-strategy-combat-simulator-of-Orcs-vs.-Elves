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
// Importing Scanner class to get user's input and another class for creating an ArrayList
import java.util.Scanner;
import java.util.*;
// Class for our Simulation World 
public class World
{
    public static final int SIZE = 10;
    public static final int ORCS_WIN = 0;
    public static final int ELVES_WIN = 1;
    public static final int DRAW = 2;
    public static final int CEASE_FIRE = 3;
    public static final int NOT_OVER = 4;
    // variables in order for Runtime - Winner - rounds with no attack - condition if an attack has occurred
    private boolean run = false;
    private String winner = " ";
    private int rounds = 0;
    private String attackOccured = "No";
    // Creating GameStatus object for Debug mode
    private GameStatus debug = new GameStatus();
    // Creating an array of objects for our world
    private Entity [][] aWorld;
    // Creating an ArrayList for storing and referencing each Entity
    private ArrayList<Entity> anArray;

  
    
    // Features: Constructor which from starting input file will determine the position of the objects in the array. 
    // The type of character in the file determines the appearance of the Entity(O or E)
    // If the element is empty (spaces become null elements)
    // Adding each Entity to the ArrayList for quick access to all Entities
    // Calling the display method at the end to display the starting positions
    // Limitations: Can't change anything about the Entities and is called only once
    public World()
    {
        aWorld = new Entity[SIZE][SIZE];
        int r;
        int c;
        for (r = 0; r < SIZE; r++){
            for (c = 0; c < SIZE; c++){
                aWorld[r][c] = null;
            }
        }     
        aWorld = FileInitialization.read();
        anArray = new ArrayList<Entity>();
        int m;
        int n;
        for (m = 0; m < SIZE; m++){
            for (n = 0; n < SIZE; n++){
                if (aWorld[m][n] != null){
                    aWorld[m][n].setLocation(m,n);
                    anArray.add(aWorld[m][n]);  
                }                
            }
        }
        display();
    }
    // Features: Method for going through the ArrayList and passing information about each Entity to checkAttack method for the next Entity's action
    // Can access information about the Entities
    // It will keep track of number of rounds after the last attack and will end the program if it reaches 10 and cease fire will happen
    // Passes number of rounds after the last attack to GameStatus class checkRounds method for debug mode  
    // Limitations: Can't change anything about the Entities
    // Can't change the number of rounds for cease fire
    public void simulation()
    {
        int k;
        int r;
        int c;
        char e;
        update();
        attackOccured = "No";
        for (k = 0; k < anArray.size(); k++){
            if (anArray.get(k) != null){
                r = anArray.get(k).getRow();
                c = anArray.get(k).getColumn();
                e = anArray.get(k).getAppearance();
                if (e == Entity.ORC)
                    checkAttack(k,r,c,e);
            }
        }
        for (k = 0; k < anArray.size(); k++){
            if (anArray.get(k) != null){
                r = anArray.get(k).getRow();
                c = anArray.get(k).getColumn();
                e = anArray.get(k).getAppearance();
                if (e == Entity.ELF)
                    checkAttack(k,r,c,e); 
            }
        }
        if (attackOccured == "No"){
            rounds++;
            if (rounds == 10){
                run = true;
            }
        }
        if  (GameStatus.debugModeOn == true)
            debug.checkRounds(rounds);
    }
    // Features: Method for checking all the adjacent elements of each Entity to see if there are any enemies nearby
    // If there is an enemy nearby it will pass the needed parameters to attack method so that the Entity can attack that enemy
    // After finding one enemy it will stop looking for additional enemies because each Entity can only attack one enemy per turn
    // If there are no enemies nearby it will pass the needed parameters to checkMove method based on faction for destination checking 
    // Passes information about the Entity to checkAttack method in GameStatus class for debug mode 
    // Limitations: Can't change anything about the Entities
    // Needs parameters to run 
    public void checkAttack(int k, int row, int column, char appearance)
    {
        int minRow = 0;
        int minColumn = 0;
        int maxRow = 0;
        int maxColumn = 0;
        int m = 0;
        int n = 0;
        char temp = 'N';
        int attack = 0;
        if  (GameStatus.debugModeOn == true)
            debug.checkAttack(appearance,row,column);
        if ((row - 1 >= 0) && (column - 1 >= 0)){
            minRow = row - 1;
            minColumn = column - 1;
        }
        if ((row + 1 < SIZE) && (column + 1 < SIZE)){
            maxRow = row + 1;
            maxColumn = column + 1;
        }
        for (m = minRow; m <= maxRow; m++){
            for (n = minColumn; n <= maxColumn; n++){
                if ((aWorld[m][n] != null) && (attack == 0)){ 
                    if (aWorld[m][n].getAppearance() != appearance){
                        temp = 'Y';
                        attack(k,row,column,m,n,appearance);
                        attack ++;        
                    }
                }
            }
        }
        if (temp != 'Y'){
            if (appearance == Entity.ORC)
                checkMoveOrc(k,row,column,appearance);
            if (appearance == Entity.ELF)
                checkMoveElf(k,row,column,appearance); 
        }
    }
    // Features: Method for checking the destination of an Orc Entity to see if its blocked by another friendly Entity, Out of bounds or Empty
    // It will then pass the information about the destination along with the condition(full or empty) and the Entity's appearance to move method
    // Also will pass the destination and condition to checkMoveOrc method in GameStatus class for debug mode 
    // Limitations: Can't change the position of the Entities 
    // Needs parameters to run
    public void checkMoveOrc(int k, int row, int column, char appearance)
    {
        String condition = "Empty";
        String rangeOrc = "Out";
        int newRowOrc = 0;
        int newColumnOrc = 0;
        if ((row + 1 < SIZE) && (column + 1 < SIZE)){
            rangeOrc = "In";
            newRowOrc = row + 1;
            newColumnOrc = column + 1;
        }
        if  ((aWorld[newRowOrc][newColumnOrc] != null) || (rangeOrc == "Out")){
            condition = "Full";
            if  ((GameStatus.debugModeOn == true) && (rangeOrc == "Out"))
                debug.checkMoveOrc(row + 1,column + 1,"OutofBounds");
            else if  (GameStatus.debugModeOn == true)
                debug.checkMoveOrc(newRowOrc,newColumnOrc,"Full");
            move(k,row,column,appearance,condition);
        }
        if  ((aWorld[newRowOrc][newColumnOrc] == null) && (rangeOrc == "In"))
            if  (GameStatus.debugModeOn == true)
                debug.checkMoveOrc(newRowOrc,newColumnOrc,"Empty");
            move(k,row,column,appearance,condition);
    }
    // Features: Method for checking the destination of an Elf Entity to see if its blocked by another friendly Entity, Out of bounds or Empty
    // It will then pass the information about the destination along with the condition(full or empty) and the Entity's appearance to move method
    // Also will pass the destination and condition to checkMoveElf method in GameStatus class for debug mode 
    // Limitations: Can't change the position of the Entities 
    // Needs parameters to run
    public void checkMoveElf(int k, int row, int column, char appearance)
    {
        String condition = "Empty";
        String rangeElf = "Out";
        int newRowElf = 0;
        int newColumnElf = 0;
        if ((row - 1 >= 0) && (column - 1 >= 0)){
            newRowElf = row - 1;
            newColumnElf = column - 1;
            rangeElf = "In";
        }
        if  ((aWorld[newRowElf][newColumnElf] != null) || (rangeElf == "Out")){
            condition = "Full";
            if  ((GameStatus.debugModeOn == true) && (rangeElf == "Out"))
                debug.checkMoveElf(row - 1,column - 1,"OutofBounds");
            else if  (GameStatus.debugModeOn == true)
                debug.checkMoveElf(newRowElf,newColumnElf,"Full");
            move(k,row,column,appearance,condition);
        }
        if  ((aWorld[newRowElf][newColumnElf] == null) && (rangeElf == "In"))
            if  (GameStatus.debugModeOn == true)
                debug.checkMoveElf(newRowElf,newColumnElf,"Empty");
            move(k,row,column,appearance,condition);
    }
    // Features: Method for attacking an enemy Entity 
    // Subtracts the Attacker damage from enemy Entity hitpoint and update the Entity's hitpoints
    // Then calling the update method to update the ArrayList to see if the enemy hitpoint is below zero and is dead
    // Passing all the information about the Attacker and the enemy to attack method in GameStatus class for debug mode
    // Limitations: Can't remove a dead Entity itself 
    // Needs parameter to run
    public void attack(int k, int row, int column,int opponentRow, int opponentColumn, char appearance)
    {
    int hp = 0;
    int newHp = 0;
    int damage = 0;
    char enemy = aWorld[opponentRow][opponentColumn].getAppearance();
    attackOccured = "Yes";
    rounds = 0;
    hp = aWorld[opponentRow][opponentColumn].getHitPoints();
    damage = anArray.get(k).getDamage();
    aWorld[opponentRow][opponentColumn].setHitPoints(hp - damage);
    newHp = aWorld[opponentRow][opponentColumn].getHitPoints();
    if  (GameStatus.debugModeOn == true){
        debug.attack(opponentRow,opponentColumn,row,column,appearance,enemy,damage,hp,newHp);    
    }
    update();
    }
    // Features: Method for updating the 2D world array and the Entities ArrayList if one of the Entity's is dead
    // Removes the dead Entity from 2D world array and the Entities ArrayList and assigning null for its place
    // Checking if there are only members from one faction remaining
    // If there are only members from one faction remaining it will declare a winner and change the runtime variable to true ending the program
    public void update()
    {
        int elves = 0;
        int orcs = 0;
        char appearance = ' ';
        int r;
        int c;
        int k;
        for (k = 0; k < anArray.size(); k++){
            if (anArray.get(k) != null){
                r = anArray.get(k).getRow();
                c = anArray.get(k).getColumn();
                if (anArray.get(k).getHitPoints() <= 0){
                    aWorld[r][c] = null;
                    anArray.set(k, null);
                }
            }
        }
        for (k = 0; k < anArray.size(); k++){
            if (anArray.get(k) != null){
                appearance = anArray.get(k).getAppearance();
                if (appearance == Entity.ORC)
                    orcs++;
                if (appearance == Entity.ELF)
                    elves++;      
            }
        }
        if (elves == 0){
            winner = "Orcs";
            run = true;
        }
        if (orcs == 0){
            winner = "Elves";
            run = true;
        }
        if ((elves == 0) && (orcs == 0)){
            winner = "Draw";
            run = true;
        }          
    }
    // Features: Method for moving the Entities to their destination based on their faction by using the information and condition from checkMove method
    // Can set new positions for Entities ArrayList and moving the Entities in 2D world array and assigning null to previous location element
    // Limitations: Can't change positions without checkMethod parameters
    // Needs parameters to run
    public void move(int k, int row, int column, char appearance, String condition)
    {
        if (appearance == Entity.ORC){
            if (condition == "Full")
                anArray.get(k).setLocation(row, column);
            if (condition != "Full"){
                aWorld[row][column] = null;
                anArray.get(k).setLocation(row+1, column+1);
                aWorld[row+1][column+1] = anArray.get(k);
            }
        }
        if (appearance == Entity.ELF){
            if (condition == "Full")
                anArray.get(k).setLocation(row, column);
            if (condition != "Full"){
                aWorld[row][column] = null;
                anArray.get(k).setLocation(row-1, column-1);
                aWorld[row-1][column-1] = anArray.get(k);
            }
        }
    }
    // Features: Displays 2D world array elements one at a time one row per line 
    // Each element is bound above, below, left and right by bounding lines
    // Displays the winner at the top
    // Limitations: Can't change anything
    public void display()
    {
        int r = -1;
        int c = -1;
        int b = -1;
        int i;
        if (winner == "Orcs")
            System.out.println("Orcs Win!");
        if (winner == "Elves")
            System.out.println("Elves Win!");
        if ((rounds == 10) || (winner == "Draw"))
            System.out.println("Draw!");
        for (r = 0; r < SIZE; r++){
            for (i = 0; i < SIZE; i++)
                System.out.print("  - ");
            System.out.println();
            System.out.print("| ");
            for (c = 0; c < SIZE; c++){
                if(aWorld[r][c] == null)
                    System.out.print("  | ");
                else
                    System.out.print(aWorld[r][c].getAppearance() +" | "); 
            }
            System.out.println();
        }
        for (b = 0; b < SIZE; b++)
            System.out.print("  - ");
        System.out.println();
    }
    // Features: Method for starting the program and keep the program running by using a while loop and runtime variable
    // Calls the simulation method each time to update the simulation and then the display method to display the world
    // Gets the user input to continue with the rounds
    // If the user enters "D" or "d" the method will call the debug method in GameStatus class to turn the debug mode On 
    // If the user enters "D" or "d" again it will call the debug method in GameStatus class to turn the debug mode Off
    public void start()
    {
        String input;        
        Scanner in = new Scanner(System.in);
        System.out.print("Press enter to continue or 'd' to turn Debug Mode On/Off for next rounds: ");
        input = in.nextLine();
        switch(input)
            {
                case "d":
                case "D":
                debug.debug();
            }
        while (run == false){
            input = "";
            simulation();
            display();
            if (run == false){
                System.out.print("Press enter to continue or 'd' to turn Debug Mode On/Off for next rounds: ");
                input = in.nextLine();
            }
            switch(input)
            {
                case "d":
                case "D":
                debug.debug();
            }
        }
    }
}