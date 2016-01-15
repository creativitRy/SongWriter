/**
 * SongWriter class is the main class that handles input and output.
 *  @author Gahwon Lee, Henry Wang
 */

import java.io.*;
import java.util.*;

public class SongWriter
{
	public static NoteLetter key;
	public static boolean isMinor;
	public static String time;
	public static int chordsAmount = 0;
	public static ChordStack[] chords;
	public static Note[][] notes;  //[0=S 1=A 2=T 3=B] [chord number]
	public static NotePossibilities[][] possibleNotes;  //[0=S 1=A 2=T 3=B] [chord number]
	
	public static void main(String[] args) throws IOException
	{
		Scanner fileNameScn = new Scanner(System.in);

		Scanner scn;
		outerloop:
		while (true)
		{
			try
			{
				System.out.print("Txt file name that contains Chord progressions?: ");
				String fileName = fileNameScn.nextLine();

				if (fileName.indexOf(".txt") == -1)
					fileName += ".txt";

				scn = new Scanner(new File(fileName));

				break outerloop;
			}
			catch (FileNotFoundException e)
			{
				System.out.println("\nFile does not exist. Please try again.");
				System.out.println("Make sure you spelled it correctly since this is case-sensitive.");
				System.out.println("This doesn't care whether you put .txt or not.\n");
			}
		}

		String keyName = scn.next();
		String minorKey = scn.next();
		scn.nextLine();
		
		key = new NoteLetter(keyName);
		isMinor = (minorKey.equals("minor"));
		
		time = scn.nextLine();
		
		String chordsOneString = scn.nextLine().trim();
		
		//int chordsAmount = 0;
		Scanner chordsScnTemp = new Scanner(chordsOneString);
		
		System.out.println("\nChords Detected:");
		
		while (chordsScnTemp.hasNext())
		{
			System.out.print(chordsScnTemp.next() + "  ");
			chordsAmount++;
		}
		
		//chordsScnTemp.close();
		
		/*
		steps:
		1. analyze chords
		2. write bass line (within range C4-G2)
		3. apply resolution rules
		4. write random soprano line based on probability
		5. write alto and tenor line based on probability
		6. check for errors (parallel unison/5th/octaves, range of voice) - if errors exist, redo 3-5
		7. write midi file
		 */


		//1. analyze chords
		Scanner chordsScn = new Scanner(chordsOneString);
		chords = new ChordStack[chordsAmount];
		
		System.out.println("\nAnalyzing Chords...");
		System.out.println(chordsAmount);
		for (int i = 0; i < chordsAmount; i++)
		{
			chords[i] = new ChordStack(chordsScn.next(), isMinor);
		}
		
		notes = new Note[4][chordsAmount];
		possibleNotes = new NotePossibilities[4][chordsAmount];
		
		writeBassLine();
		
		writeSopranoLine();
		
		
	}
	public static void writeBassLine()
	{
		//2. write bass line
		System.out.println("\nWriting bass line...");
		
		Note temp = new Note("C3");
		possibleNotes[3][0] = new NotePossibilities(temp, chords[0]);
		
		possibleNotes[3][0].removeAllExceptBassNote();
		possibleNotes[3][0].removePitchRange(7, 2, 0, 4, true);
		
		notes[3][0] = possibleNotes[3][0].getRandomNote();
		
		for (int i = 1; i < chordsAmount; i++)
		{
			
			possibleNotes[3][i] = new NotePossibilities(notes[3][i - 1], chords[i]);
			
			possibleNotes[3][i].removeAllExceptBassNote();
			possibleNotes[3][i].removePitchRange(7, 2, 0, 4, true); //within bass pitch range
			possibleNotes[3][i].removeSuperJumps();  //remove jumps higher than octaves
			possibleNotes[3][i].removeInterval(11);
			possibleNotes[3][i].removeInterval(10);
			possibleNotes[3][i].removeInterval(9);
			
			notes[3][i] = possibleNotes[3][i].getRandomNote();
		}
		
		for (int i = 0; i < chordsAmount; i++)
			System.out.print(notes[3][i] + " ");
		
		System.out.println();
	}
	public static void writeSopranoLine()
	{
		//3. write soprano line
		System.out.println("\nWriting soprano line...");
		
		Note temp = new Note("C5");
		possibleNotes[0][0] = new NotePossibilities(temp, chords[0]);
		
		//possibleNotes[0][0].removeAllExceptBassNote();
		possibleNotes[0][0].removePitchRange(0, 4, 7, 5, true);
		
		notes[0][0] = possibleNotes[0][0].getRandomNote();
		
		for (int i = 1; i < chordsAmount; i++)
		{
			
			possibleNotes[0][i] = new NotePossibilities(notes[3][i - 1], chords[i]);
			
			possibleNotes[0][i].removeAllExceptBassNote();
			possibleNotes[0][i].removePitchRange(0, 4, 7, 5, true); //within sop pitch range
			possibleNotes[0][i].removeIntervals(8);  //remove jumps higher than m6
			
			possibleNotes[0][i].setWeightInterval(0, 2);
			possibleNotes[0][i].setWeightInterval(1, 3);
			possibleNotes[0][i].setWeightInterval(2, 3);
			possibleNotes[0][i].setWeightInterval(3, 3);
			possibleNotes[0][i].setWeightInterval(4, 3);
			possibleNotes[0][i].setWeightInterval(5, 3);
			possibleNotes[0][i].setWeightInterval(6, 3);
			
			notes[0][i] = possibleNotes[0][i].getRandomNote();
		}
		
		for (int i = 0; i < chordsAmount; i++)
			System.out.print(notes[0][i] + " ");
		
		System.out.println();
	}

}
