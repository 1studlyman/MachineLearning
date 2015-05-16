import java.util.ArrayList;

import weka.core.Instance;
import weka.core.Instances;


public class TreeBuilder extends Thread {

	TreeNode rootNode;
	
	public void run()
	{
		//Holder for the best set of branches and their information gain for every iteration
		ArrayList<TreeNode> bestBranches = null;
		double bestInformationGain = 1;
		int    bestAttributeIndex  = -1;
		
		//Go through every attribute
		int numAttributes = rootNode.instances.numAttributes();
			
		//Create a possible set of instances group for each attribute
		for (int i = 0; i < numAttributes; i++)
		{
			//Avoid classifying by the classifier attribute
			if (rootNode.instances.classIndex() != i)
			{
				int numAttributeValues = rootNode.instances.attribute(i).numValues();
				assert (numAttributeValues > 0);
			
			
				ArrayList<TreeNode> potentialBranches = new ArrayList(numAttributeValues);
				for (int j = 0; j < numAttributeValues; j++)
				{
					potentialBranches.add(new TreeNode(0));
					potentialBranches.get(j).instances = new Instances(rootNode.instances, 0); //Empty
				}
			
				for (int j = 0; j < rootNode.instances.numInstances(); j++)
				{
					Instance currentInstance = rootNode.instances.instance(j);
					potentialBranches.get((int)currentInstance.value(i)).instances.add(currentInstance);
				}
			
				//Calculate Entropy and the overall information gain
				double informationGain = 0;
				for (int k = 0; k < numAttributeValues; k++)
				{
					potentialBranches.get(k).entropy  = potentialBranches.get(k).calculateEntropy(potentialBranches.get(k).instances);
					informationGain = informationGain + potentialBranches.get(k).entropy;
				}
				
				//Determine if this set has the best information gain thus far
				if (informationGain < bestInformationGain)
				{
					bestBranches = potentialBranches;
					bestAttributeIndex = i;
					rootNode.attributeIndexUsed = bestAttributeIndex;
				}
			}
		}
		
		//Print the best branches before attaching them to the rootNode
		for (int i = 0; i < bestBranches.size(); i++)
		{
			for (int j = 0; j < rootNode.getLevel(); j++)
			{
				System.out.print("   ");
			}
			System.out.println("L" + rootNode.getLevel() + "   Attr: " + bestAttributeIndex + "   Stats: ");
			
			for (int j = 0; j < rootNode.getLevel(); j++)
			{
				System.out.print("   ");
			}
			System.out.println(bestBranches.get(i).instances.attributeStats(bestAttributeIndex).toString());
		}		
			
		//Attach the best branches to the current node as children and remove the attribute used
		for (int i = 0; i < bestBranches.size(); i++)
		{
			bestBranches.get(i).instances.deleteAttributeAt(bestAttributeIndex);
			rootNode.addChildNode(bestBranches.get(i));
		}
		
		//Start a new thread for each child node that's not a leaf to continue building the tree
		ArrayList <TreeBuilder> childThreads = new ArrayList<TreeBuilder>(rootNode.getChildren().size());
		for (int i = 0; i < rootNode.getChildren().size(); i++)
		{
			if (rootNode.getChildren().get(i).instances.numAttributes() > 0 &&
				rootNode.getChildren().get(i).entropy > 0) //Leaf check
			{
				TreeBuilder newThread = new TreeBuilder();
				newThread.rootNode = rootNode.getChildren().get(i);
				newThread.start();
				
				//Add our new thread (which is running now) to our list of threads
				childThreads.add(newThread);
			}
		}
		
		//Now wait till all the children are done
		for (int i = 0; i < childThreads.size(); i++)
		{
			try {
				childThreads.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
