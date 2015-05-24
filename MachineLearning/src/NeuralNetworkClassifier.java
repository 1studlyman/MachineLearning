import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;


@SuppressWarnings("serial")
public class NeuralNetworkClassifier extends Classifier{

	@Override
	public void buildClassifier(Instances instances) throws Exception 
	{
		//TODO How to make these optimized values
		int numNodes = 20;
		int numLayers = 10;
		
		NeuralNetwork neuralNet = new NeuralNetwork(instances.numAttributes(), numNodes, numLayers, instances.numClasses());
		
		
		for (int i = 0; i < instances.numInstances(); i++)
		{
			ArrayList <Double> trainingInput = new ArrayList<Double>();
			for (int j = 0; j < instances.instance(i).numAttributes(); j++)
			{
				trainingInput.add(instances.instance(i).value(j)); 
			}

			neuralNet.trainNetwork(trainingInput, new ArrayList <Double>()); //TODO When implementing the training, remove this and set it to the target
		}
		
		
	}
	
	@Override
	public double classifyInstance(Instance instance)
	{
		return 0; //So arbitrary it hurts!
	}
	
	
}
