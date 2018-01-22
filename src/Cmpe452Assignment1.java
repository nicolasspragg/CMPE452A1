
import java.util.*;
import java.io.*;
import java.math.*;


public class Cmpe452Assignment1 {



    public static void main(String[] args) throws  Exception{
     double learningRate = 0.6;
     int numEntities = 120;
     int trainDataSize = 40;
     int iterations = 0 ;
     double theta = 0;
        double lError; //Error for  predicted vs actual
        double error; // root mean squared error

        double sepalLength[] = new double[numEntities];
        double sepalWidth[] = new double[numEntities];
        double petalLength[] = new double[numEntities];
        double petalWidth[] = new double[numEntities];

        int output;
        int outputs[] = new int[120];
        readInAttr(sepalLength, sepalWidth, petalLength, petalWidth, outputs);

        double weights[] = new double[5];

        
        weights[0] = 0.4;
        weights[1] = 0.5;
        weights[2] = 0.7;
        weights[3] = 0.3;
        weights[4] = 0.2; // bias

        double rmsError;

    //train
       do {
           error = 0;
           iterations++;
           for (int i = 0; i < 120; i++) {
               output = findOutputValue(theta,weights,sepalLength[i],sepalWidth[i],petalLength[i],petalWidth[i]);
               lError = outputs[i] - output;
               weights[0] += learningRate * lError * sepalLength[i];
               weights[1] += learningRate * lError * sepalWidth[i];
               weights[2] += learningRate * lError * petalLength[i];
               weights[3] += learningRate * lError * petalWidth[i];
               weights[4] += learningRate * lError;
               error += (lError * lError);
               rmsError = Math.sqrt(error/numEntities);
               //System.out.println("Current weights" + weights[0] + weights[1] + weights[2] + weights [3] + weights[4]);
               System.out.println("Iteration: " +iterations);
               System.out.println("Error: " +error);

           }

       } while (error != 0);

       System.out.println("Ending weights" + weights[0] + weights[1] + weights[2] + weights [3] + weights[4]);
       System.out.println(iterations);


    }



    public static int findOutputValue(double theta,double[]weights, double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        double sum = sepalLength * weights[0] + sepalWidth * weights[1] + petalLength * weights[2] + petalWidth * weights[3];
        if(sum >= theta) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void readInAttr(double sepalLength[], double sepalWidth[], double petalLength[], double petalWidth[], int outputs[]) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("train.txt"));
        String line = null;
        int i = 0;
        while((line = br.readLine()) != null) {
            String[] values = line.split(",");

            sepalLength[i] = Double.parseDouble(values[0]);
            sepalWidth[i] = Double.parseDouble(values[1]);
            petalLength[i] = Double.parseDouble(values[2]);
            petalWidth[i] = Double.parseDouble(values[3]);
            // convert string class names to numerical data
            if(values[4].equals("Iris-setosa")) {
                outputs[i] = 0;
            } else if (values[4].equals("Iris-versicolor")) {
                outputs[i] = 1;

            } else {
                outputs[i] = 2;
            }
            i++;

        }

    }


}

