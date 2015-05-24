import java.util.ArrayList;

public class NeuralNetwork {

	ArrayList <NeuralLayer> neuralLayers = new ArrayList <NeuralLayer>()	;
	
	private ArrayList <Double> input;
	private ArrayList <Double> output;
	
	int    numNodesPerLayer = 10; //These are arbitrary. TODO: Figure out an algorithmic way to set this. 
	Double bias             = -1.0;
	Double learningRate     = 1.0;
	
	NeuralNetwork(int numInputs, int numNodes, int numLayers, int numOutputs)
	{
		assert(numInputs > 0 && numNodes > 0 && numLayers > 0 && numOutputs > 0);
		
		//The first layer must have as many inputs as needed by the network
		neuralLayers.add(new NeuralLayer(numInputs, numNodes));
		
		//The rest of the layers must have as many inputs as there are nodes in the previous layer
		for (int i = 1; i < numLayers - 1; i++)
		{
			neuralLayers.add(new NeuralLayer(neuralLayers.get(i - 1).getNumNeuralNodes(), numNodes));
		}
		
		//The last layer must have the same nodes as outputs desired
		NeuralLayer lastLayer = new NeuralLayer(neuralLayers.get(neuralLayers.size() - 1).numInputs, numOutputs);
		neuralLayers.add(lastLayer);
	}
	
	void trainNetwork(ArrayList <Double> trainingInput, ArrayList <Double> targetOutput)
	{
		//Ensure there are exactly the number of nodes in the last layer for how many outputs are expected
		assert (targetOutput.size()  == neuralLayers.get(neuralLayers.size() - 1).getNumNeuralNodes());
	
		//If the output is incorrect, have the network mend paths
		
	}
	
	void runData(ArrayList <Double> pInput)
	{
		setInput(pInput);
		
		//Holds interLayer data
		ArrayList <Double> tempData = input;
		
		for (int i = 0; i < neuralLayers.size(); i++)
		{
			tempData = neuralLayers.get(i).evaluate(tempData);
		}
		
		output = tempData;
	}
	
	void setInput(ArrayList <Double> pInput)
	{
		input = pInput;
		input.add(0, bias); // Insert the bias
	}
}
