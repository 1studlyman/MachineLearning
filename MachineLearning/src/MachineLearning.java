import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.core.FastVector;
import java.util.Random;

public class MachineLearning {
	/**
	 * @param args
	 * @throws Exception on dataset reading from the CSV file.
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Opening the data file.");
		
		String filename = "IrisDataSet.csv"; //What we will be testing
		
		//Read the dataset from the csv file
		DataSource source = new DataSource(filename);
		Instances  data = source.getDataSet();
		data.setClassIndex(4); //The name is the class index

		System.out.println("Data file read.");	
		
		
		//Randomize and divide into training and test sets
		data.randomize(new Random());
		Instances trainingData = new Instances(data, 0, 99);
		Instances testingData  = new Instances(data, 99, 50);
		
		/*
		//Hardcoded Classifier
		HardcodeClassifier hardcodeClassifier = new HardcodeClassifier();
		hardcodeClassifier.buildClassifier(trainingData);
		*/
		
		//Knn Classifier
		KnnClassifier knnClassifier = new KnnClassifier();
		knnClassifier.buildClassifier(trainingData);
		
		//Here's where we will run the evaluation
		Evaluation evaluation = new Evaluation(testingData);
		evaluation.evaluateModel(knnClassifier, testingData);
	
		//Print the results of the evaluation
		System.out.println(evaluation.toSummaryString());
	}

}
