import java.util.ArrayList;
import java.util.Random;


public class NeuralNode {
	
	ArrayList<Double> inputWeights = new ArrayList<Double>();
	Random r = new Random();
	Double output;
	
	NeuralNode(int numInputs)
	{	
		//Create the inputWeights with random initial wieghts
		for (int i = 0; i < numInputs; i++)
			inputWeights.add(getRandomWeight());
	}
	
	Double getRandomWeight()
	{
		Double tempDouble = Double.MAX_VALUE * r.nextDouble();
		if (r.nextBoolean())
			tempDouble = -tempDouble;
		
		return tempDouble; 
	}
	
	Double evaluate(ArrayList<Double> inputs)
	{
		assert (inputs.size() == inputWeights.size());
		
		Double result = 0.0;
		
		for (int i = 0; i < inputWeights.size(); i++)
		{
			result += inputWeights.get(i) * inputs.get(i);
		}
		
		return result;
	}
	
	void updatePath()
	{
		
	}
}
