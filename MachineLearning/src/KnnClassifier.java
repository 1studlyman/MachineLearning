import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;

class Neighbor 
{
	double distance;
	int    classIndex;
}

public class KnnClassifier extends Classifier {

	int k = 1;  //TODO: Add an ability to change this by the caller function.
	Standardize standardize; //Will standardize and filter numeric inputs
	Instances   standardizedInstances;
	
	@Override
	public void buildClassifier(Instances instances) throws Exception {
		standardize = new Standardize();
		standardize.setInputFormat(instances);
		
		standardizedInstances = Filter.useFilter(instances, standardize);
		
	}
	
	@Override
	public double classifyInstance(Instance instance)
	{
		try {
			System.out.println("nonFiltd: " + instance);
			standardize.input(instance);
			Instance filteredInstance = standardize.output();
			System.out.println("Filtered: " + filteredInstance);
			
			ArrayList<Neighbor> neighbors = new ArrayList<>();
			
			//Find the nearest k neighbor instances for each numeric attribute
			for (int i = 0; i < standardizedInstances.numAttributes(); i++)
			{
				standardizedInstances.sort(i); //Sort so we can get the neighbors
				for (int j = k; j < standardizedInstances.numInstances() - k; j++)
				{
					//Record each neighbor and add to their respective overall distance
					if (standardizedInstances.instance(j - k).attribute(i) <= filteredInstance.attribute(i) &&
					    standardizedInstances.instance(j + k).attribute(i) >= filteredInstance.attribute(i))
					{
						//TODO: Finish this lab. The above won't work and I ran out of time. 
					}
				}
			}
			
			//Whichever neighbor with the smallest distance wins!
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return 0;
	}

}
