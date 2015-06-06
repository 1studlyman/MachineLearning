import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;


public class NeuralNode {
	
	ArrayList<Double> inputWeights = new ArrayList<Double>();
	Random r = new Random();
	Double output;
	Double lastCalculatedError = 0.0;
	
	NeuralNode(int numInputs)
	{	
		//Create the inputWeights with random initial weights
		//The sum of the weights is 1.0
		Double remainingWeight = 1.0;
		Double tempNum;
		
		//Have as many weights for the number of inputs plus one for the bias input
		for (int i = numInputs + 1; i > 1;)
		{
			tempNum = getRandomWeight();
			if (tempNum <= remainingWeight)
			{
				inputWeights.add(tempNum);
				remainingWeight -= tempNum;
				i--;
			}
		}
		//Put the remainder onto as a weight
		inputWeights.add(remainingWeight);
	}
	
	Double getRandomWeight()
	{
		Double tempDouble = r.nextDouble();  
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
			assert(!inputs.get(i).isNaN());
			result += inputWeights.get(i) * inputs.get(i);
			assert(!result.isNaN());
		}
		
		assert(!result.isNaN());
		
		result = 1.0 / (1.0 + Math.pow(Math.E, -result));
		
		assert(!result.isNaN());
		
		//Make sure we save this output for later mending
		output = result;
		
		return result;
	}
	
	void mendInputWieghts(Double learningRate, Double error)
	{
		Double oldWeight;
		Double newWeight;
		
		lastCalculatedError = error;
		
		for (int i = 0; i < inputWeights.size(); i++)
		{
			oldWeight = inputWeights.get(i);
			newWeight = oldWeight - (learningRate * error * output);
			assert (!newWeight.isNaN());
			
			//System.out.println(oldWeight + " " + newWeight);
			
			inputWeights.set(i, newWeight);
		}

	}
}
