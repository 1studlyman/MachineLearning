import java.util.ArrayList;

public class NeuralNetwork {

	ArrayList <NeuralLayer> neuralLayers = new ArrayList <NeuralLayer>()	;
	
	private ArrayList <Double> input;
	
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
	
	void trainNetwork(ArrayList <Double> trainingInput, Double targetClass)
	{
		//If the output is incorrect, have the network mend paths
		Double netOutput = runData(trainingInput);
		//System.out.println("T: " + targetClass + " E: " + netOutput);
		if (!netOutput.equals(targetClass))
		{			
			Double error;
			Double target;
			
			//Temp variables for the sake of smaller lines of code
			NeuralLayer tempLayer;
			NeuralNode  tempNode; 
			
			//Correct the output layer first
			tempLayer = neuralLayers.get(neuralLayers.size() - 1);
			assert(targetClass <= tempLayer.getNumNeuralNodes());
			
			for (int i = 0; i < tempLayer.getNumNeuralNodes(); i++)
			{
				//The target is 1.0 for the output node representing the correct class index
				if(i == targetClass)
				{
					target = 1.0;
				}
				else
				{
					target = 0.0;
				}
				
				tempNode = tempLayer.neuralNodes.get(i);
				error = tempNode.output * (1.0 - tempNode.output) * (tempNode.output - target);
				
				//TODO Figure out if tempLayer is even necessary
				//Mend the node within the temp neural layer for later use of the weights
				tempLayer.neuralNodes.get(i).mendInputWieghts(learningRate, error);
				//Now mend our real network's output layer
				neuralLayers.set(neuralLayers.size() - 1, tempLayer);
			}
			
			//Now correct the hidden and input layers starting from the second-to-last layer
			// i = currentLayer
			// j = currentNode
			// k = currentNodeToTheRight
			for (int i = neuralLayers.size() - 2; i > 0; i--)
			{
				for (int j = 0; j < neuralLayers.get(i).getNumNeuralNodes(); j++)
				{
					//Calculate the error by using the error and weights of the layer to the right
					double sumOfErrors = 0.0;
					for (int k = 0; k < neuralLayers.get(i + 1).getNumNeuralNodes(); k++)
					{
						sumOfErrors += neuralLayers.get(i + 1).neuralNodes.get(k).inputWeights.get(j) * neuralLayers.get(i + 1).neuralNodes.get(k).lastCalculatedError;
					}
					error = neuralLayers.get(i).neuralNodes.get(j).output * (1.0 - neuralLayers.get(i).neuralNodes.get(j).output) * sumOfErrors;
					
					//Now mend the weights of the current layer's current node
					neuralLayers.get(i).neuralNodes.get(j).mendInputWieghts(learningRate, error);
				}				
			}
		}
	}
	
	
	//Runs a set of inputs through the network and generates an estimated class.
	Double runData(ArrayList <Double> pInput)
	{
		setInput(pInput);
		
		//Holds interLayer data and then the final output
		ArrayList <Double> tempData = input;
		
		for (int i = 0; i < neuralLayers.size(); i++)
		{
			tempData.add(bias); //Add the bias in every layer.
			tempData = neuralLayers.get(i).evaluate(tempData);
		}
		
		
		//The index of the output which has the greatest value is the classIndex selected
		double result = 0;
		double maxValue = 0;
		
		for (int i = 0; i < tempData.size(); i++)
		{
			if (tempData.get(i) > maxValue)
			{
				maxValue = tempData.get(i);
				result = (double)i;
			}
		}
		
		return result;
	}
	
	void setInput(ArrayList <Double> pInput)
	{
		//Check data integrity
		for (int i = 0; i < pInput.size(); i++)
			assert(!pInput.get(i).isNaN());
		input = pInput;
	}
}
