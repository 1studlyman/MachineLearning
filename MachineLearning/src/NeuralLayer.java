import java.util.ArrayList;


public class NeuralLayer {
	
	ArrayList <NeuralNode> neuralNodes  = new ArrayList <NeuralNode>();
	ArrayList <Double>     inputValues  = new ArrayList <Double>();
	ArrayList <Double>     outputValues = new ArrayList <Double>();
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
		assert (neuralNodes.size() > 0);
		assert (inputs.size() == neuralNodes.get(0).inputWeights.size());
		
		inputValues = inputs;
		
		outputValues.clear();
		
		for (int i = 0; i < neuralNodes.size(); i++)
		{
			outputValues.add(neuralNodes.get(i).evaluate(inputs));
		}
		
		return outputValues;
	}
	
	int getNumNeuralNodes()
	{
		return neuralNodes.size();
	}
}
