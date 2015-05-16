import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

@SuppressWarnings("serial")
public class ID3Classifier extends Classifier 
{
	Discretize  discretize; //Will standardize and filter numeric inputs
	Instances   discretizedInstances;
	
	TreeNode rootNode;
	
	@Override
	public void buildClassifier(Instances instances) throws Exception {
		//Standardize the numerical attributes
		discretize = new Discretize();
		discretize.setInputFormat(instances);
		
		discretizedInstances = Filter.useFilter(instances, discretize);
		
		//Build the Tree
		//We use the class index value for the leaf node value (if a leaf node)
		//We use the attribute value for determining the index of the child nodes
		// Each child node contains instances where all have the same attribute value.
		
		
		//Here's the root of our final tree once it's built
		rootNode = new TreeNode(0, discretizedInstances);
		rootNode.entropy = rootNode.calculateEntropy(rootNode.instances);

		TreeBuilder treeBuilder = new TreeBuilder();
		treeBuilder.rootNode = rootNode;
		treeBuilder.start();
		treeBuilder.join();
	}
	
	@Override
	public double classifyInstance(Instance instance)
	{
		TreeNode currentNode = rootNode;
		
		while(currentNode.children.size() > 0)
		{
			currentNode = currentNode.getChildren().get((int)instance.value(currentNode.attributeIndexUsed));
		}
		
		System.out.println(currentNode.instances.instance(0).classValue());
		return currentNode.instances.instance(0).classValue();
	}
}


