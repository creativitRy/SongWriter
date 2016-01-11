/**
 *  NotePossibilities object holds info about what all the possible notes a note can travel to
 *  @author Gahwon Lee, Henry Wang
 */

import java.io.*;
import java.util.*;

public class NotePossibilities
{
	private Note previousNote;
	private ChordStack nextChord;
	private ArrayList<Note> possibleNotes = new ArrayList<Note>();

	public NotePossibilities(Note previousNote, ChordStack nextChord)
	{
		this.previousNote = previousNote;
		this.nextChord = nextChord;

		for (int i = 0; i < nextChord.getAllNotes().length; i++)
		{
			possibleNotes.add(new Note(nextChord.getNote(i, SongWriter.key.getChromScaleIndex() ), previousNote.getOctave(), true));
			possibleNotes.add(new Note(nextChord.getNote(i, SongWriter.key.getChromScaleIndex() ), previousNote.getOctave() - 1, true));
			possibleNotes.add(new Note(nextChord.getNote(i, SongWriter.key.getChromScaleIndex() ), previousNote.getOctave() + 1, true));
		}
	}

	/**
	 * given an array of stuff and an integer array representing the weight of the stuff, return a thing from the first array based on random weight
	 * @param stuffs something is chosen randomly from here
	 * @param weights these are the weights. higher means more chance of getting picked. x<=0 means no chance of getting picked
	 * @param <T> whatever thing you want to get. if primitive array, use wrappers
	 * @return randomly picked thing
	 */
	private <T> T calculateProbability(T[] stuffs, int[] weights)
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
