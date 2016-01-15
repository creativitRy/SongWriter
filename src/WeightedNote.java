/**
 *  The WeightedNote is like a normal Note but with an integer for weight
 *  @author Gahwon Lee
 */
 
 public class WeightedNote extends Note// implements Comparable<WeightedNote>
 {
 	private int weight;
 	
 	public WeightedNote(String name, int weight)
 	{
 		super(name);
 		this.weight = weight;
 	}
 	
 	public WeightedNote(int chromScaleIndex, boolean preferFlat, int weight)
 	{
 		super(chromScaleIndex, preferFlat);
 		this.weight = weight;
 	}
 	
 	public WeightedNote(int chromScaleIndex, int octaveNum, boolean preferFlat, int weight)
 	{
 		super(chromScaleIndex, octaveNum, preferFlat);
 		this.weight = weight;
 	}
 	
 	public int getWeight()
 	{
 		return weight;
 	}
 	
 	public void setWeight(int weight)
 	{
 		this.weight = weight;
 	}
 	
 	public void zeroWeight()
 	{
 		this.weight = 0;
 	}
 	
 	public Note getNote()
 	{
 		return this;
 	}
 	
 	//@Override
	public int compareTo(WeightedNote o) {
		return getWeight() - o.getWeight();
	}
 		
 }