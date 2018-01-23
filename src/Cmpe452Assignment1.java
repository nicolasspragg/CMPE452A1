
import java.util.*;
import java.io.*;
import java.math.*;


public class Cmpe452Assignment1 {

    public static void main(String[] args) throws  Exception{
     double learningRate = 0.2;
     int numEntities = 120;
     int trainDataSize = 40;
     int iterations = 0 ;
     double theta = 0;
        int maxIter = 4000000;
        double lError; //Error for  predicted vs actual
        double error; // root mean squared error

        double sepalLength[] = new double[numEntities];
        double sepalWidth[] = new double[numEntities];
        double petalLength[] = new double[numEntities];
        double petalWidth[] = new double[numEntities];

        int output;
        int outputs[] = new int[120];

        int settosaVSAll[] = new int[120];
        fillsettosaVSAll(settosaVSAll);


        int versicolorVSAll[] = new int[120];
        fillversicolorVSAll(versicolorVSAll);


        int virginicaVSAll[] = new int[120];
        fillvirginicaVSAll(virginicaVSAll);


        readInAttr(sepalLength, sepalWidth, petalLength, petalWidth, outputs);
        double weights[] = new double[5];


        weights[0] = 0.4;
        weights[1] = 0.5;
        weights[2] = 0.7;
        weights[3] = 0.3;
        weights[4] = 0.2; // bias

        double rmsError;

        /*
       do {
           iterations++;
           error = 0;
           for (int i = 0; i < numEntities; i++) {
               output = findOutputValue(theta,weights,sepalLength[i],sepalWidth[i],petalLength[i],petalWidth[i]);
               lError = outputs[i] - output;
               weights[0] += learningRate * lError * sepalLength[i];
               weights[1] += learningRate * lError * sepalWidth[i];
               weights[2] += learningRate * lError * petalLength[i];
               weights[3] += learningRate * lError * petalWidth[i];
               weights[4] += learningRate * lError;
               error += (lError * lError);
               rmsError = Math.sqrt(error/numEntities);
           }

       } while (error != 0);

*/



        //train classifier settosaVSAll

        int settosaError;
        int settosaErrorLocal;
        int settosaOutput;
        do {
            settosaError = 0;
            for (int i = 0; i < numEntities; i++) {
                settosaOutput = findOutputValue(theta,weights,sepalLength[i],sepalWidth[i],petalLength[i],petalWidth[i]);
                settosaErrorLocal = settosaVSAll[i] - settosaOutput;
                weights[0] += learningRate * settosaErrorLocal * sepalLength[i];
                weights[1] += learningRate * settosaErrorLocal * sepalWidth[i];
                weights[2] += learningRate * settosaErrorLocal * petalLength[i];
                weights[3] += learningRate * settosaErrorLocal * petalWidth[i];
                weights[4] += learningRate * settosaErrorLocal;
                settosaError += (settosaErrorLocal*settosaErrorLocal);
            }
        } while (settosaError != 0);
        System.out.println("Classified settosa vs all");


        //train classifier veriscolorVSall
        int versicolorError;
        int versicolorErrorLocal;
        int versicolorOutput;

       double correctCount = 0;
       double wrongCount = 0;

        int count = 0;
        do {
            count++;
            versicolorError = 0;
            for (int i = 0; i < numEntities; i++) {
                versicolorOutput = findOutputValue(theta,weights,sepalLength[i],sepalWidth[i],petalLength[i],petalWidth[i]);
                versicolorErrorLocal = versicolorVSAll[i] - versicolorOutput;
                if(versicolorErrorLocal == 0) {
                    correctCount++;

                } else {
                    wrongCount++;
                }
                weights[0] += learningRate * versicolorErrorLocal * sepalLength[i];
                weights[1] += learningRate * versicolorErrorLocal * sepalWidth[i];
                weights[2] += learningRate * versicolorErrorLocal * petalLength[i];
                weights[3] += learningRate * versicolorErrorLocal * petalWidth[i];
                weights[4] += learningRate * versicolorErrorLocal;
                versicolorError += (versicolorErrorLocal*versicolorErrorLocal);
            }

        } while (versicolorError != 0 && count <= maxIter );
        System.out.println("Classified versicolor vs all");

        System.out.println(correctCount);
        System.out.println(wrongCount);

        double acc = wrongCount / correctCount;
        System.out.println("Accuracy " +acc +"%");


    }


    public static int findOutputValue(double theta,double[]weights, double sepalLength, double sepalWidth, double petalLength, double petalWidth) {
        double sum = sepalLength * weights[0] + sepalWidth * weights[1] + petalLength * weights[2] + petalWidth * weights[3];
        if(sum > theta) {
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
            i++;

        }

    }

    public static void fillsettosaVSAll(int settosaVSAll[]) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("train.txt"));
        String line = null;
        int i = 0;
        while((line = br.readLine()) != null) {
            String[] values = line.split(",");
           if(values[4].equals("Iris-setosa")) {
                settosaVSAll[i] = 1;
           } else {
               settosaVSAll[i] = 0;
           }
            i++;
        }

    }

    public static void fillversicolorVSAll(int versicolorVSAll[]) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("train.txt"));
        String line = null;
        int i = 0;
        while((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if(values[4].equals("Iris-versicolor")) {
                versicolorVSAll[i] = 1;
            } else {
                versicolorVSAll[i] = 0;
            }
            i++;
        }

    }

    public static void fillvirginicaVSAll(int virginicaVSAll[]) throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("train.txt"));
        String line = null;
        int i = 0;
        while((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if(values[4].equals("Iris-virginica")) {
                virginicaVSAll[i] = 1;
            } else {
                virginicaVSAll[i] = 0;
            }
            i++;
        }

    }




}

