import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.filters.unsupervised.attribute.NumericToNominal;

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
		Instances trainingData = new Instances(data, 0, (data.numInstances()));
		Instances testingData  = new Instances(data, 0, (data.numInstances()));
		
		System.out.println("Creating classifier");
		
		/*
		//Hardcoded Classifier
		HardcodeClassifier hardcodeClassifier = new HardcodeClassifier();
		hardcodeClassifier.buildClassifier(trainingData);
		*/
		
		/*
		//Knn Classifier
		KnnClassifier knnClassifier = new KnnClassifier();
		knnClassifier.buildClassifier(trainingData);
		*/
		
		/*
		//ID3 Classifier
		ID3Classifier id3 = new ID3Classifier();
		id3.buildClassifier(trainingData);
		*/
		
		//Neural Network Classifier
		//NeuralNetworkClassifier neuralNetworkClassifier = new NeuralNetworkClassifier();
		//neuralNetworkClassifier.buildClassifier(trainingData);
		
		MultilayerPerceptron neuralNetworkClassifier = new MultilayerPerceptron();
		neuralNetworkClassifier.buildClassifier(trainingData);
		
		System.out.println("Evaluating the classifier");
		
		//Here's where we will run the evaluation with
		Evaluation evaluation = new Evaluation(testingData);
		evaluation.evaluateModel(neuralNetworkClassifier, testingData);
	
		//Print the results of the evaluation
		System.out.println(evaluation.toSummaryString());
	}

}
