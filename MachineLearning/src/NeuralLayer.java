import java.util.ArrayList;


public class NeuralLayer {
	
	ArrayList <NeuralNode> neuralNodes = new ArrayList <NeuralNode>();
	ArrayList <Double>     outputValues;
	int       numInputs;
	
	NeuralLayer(int pNumInputs, int numNodes)
	{
		numInputs = pNumInputs;
		
		for (int i = 0; i < numNodes; i++)
		{
			neuralNodes.add(new NeuralNode(numInputs));
		}
	}
	
	ArrayList <Double> evaluate(ArrayList <Double> inputs)
	{
		assert (inputs.size() == neuralNodes.get(0).inputWeights.size());
		
		for (int i = 0; i < neuralNodes.size(); i++)
		{
			outputValues.set(i, neuralNodes.get(i).evaluate(inputs));
		}
		
		return outputValues;
	}
	
	int getNumNeuralNodes()
	{
		return neuralNodes.size();
	}
}
