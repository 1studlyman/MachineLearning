import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class HardcodeClassifier extends Classifier {

	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double classifyInstance(Instance instance)
	{
		return 0; //So arbitrary it hurts!
	}

}
