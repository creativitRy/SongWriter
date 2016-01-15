/**
 *  NotePossibilities object holds info about what all the possible notes a note can travel to
 *  @author Gahwon Lee, Henry Wang
 */

import java.util.*;

public class NotePossibilities
{
	private Note previousNote;
	private ChordStack nextChord;
	private ArrayList<WeightedNote> possibleNotes = new ArrayList<WeightedNote>();

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
			possibleNotes.add(new WeightedNote(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[i], previousNote.getOctave(), temp, 1));
			possibleNotes.add(new WeightedNote(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[i], previousNote.getOctave() - 1, temp, 1));
			possibleNotes.add(new WeightedNote(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[i], previousNote.getOctave() + 1, temp, 1));
		}
	}

	/**
	 * adds a note that is within the next chord with a weight of 0.
	 * @param noteInChord which note of the next chord to put in (0=root, 1=third, 2=5th, 3=7th)
	 */
	public void addNoteInChord(int noteInChord)
	{
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
		
		possibleNotes.add(new WeightedNote(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[noteInChord], previousNote.getOctave(), temp, 1) );
		possibleNotes.add(new WeightedNote(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[noteInChord], previousNote.getOctave() - 1, temp, 1) );
		possibleNotes.add(new WeightedNote(nextChord.getAllNotes(SongWriter.key.getChromScaleIndex() )[noteInChord], previousNote.getOctave() + 1, temp, 1) );
	}
	
	/**
	 * removes notes that form a complex interval with the previous note (more than an octave)
	 */
	public void removeSuperJumps()
	{
		removeIntervals(13);
	}
	public void setWeightSuperJumps(int weight)
	{
		setWeightIntervals(13, weight);
	}
	
	
	/**
	 * removes any notes with the same interval (either up or down) as the input
	 * @param interval chromatic interval with d2 = 1, P8 = 12
	 */
	public void removeInterval(int interval)
	{
		removeInterval(interval, true);
		removeInterval(interval, false);
	}
	public void setWeightInterval(int interval, int weight)
	{
		setWeightInterval(interval, true, weight);
		setWeightInterval(interval, false, weight);
	}
	public void removeInterval(int interval, boolean isUp)
	{
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0 && (isUp && possibleNotes.get(i).getFullChromScaleIndex() >= previousNote.getFullChromScaleIndex()) || (!isUp && possibleNotes.get(i).getFullChromScaleIndex() <= previousNote.getFullChromScaleIndex()))
			{
				if (Math.abs(possibleNotes.get(i).getFullChromScaleIndex() - previousNote.getFullChromScaleIndex()) == interval)
					possibleNotes.get(i).zeroWeight();
			}
		}
	}
	public void setWeightInterval(int interval, boolean isUp, int weight)
	{
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0 && (isUp && possibleNotes.get(i).getFullChromScaleIndex() >= previousNote.getFullChromScaleIndex()) || (!isUp && possibleNotes.get(i).getFullChromScaleIndex() <= previousNote.getFullChromScaleIndex()))
			{
				if (Math.abs(possibleNotes.get(i).getFullChromScaleIndex() - previousNote.getFullChromScaleIndex()) == interval)
					possibleNotes.get(i).setWeight(weight);
			}
		}
	}
	
	/**
	 * removes any notes with the same interval (either up or down) or more as the input
	 * @param intervalMin chromatic interval with d2 = 1, P8 = 12
	 */
	public void removeIntervals(int intervalMin)
	{
		removeIntervals(intervalMin, true);
		removeIntervals(intervalMin, false);
	}
	public void setWeightIntervals(int intervalMin, int weight)
	{
		setWeightIntervals(intervalMin, true, weight);
		setWeightIntervals(intervalMin, false, weight);
	}
	public void removeIntervals(int intervalMin, boolean isUp)
	{
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0 && (isUp && possibleNotes.get(i).getFullChromScaleIndex() >= previousNote.getFullChromScaleIndex()) || (!isUp && possibleNotes.get(i).getFullChromScaleIndex() <= previousNote.getFullChromScaleIndex()))
			{
				if (Math.abs(possibleNotes.get(i).getFullChromScaleIndex() - previousNote.getFullChromScaleIndex()) >= intervalMin)
					possibleNotes.get(i).zeroWeight();
			}
		}
	}
	public void setWeightIntervals(int intervalMin, boolean isUp, int weight)
	{
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0 && (isUp && possibleNotes.get(i).getFullChromScaleIndex() >= previousNote.getFullChromScaleIndex()) || (!isUp && possibleNotes.get(i).getFullChromScaleIndex() <= previousNote.getFullChromScaleIndex()))
			{
				if (Math.abs(possibleNotes.get(i).getFullChromScaleIndex() - previousNote.getFullChromScaleIndex()) >= intervalMin)
					possibleNotes.get(i).setWeight(weight);
			}
		}
	}

	/**
	 * removes any notes with the same pitch as the input
	 * @param chromScaleIndex chromatic note with C=0 and B=11
	 */
	public void removePitch(int chromScaleIndex)
	{
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0 && possibleNotes.get(i).getChromScaleIndex() == chromScaleIndex)
				possibleNotes.get(i).zeroWeight();
		}
	}
	public void setWeightPitch(int chromScaleIndex, int weight)
	{
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0 && possibleNotes.get(i).getChromScaleIndex() == chromScaleIndex)
				possibleNotes.get(i).setWeight(weight);
		}
	}

	/**
	 * removes any notes that have the same pitch as the bass of the chord
	 */
	public void removeBassNote()
	{
		removePitch(nextChord.getBassNote(SongWriter.key.getChromScaleIndex()));
	}
	public void setWeightBassNote(int weight)
	{
		setWeightPitch(nextChord.getBassNote(SongWriter.key.getChromScaleIndex()), weight);
	}

	/**
	 * removes any notes that does NOT have the same pitch as the bass of the chord
	 */
	public void removeAllExceptBassNote()
	{
		int[] notePitches = nextChord.getAllNotes(true, SongWriter.key.getChromScaleIndex());
		
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0 && possibleNotes.get(i).getChromScaleIndex() != nextChord.getBassNote(SongWriter.key.getChromScaleIndex()))
				possibleNotes.get(i).zeroWeight();
		}
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
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0)
			{
				if (invert)
				{
					if (possibleNotes.get(i).getFullChromScaleIndex() < (chromScaleIndexMin + octaveMin * 12) || possibleNotes.get(i).getFullChromScaleIndex() > (chromScaleIndexMax + octaveMax * 12))
					{
						possibleNotes.get(i).zeroWeight();
					}
				}
				else
				{
					if (possibleNotes.get(i).getFullChromScaleIndex() >= (chromScaleIndexMin + octaveMin * 12) && possibleNotes.get(i).getFullChromScaleIndex() <= (chromScaleIndexMax + octaveMax * 12))
					{
						possibleNotes.get(i).zeroWeight();
					}
				}
				
			}
		}
	}
	public void setWeightPitchRange(int chromScaleIndexMin, int octaveMin, int chromScaleIndexMax, int octaveMax, boolean invert, int weight)
	{
		for (int i = 0; i < possibleNotes.size(); i++)
		{
			if (possibleNotes.get(i).getWeight() != 0)
			{
				if (invert)
				{
					if (possibleNotes.get(i).getFullChromScaleIndex() < (chromScaleIndexMin + octaveMin * 12) || possibleNotes.get(i).getFullChromScaleIndex() > (chromScaleIndexMax + octaveMax * 12))
					{
						possibleNotes.get(i).setWeight(weight);
					}
				}
				else
				{
					if (possibleNotes.get(i).getFullChromScaleIndex() >= (chromScaleIndexMin + octaveMin * 12) && possibleNotes.get(i).getFullChromScaleIndex() <= (chromScaleIndexMax + octaveMax * 12))
					{
						possibleNotes.get(i).setWeight(weight);
					}
				}
				
			}
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
	
	public Note getRandomNote()
	{
		return calculateProbability(possibleNotes);
	}

	/**
	 * given an arraylist of stuff and an integer array representing the weight of the stuff, return a thing from the first array based on random weight
	 * @param stuffs something is chosen randomly from here
	 * @param weights these are the weights. higher means more chance of getting picked. x<=0 means no chance of getting picked
	 * @param <T> whatever thing you want to get. if primitive array, use wrappers
	 * @return randomly picked thing
	 *//*
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
	}*/
	
	/**
	 *  Same method but with WeightedNote
	 */
	private Note calculateProbability(ArrayList<WeightedNote> stuffs)
	{
		int weightSum = 1; //starts with 1 because Math.random does not generate 1
		for (WeightedNote weight : stuffs)
			weightSum += weight.getWeight();
		
		//array of indexes
		int[] indexes = new int[weightSum];
		//System.out.println(Arrays.toString(indexes));
		int index = 0;
		for (int i = 0; i < stuffs.size(); i++)
		{
			int temp = stuffs.get(i).getWeight();
			for (int j = 0; j < temp; j++)
			{
				indexes[index] = i;//stuffs[i];
				index++;
			}
		}
		//System.out.println(Arrays.toString(indexes));
		int randomValue = (int) (Math.random() * weightSum);
		if (weightSum == 0)
			return new Note("c0");
		return stuffs.get(indexes[randomValue]).getNote();
	}
}
