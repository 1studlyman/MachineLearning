import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.NominalToBinary;


@SuppressWarnings("serial")
public class NeuralNetworkClassifier extends Classifier{
	
	NominalToBinary nominalToBinary = new NominalToBinary();
	NeuralNetwork neuralNet;
	

	@Override
	public void buildClassifier(Instances instances) throws Exception 
	{
		//TODO How to make these optimized values
		int numNodes  = instances.numAttributes();
		int numLayers =  3; //Must be at least three. 
		
		int numTrainings = 1000;
		
		//Preprocessing for creating all-numeric attributes including previously nominal one
		nominalToBinary.setInputFormat(instances);
		Instances filteredInstances = NominalToBinary.useFilter(instances, nominalToBinary);
		
		
		
		//Number of nodes in the input layer (attribute count - classAttribute, number of nodes in hidden layers, number of hidden layers, and number of nodes in output layer. 
		neuralNet = new NeuralNetwork(filteredInstances.numAttributes() - 1, numNodes, numLayers, filteredInstances.numClasses());
		
		for (int k = 0; k < numTrainings; k++)
			for (int i = 0; i < filteredInstances.numInstances(); i++)
			{
				ArrayList <Double> trainingInput = new ArrayList<Double>();
				for (int j = 0; j < filteredInstances.instance(i).numAttributes(); j++)
				{
					//Exclude the class attribute
					if (filteredInstances.classIndex() != j)
						trainingInput.add(filteredInstances.instance(i).value(j)); 
				}

				neuralNet.trainNetwork(trainingInput, filteredInstances.instance(i).classValue());
			}
		
		
	}
	
	@Override
	public double classifyInstance(Instance instance)
	{
		//Preprocessing to ensure nominal values are made into binary ones
		nominalToBinary.input(instance);
		Instance filteredInstance = nominalToBinary.output();
		
		ArrayList<Double> instanceInput = new ArrayList<Double>();
		
		for (int i = 0; i < filteredInstance.numAttributes(); i++)
		{
			//Filter out the class attribute
			if (filteredInstance.classIndex() != i)
			{
				Double d = filteredInstance.value(i);
				assert(!d.isNaN());

				instanceInput.add(filteredInstance.value(i));
			}
		}
		
		assert(instanceInput.size() > 0);
		
		return neuralNet.runData(instanceInput);
	}
	
	
}
