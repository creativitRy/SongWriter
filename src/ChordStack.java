/**
 *  ChordStack object holds info about a specific chord
 *  either increase or decrease the population.
 *  @author Gahwon Lee, Henry Wang
 *  Period: 0
 *  Date: 01-05-16
 */

import java.util.*;

public class ChordStack
{
	private int root; //0=tonic, 6=subtonic/leading tone
	private boolean minor; //true=minor root, false=major root
	private boolean seventh; //true if chord is 7th, false if chord is triad
	private int inversion; //0=none, 1=1st, 2=2nd, 3=3rd
	
	public ChordStack()
	{
		root = 0;
		minor = false;
		seventh = false;
		inversion = 0;
	}
	public ChordStack(int rootGiven, boolean isMinorGiven)
	{
		root = rootGiven;
		minor = isMinorGiven;
		seventh = false;
		inversion = 0;
	}
	public ChordStack(int rootGiven, boolean isMinorGiven, boolean isSeventhGiven)
	{
		root = rootGiven;
		minor = isMinorGiven;
		seventh = isSeventhGiven;
		inversion = 0;
	}
	public ChordStack(int rootGiven, boolean isMinorGiven, int invGiven)
	{
		root = rootGiven;
		minor = isMinorGiven;
		seventh = (invGiven>2);
		inversion = invGiven;
	}
	public ChordStack(int rootGiven, boolean isMinorGiven, boolean isSeventhGiven, int invGiven)
	{
		root = rootGiven;
		minor = isMinorGiven;
		seventh = isSeventhGiven;
		inversion = invGiven;
	}
	
	public ChordStack(String romanNum, boolean isMinorGiven)
	{
		minor = isMinorGiven;
		
		if (romanNum.toLowerCase().indexOf("vii") != -1)
			root = 6;
		else if (romanNum.toLowerCase().indexOf("vi") != -1)
			root = 5;
		else if (romanNum.toLowerCase().indexOf("iv") != -1)
			root = 3;
		else if (romanNum.toLowerCase().indexOf("v") != -1)
			root = 4;
		else if (romanNum.toLowerCase().indexOf("iii") != -1)
			root = 2;
		else if (romanNum.toLowerCase().indexOf("ii") != -1)
			root = 1;
		else// if (romanNum.toLowerCase().indexOf("i") != -1)
			root = 0;
			
		if (romanNum.indexOf("42") != -1)
		{
			inversion = 3;
			seventh = true;
		}
		else if (romanNum.indexOf("43") != -1)
		{
			inversion = 2;
			seventh = true;
		}
		else if (romanNum.indexOf("65") != -1)
		{
			inversion = 1;
			seventh = true;
		}
		else if (romanNum.indexOf("7") != -1)
		{
			inversion = 0;
			seventh = true;
		}
		else if (romanNum.indexOf("64") != -1)
			inversion = 2;
		else if (romanNum.indexOf("6") != -1)
			inversion = 1;
		else
			inversion = 0;
	}
	/* private int root; //0=tonic, 6=subtonic/leading tone
	private boolean isMinor; //true=minor root, false=major root
	private boolean isSeventh; //true if chord is 7th, false if chord is triad
	private int inversion; //0=none, 1=1st, 2=2nd, 3=3rd
	 */
	public int getRoot()
	{
		return root;
	}
	
	public boolean isMinor()
	{
		return minor;
	}
	
	public boolean isSeventh()
	{
		return seventh;
	}
	
	public int getInversion()
	{
		return inversion;
	}
	
	public String getFiguredBassInversion()
	{
		if (seventh)
		{
			if (inversion == 0)
				return "7";
			else if (inversion == 1)
				return "65";
			else if (inversion == 2)
				return "43";
			else
				return "42";
		}
		else
		{
			if (inversion == 0)
				return "";
			else if (inversion == 1)
				return "6";
			else
				return "64";
		}
	}
	/**
	 * Identifies the intervals for each scale degree
	 * array[num] is between num note and (num+1)%7 note
	 * @return Array of steps for each scale degree starting from tonic note. 
	 */
	public int[] getIntervals()
	{
		int[] majorIntervals = {2,2,1,2,2,2,1};
		int[] minorIntervals = {2,1,2,2,1,3,1};
		
		if (minor)
			return minorIntervals;
		return majorIntervals;
	}
	
	public int getNote(int noteNum)
	{
		int note = 0;
		int[] intervals = getIntervals();
			
		for (int i = 0; i < (noteNum % 12); i++)
		{
			note = (note + intervals[i]) % 12;
		}
		
		return note;
	}
	public int getNote(int noteNum, int tonic)
	{
		int note = tonic;
		int[] intervals = getIntervals();
			
		for (int i = 0; i < (noteNum % 12); i++)
		{
			note = (note + intervals[i]) % 12;
		}
		
		return note;
	}
	
	public int getBassNote()
	{
		int bassNote = getNote(root + inversion * 2);
		
		return bassNote;
	}
	public int getBassNote(int tonic)
	{
		int bassNote = getNote(root + inversion * 2, tonic);
		
		return bassNote;
	}
	
	/**
	 *  returns all notes in an array
	 *  [0] = root
	 */
	public int[] getAllNotes()
	{
		int notes[] = new int[(seventh?4:3)];
		
		notes[0] = getNote(root);
		notes[1] = getNote(root + 2);
		notes[2] = getNote(root + 4);
		if (seventh)
			notes[3] = getNote(root + 6);
		
		return notes;
	}
	public int[] getAllNotes(int tonic)
	{
		int notes[] = new int[(seventh?4:3)];
		
		notes[0] = getNote(root, tonic);
		notes[1] = getNote(root + 2, tonic);
		notes[2] = getNote(root + 4, tonic);
		if (seventh)
			notes[3] = getNote(root + 6, tonic);
		
		return notes;
	}
	/**
	 *  returns all notes in an array
	 *  [0] = bass if param is true
	 */
	public int[] getAllNotes(boolean startFromBass)
	{
		int notes[] = new int[(seventh?4:3)];
		
		notes[0] = getNote(root);
		notes[1] = getNote(root + 2);
		notes[2] = getNote(root + 4);
		if (seventh)
			notes[3] = getNote(root + 6);
		
		if (!startFromBass)
			return notes;
		
		int newNotes[] = new int[(seventh?4:3)];
		
		newNotes[0] = notes[inversion];
		newNotes[1] = notes[(inversion + 1) % (seventh?4:3)];
		newNotes[2] = notes[(inversion + 2) % (seventh?4:3)];
		if (seventh)
			newNotes[3] = notes[(inversion + 3) % (seventh?4:3)];
		
		return newNotes;
	}
	public int[] getAllNotes(boolean startFromBass, int tonic)
	{
		int notes[] = new int[(seventh?4:3)];
		
		notes[0] = getNote(root, tonic);
		notes[1] = getNote(root + 2, tonic);
		notes[2] = getNote(root + 4, tonic);
		if (seventh)
			notes[3] = getNote(root + 6, tonic);
		
		if (!startFromBass)
			return notes;
		
		int newNotes[] = new int[(seventh?4:3)];
		
		newNotes[0] = notes[inversion];
		newNotes[1] = notes[(inversion + 1) % (seventh?4:3)];
		newNotes[2] = notes[(inversion + 2) % (seventh?4:3)];
		if (seventh)
			newNotes[3] = notes[(inversion + 3) % (seventh?4:3)];
		
		return newNotes;
	}
	public String toString()
	{
		return Arrays.toString(getAllNotes(true));
	}
}