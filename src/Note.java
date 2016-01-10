/**
 *  The Note class represents a note letter, octave number, and pertinent sharps/flats.
 *  @author Henry Wang
 */
public class Note implements Comparable<Note>{
	private static final int[] letterIndexes = {8,10,0,2,3,5,7};
	
	private final int letter;
	private final int octave;
	private final int stepAdjust; //This determines whether if there is a sharp or flat.
	
	private static int cyclic(int num, int period) {
		return num - (num / period) * period;
	}
	
	/**
	 * Constructor for a Note in standard music notation: Note letter, followed by sharp/flat, followed by octave number(ex. A#3)
	 * Note: The double sharp will be notated as x (lower-case 'x'), and flats as 'b', and double-flats as 'bb'. 
	 * @param name name of this note
	 */
	public Note(String name) {
		name = name.toLowerCase();
		char first = name.charAt(0);
		if (first < 'a' || first > 'g') {
			throw new IllegalArgumentException("Letter out of range");
		}else {
			this.letter = first - 'a';
		}
		
		int index = 2; //Index where we should find the octave number
		int adjust = 0;
		char second = name.charAt(1);
		switch (name.charAt(1)) {
		case '#': //Sharp
			adjust = 1;
			break;
		case 'x': //Double-sharp
			adjust = 2;
			break;
		case 'b': //Flat or Double-flat
			if (name.charAt(2) == 'b') {
				index++;
				adjust = -2;
			}else {
				adjust = -1;
			}
			break;
		default: //This note is natural. Next index should be a number
			index--;
		}
		stepAdjust = adjust;
		
		char octaveChar = name.charAt(index);
		if (octaveChar < '0' || octaveChar > '9') {
			throw new IllegalArgumentException("Cannot parse octave number (single digit from 0-9)");
		}
		
		octave = octaveChar - '0';
	}
	
	/**
	 * Constructor for a Note (no sharps/flats) with integer value of note letter and octave
	 * @param letter letter name: A=0,B=1,C=2...G=6
	 * @param octave octave number of this note
	 */
	public Note(int letter, int octave) {
		this(letter, octave, 0);
	}
	
	/**
	 * Constructor for a Note with the NoteLetter object for letter and octave number
	 * @param letter letter object for note
	 * @param octave octave number of this note
	 */
	public Note(NoteLetter letter, int octave) {
		this(letter.getLetter(), letter.getStepAdjust(), octave);
	}
	
	/**
	 * Constructor for a Note with integer values of note letter, octave, and adjustments (whether if there are sharps or flats)
	 * @param letter letter name: A=0,B=1,C=2...G=6
	 * @param octave octave number of this note
	 * @param stepAdjust whether if this note is sharp. Ex. if this note is sharped then stepAdjust is 1, if note is flatted, stepAdjust is -1
	 */
	public Note(int letter, int octave, int stepAdjust) {
		this.letter = letter;
		this.octave = octave;
		this.stepAdjust = stepAdjust;
	}
	
	//Getters
	public int getLetter() {
		return letter;
	}
	public int getOctave() {
		return octave;
	}
	public int getStepAdjust() {
		return stepAdjust;
	}
	
	/**
	 * Obtains the chromatic scale index, where C has the index 0.
	 * @return the chromatic scale index represented by this note. (Always between 0 and 11).
	 */
	public int getChromScaleIndex() {
		return cyclic(letterIndexes[letter] + stepAdjust, 12);
	}

	/**
	 * Obtains the chromatic-scale index of this note (related to the key index of a piano), where C0 has the index 0;
	 * @return the chromatic scale index represented by this note.
	 */
	public int getFullChromScaleIndex() {
		return letterIndexes[letter] + stepAdjust + octave * 12;
	}
	
	/**
	 * Compares two notes whether which one is higher or lower. Returns a negative number if this note is lower then other note, and vice versa.
	 * @param o the other note to compare with.
	 */
	@Override
	public int compareTo(Note o) {
		return getFullChromScaleIndex() - o.getFullChromScaleIndex();
	}
	
	
}