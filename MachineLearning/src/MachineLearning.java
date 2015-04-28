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
		// TODO Auto-generated method stub
		
		//The numeric attributes in centimeters
		Attribute sepalLength = new Attribute("sepalLength");
		Attribute sepalWidth  = new Attribute("sepalWidth");
		Attribute petalLength = new Attribute("petalLength");
		Attribute petalWidth  = new Attribute("petalWidth");
		
		//The vector by which to hold the nominal values
		FastVector typeOfIris = new FastVector(3);
		typeOfIris.addElement("Iris-setosa");
		typeOfIris.addElement("Iris-versicolor");
		typeOfIris.addElement("Iris-virginica");
		
		//The nominal attribute for iris type
		Attribute irisType = new Attribute("irisType", typeOfIris);
		
		//Now collect all the attributes
		FastVector attributesFastVector = new FastVector(5);
		attributesFastVector.addElement(sepalLength);
		attributesFastVector.addElement(sepalWidth);
		attributesFastVector.addElement(petalLength);
		attributesFastVector.addElement(petalWidth);
		attributesFastVector.addElement(irisType);
		
		//TODO: Figure out why I can't add the attributes above to the data below
		
		//Read the dataset from the csv file
		System.out.println("Opening the data file.");
		DataSource source = new DataSource("IrisDataSet.csv");
		Instances  data = source.getDataSet();
		data.setClassIndex(4); //The name is the class index
		System.out.println("Data file read.");	
		
		
		//Randomize and divide into training and test sets
		data.randomize(new Random());
		Instances trainingData = new Instances(data, 0, 99);
		Instances testingData  = new Instances(data, 99, 50);
		
		HardcodeClassifier hardcodeClassifier = new HardcodeClassifier();
		hardcodeClassifier.buildClassifier(trainingData);
		
		//Here's where we will run the evaluation
		Evaluation evaluation = new Evaluation(testingData);
		evaluation.evaluateModel(hardcodeClassifier, testingData);
	
		//Print the results of the evaluation
		System.out.println(evaluation.toSummaryString());
	}

}
