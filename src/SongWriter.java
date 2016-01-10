/**
 * ChordStack object holds info about a specific chord
 *  either increase or decrease the population.
 *  @author Gahwon Lee
 *  Period: 0
 *  Date: 01-08-16
 */

import java.io.*;
import java.util.*;
import javax.sound.midi.*;

public class SongWriter
{
	public static NoteLetter key;
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
        String keyName = scn.next();
        String minorKey = scn.next();
        scn.nextLine();
        
        key = new NoteLetter(keyName);
        isMinor = (minorKey.equals("minor"));
        
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

        /*
        steps:
        1.

         */
    }

}
