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
	public static Note[][] notes;
	
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
		
		while (chordsScnTemp.hasNext())
		{
			chordsAmount++;
		}
		
		//chordsScnTemp.close();
		
		/*
		steps:
		1. analyze chords
		2. apply resolution rules
		3. write bass line (within range C4-G2)
		4. write random soprano line based on probability
		5. write alto and tenor line based on probability
		6. check for errors (parallel unison/5th/octaves, range of voice) - if errors exist, redo 3-5 (probably through recursion)
		7. write midi file
		 */


		//1. analyze chords
		Scanner chordsScn = new Scanner(chordsOneString);
		chords = new ChordStack[chordsAmount];
		
		for (int i = 0; i < chordsAmount; i++)
		{
			chords[i] = new ChordStack(chordsScn.next(), isMinor);
		}



	}

	/**
	 * given an array of stuff and an integer array representing the weight of the stuff, return a thing from the first array based on random weight
	 * @param stuffs something is chosen randomly from here
	 * @param weights these are the weights. higher means more chance of getting picked. x<=0 means no chance of getting picked
	 * @param <T> whatever thing you want to get. if primitive array, use wrappers
	 * @return randomly picked thing
	 */
	public static <T> T calculateProbability(T[] stuffs, int[] weights)
	{
		if (stuffs.length != weights.length)
			throw new IllegalArgumentException("make sure the stuff array and the weight array have the same length");

		int weightSum = 1; //starts with 1 because Math.random does not generate 1
		for (int weight : weights)
			weightSum += weight;

		//array of indexes
		int[] indexes = new int[weightSum];
		for (int i = 0; i < weights.length; i++)
		{
			for (int j = 0; j < weights[i]; j++)
			{
				indexes[j] = i;//stuffs[i];
			}
		}

		int randomValue = (int) (Math.random() * weightSum);

		return stuffs[indexes[randomValue]];
	}

}
