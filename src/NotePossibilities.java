/**
 *  NotePossibilities object holds info about what all the possible notes a note can travel to
 *  @author Gahwon Lee, Henry Wang
 */

import java.util.*;

public class NotePossibilities
{
	private Note previousNote;
	private ChordStack nextChord;
	private ArrayList<Note> possibleNotes = new ArrayList<Note>();
	private ArrayList<Integer> weights = new ArrayList<Integer>();

	public NotePossibilities(Note previousNote, ChordStack nextChord)
	{
		this.previousNote = previousNote;
		this.nextChord = nextChord;

		boolean temp;
		if (SongWriter.isMinor)
		{
			//TODO: make exceptions for la and ti in melodic minor
			int[] arr = {2, 7, 0, 5, 10, 3};
			Arrays.sort(arr);
			temp = (Arrays.binarySearch(arr, SongWriter.key.getChromScaleIndex() ) != -1);
		}
		else
		{
			int[] arr = {5, 10, 3, 8, 1, 6};
			Arrays.sort(arr);
			temp = (Arrays.binarySearch(arr, SongWriter.key.getChromScaleIndex() ) != -1);
		}

		for (int i = 0; i < nextChord.getAllNotes().length; i++)
		{
			possibleNotes.add(new Note(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[i], previousNote.getOctave(), temp));
			possibleNotes.add(new Note(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[i], previousNote.getOctave() - 1, temp));
			possibleNotes.add(new Note(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[i], previousNote.getOctave() + 1, temp));
		}
	}

	//TODO: things below
	/**
	 * removes notes that form a complex interval with the previous note (more than an octave)
	 */
	public void removeSuperJumps()
	{
		removeIntervals(12);
	}
	
	/**
	 * removes any notes with the same interval (either up or down) as the input
	 * @param interval chromatic interval with d2 = 1, P8 = 12
	 */
	public void removeInterval(int interval)
	{
		
	}
	
	/**
	 * removes any notes with the same interval (either up or down) or more as the input
	 * @param intervalMin chromatic interval with d2 = 1, P8 = 12
	 */
	public void removeIntervals(int intervalMin)
	{
		
	}

	/**
	 * removes any notes with the same pitch as the input
	 * @param chromScaleIndex chromatic note with C=0 and B=11
	 */
	public void removePitch(int chromScaleIndex)
	{

	}

	/**
	 * removes any notes that have the same pitch as the bass of the chord
	 */
	public void removeBassNote()
	{
		removePitch(nextChord.getBassNote(SongWriter.key.getChromScaleIndex()));
	}

	/**
	 * removes any notes that does NOT have the same pitch as the bass of the chord
	 */
	public void removeAllExceptBassNote()
	{

	}
	
	/**
	 * removes any notes within/outside range (for both pitch and octave number)
	 * @param chromScaleIndexMin lowest chromatic pitch (inclusive)
	 * @param octaveMin lowest octave number (inclusive)
	 * @param chromScaleIndexMax highest chromatic pitch (inclusive)
	 * @param octaveMax highest octave number (inclusive)
	 * @param invert if true, remove pitches outside of range (exclusive)
	 */
	public void removePitchRange(int chromScaleIndexMin, int octaveMin, int chromScaleIndexMax, int octaveMax, boolean invert)
	{
		
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
		int index = 0;
		for (int i = 0; i < weights.length; i++)
		{
			for (int j = 0; j < weights[i]; j++)
			{
				indexes[index] = i;//stuffs[i];
				index++;
			}
		}

		int randomValue = (int) (Math.random() * weightSum);

		return stuffs[indexes[randomValue]];
	}

	//TODO: change int[] weights to arraylist of Integer (or just create new object holding a Note note and int weight)
	/**
	 * given an arraylist of stuff and an integer array representing the weight of the stuff, return a thing from the first array based on random weight
	 * @param stuffs something is chosen randomly from here
	 * @param weights these are the weights. higher means more chance of getting picked. x<=0 means no chance of getting picked
	 * @param <T> whatever thing you want to get. if primitive array, use wrappers
	 * @return randomly picked thing
	 */
	private <T> T calculateProbability(ArrayList<T> stuffs, ArrayList<Integer> weights)
	{
		if (stuffs.size() != weights.size())
			throw new IllegalArgumentException("make sure the stuff arraylist and the weight arraylist have the same length");

		int weightSum = 1; //starts with 1 because Math.random does not generate 1
		for (Integer weight : weights)
			weightSum += weight;

		//array of indexes
		int[] indexes = new int[weightSum - 1];
		int index = 0;
		for (int i = 0; i < weights.size(); i++)
		{
			for (int j = 0; j < weights.get(i); j++)
			{
				indexes[index] = i;//stuffs[i];
				index++;
			}
		}

		int randomValue = (int) (Math.random() * weightSum);

		return stuffs.get(indexes[randomValue]);
	}
}
