package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Test {

    public MyMatrix<Double> countResultsGauss(int numberOfAgents) {
                int iloscAgentow = numberOfAgents;

                AgentMatrix am = new AgentMatrix(iloscAgentow);
                am.fulfillMatrix();

                MyMatrix<Double> results = new MyMatrix<Double>(Double.class,(numberOfAgents+1)*(numberOfAgents+2)/2,1);
                am.countResultVector();
                results = am.countSecondBGauss();
                return results;
        }

        public MyMatrix<Double> countResultsGaussSeidel(int numberOfAgents){
                int iloscAgentow = numberOfAgents;
                int iloscProb = 200;

                GaussSeidel gs = new GaussSeidel(iloscAgentow,iloscProb);
                MyMatrix<Double> results = new MyMatrix<Double>(Double.class,(numberOfAgents+1)*(numberOfAgents+2)/2,1);
                gs.countGaussSeidelVector();
                results = gs.countSecondB();
                return results;
        }

	public void countAndWriteTimeOfExecition() throws IOException {
        int n=25;
	    double startTime1, endTime1, startTime2, endTime2, startTime3, endTime3;
	    FileWriter fileWriter = new FileWriter("Wyniki_czasow.txt");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println("Gauss;GaussSeidel;n = " + n + ";");
    //for (int n=3;n<=30;n++) {
        //printWriter.println("Gauss;GaussSeidel;n = " + n + ";");

        //for (int q = 1; q <= 10; q++) {
            //Gauss
            startTime1 = System.nanoTime();
            countResultsGauss(n);
            endTime1 = System.nanoTime();
            //GaussSeidel
            startTime2 = System.nanoTime();
            countResultsGaussSeidel(n);
            endTime2 = System.nanoTime();
            printWriter.println((endTime1 - startTime1) + ";" + (endTime2 - startTime2) + ";");
            //}
    //}
                printWriter.close();
        }

}
