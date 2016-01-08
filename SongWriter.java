/**
 * ChordStack object holds info about a specific chord
 *  either increase or decrease the population.
 *  @author Gahwon Lee
 *  Period: 0
 *  Date: 01-08-16
 */
 
import java.util.*;
import java.io.*;

public class SongWriter
{
	public static int key;
	public static boolean isMinor;
	public static String time;
	
	public static void main(String[] args) throws IOException
	{
        Scanner fileNameScn = new Scanner(System.in);
        
        System.out.print("Txt file name that contains Chord progressions?: ");
        String fileName = fileNameScn.nextLine();
        
        if (fileName.indexOf(".txt") == -1)
        	fileName += ".txt";
        
        Scanner scn = new Scanner(new File(fileName));
        
        String tempKey = scn.nextLine();
        isMinor = (tempKey.toLowerCase().indexOf(".minor") != -1);
        
        if (tempKey.toLowerCase().indexOf("c#") != -1)
        	key = 1;
        else if (tempKey.toLowerCase().indexOf("c") != -1)
        	key = 0;
        else if (tempKey.toLowerCase().indexOf("db") != -1)
        	key = 1;
        else if (tempKey.toLowerCase().indexOf("d#") != -1)
        	key = 3;
        else if (tempKey.toLowerCase().indexOf("d") != -1)
        	key = 2;
        else if (tempKey.toLowerCase().indexOf("eb") != -1)
        	key = 3;
        else if (tempKey.toLowerCase().indexOf("e") != -1)
        	key = 4;
        else if (tempKey.toLowerCase().indexOf("f#") != -1)
        	key = 6;
        else if (tempKey.toLowerCase().indexOf("f") != -1)
        	key = 5;
        else if (tempKey.toLowerCase().indexOf("gb") != -1)
        	key = 6;
        else if (tempKey.toLowerCase().indexOf("g#") != -1)
        	key = 8;
        else if (tempKey.toLowerCase().indexOf("g") != -1)
        	key = 7;
        else if (tempKey.toLowerCase().indexOf("ab") != -1)
        	key = 8;
        else if (tempKey.toLowerCase().indexOf("a#") != -1)
        	key = 10;
        else if (tempKey.toLowerCase().indexOf("a") != -1)
        	key = 9;
        else if (tempKey.toLowerCase().indexOf("bb") != -1)
        	key = 10;
        else
        	key = 11;
        
        time = scn.nextLine();
        
        String chordsOneString = scn.nextLine().trim();
        
        int chordsAmount = 0;
        Scanner chordsScnTemp = new Scanner(chordsOneString);
        
        while (chordsScnTemp.hasNext())
        {
        	chordsAmount++;
        }
        
        //chordsScnTemp.close();
        
        Scanner chordsScn = new Scanner(chordsOneString);
        ChordStack[] chords = new ChordStack[chordsAmount];
        
        for (int i = 0; i < chordsAmount; i++)
        {
        	chords[i] = new ChordStack(chordsScn.next(), isMinor);
        }
        
    }
}
