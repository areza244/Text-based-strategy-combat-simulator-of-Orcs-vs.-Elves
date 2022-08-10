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

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
  Usage:
  * Call the static read method to prompt the user for the file containing the 
    positions.
  * The method will prompt for a filename and try to open that the file in the
    location where the Driver for your Java program is located.
  * The prompt will be repeated if there are problems accessing the file (e.g.
    the file is not at the specified location, it is corrupted etc.).
  * The method returns a reference to a 2D array of references to objects of type
    Entity.
  * The appearance attribute of the entity will correspond to the appearance of 
    characters in the file.
  * Starting files must be exactly 10x10 characters in size and consist only of the 
    'O' character, an 'E' character or a space.

  Version Feb 16B, 2021
  * Program repeats prompt for filename if file not found at location.

  Version Feb 16A, 2021
  * Program allows the user to specify the input file at runtime.

  Version: Feb 11A:
  * Changed references of dwarves to elves. Constants related to the
  * Entity attributes deleted.

  Version: 2015
  * Original code reads in dwarven and orc positions

*/
public class FileInitialization
{
    public static Entity[][] read() {
        Entity [][] temp = new Entity[World.SIZE][World.SIZE];
        String line = null;
        int r, c;
        char letter;
        BufferedReader br = null;
        FileReader fr = null;
        FileContainer aContainer = null;
        try {
            aContainer = checkingFile(br,fr);
            br = aContainer.getBufferedReader();
            fr = aContainer.getFileReader();
            line = br.readLine();
            if (line == null)
                System.out.println("Empty file, nothing to read");
            r = 0;
            while (line != null) {   
                c = 0;
                while(c < World.SIZE) {
                    letter = line.charAt(c);
                    temp[r][c] = createEntity(letter);
                    c = c + 1;
                }
                line = br.readLine();
                if (line != null)
                    r = r + 1;
            }
            fr.close();
        }
        catch (FileNotFoundException e) {
            String location = System.getProperty("user.dir");
            System.out.println("Input file not in " + location);
        }
        catch (IOException e) {
            System.out.println("General file input problem " + 
                               "reading starting positions");
        }
        return(temp);
    }
    
    private static FileContainer checkingFile(BufferedReader br, FileReader fr)
    {
        FileContainer aContainer = null;    
        Scanner in = new Scanner(System.in);
        String filename = null;
        boolean fileFound = false;
        while (fileFound == false)
        {
            try
            {
                System.out.print("Name of file containing starting positions: ");
                filename = in.nextLine();   
	        fr = new FileReader(filename);
                br = new BufferedReader(fr);
                fileFound = true;
            }
            catch (FileNotFoundException ex)
            {
                String location = System.getProperty("user.dir");
                System.out.println("Input file not in " + location);
            }

         }
         aContainer = new FileContainer(br,fr); 
         return(aContainer);
    }
 


    private static Entity createEntity(char letter)
    {
        Entity e = null;
        switch(letter)
        {
            case Entity.ORC:
                e = new Entity(Entity.ORC,Entity.ORC_HP,Entity.ORC_DAMAGE);
                break;

            case Entity.ELF:
                e = new Entity(Entity.ELF,Entity.ELF_HP,Entity.ELF_DAMAGE);
                break;
            case Entity.EMPTY:
                e = null;
                break;
            default:
                System.out.println("Error: Invalid character [" + letter + 
                                   "] in file");
        }
        return(e);
    }
}