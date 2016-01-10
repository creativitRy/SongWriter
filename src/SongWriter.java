/**
 * ChordStack object holds info about a specific chord
 *  either increase or decrease the population.
 *  @author Gahwon Lee, Henry Wang
 */

import java.io.*;
import java.util.*;

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
		1. analyze chords
		2. apply resolution rules
		3. write bass line
		4. write random soprano line based on probability
		5. write alto and tenor line based on probability
		6. check for errors - if errors exist, redo 3-5
		7. write midi file

		 */

	}

	/**
	 * given an array of stuff and an integer array representing the weight of the stuff, return a thing from the first array based on random weight
	 * @param stuffs something is chosen randomly from here
	 * @param weights these are the weights. higher means more chance of getting picked. x<=0 means no chance of getting picked
	 * @param <T> whatever thing you want to get
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
