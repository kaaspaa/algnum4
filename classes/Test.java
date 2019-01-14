package classes;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Test {

    public MyMatrix<Double> countSlowResultsGauss(int numberOfAgents) {
        AgentMatrix am = new AgentMatrix(numberOfAgents);
        am.fulfillMatrix();

        MyMatrix<Double> results = new MyMatrix<Double>(Double.class,(numberOfAgents+1)*(numberOfAgents+2)/2,1);
        results = am.countResultVectorSlow();
        return results;
    }

    public MyMatrix<Double> countFastResultsGauss(int numberOfAgents) {
        AgentMatrix am = new AgentMatrix(numberOfAgents);
        am.fulfillMatrix();

        MyMatrix<Double> results = new MyMatrix<Double>(Double.class,(numberOfAgents+1)*(numberOfAgents+2)/2,1);
        results = am.countResultVectorFast();
        return results;
    }


    public MyMatrix<Double> countResultsGaussSeidel(int numberOfAgents){
        int iloscAgentow = numberOfAgents;
        int iloscProb = 200;

        GaussSeidel gs = new GaussSeidel(iloscAgentow,iloscProb);
        MyMatrix<Double> results = new MyMatrix<Double>(Double.class,(numberOfAgents+1)*(numberOfAgents+2)/2,1);
        results = gs.countGaussSeidelVector();

        return results;
    }

    public double countResultsSparseTime(int numberOfAgents){
        SparseMatrix sparseMatrix = new SparseMatrix(numberOfAgents);
        return sparseMatrix.countTime();
    }

    public double countSlowGaussTime(int numberOfAgents) {
        AgentMatrix ag = new AgentMatrix(numberOfAgents);
        return ag.countTimeSlow();
    }

    public double countFastGaussTime(int numberOfAgents) {
        AgentMatrix ag = new AgentMatrix(numberOfAgents);
        return ag.countTimeFast();
    }

    public double countGaussSeidelTime(int numberOfAgents) {
        int iloscProb = 200;
        GaussSeidel gaussSeidel = new GaussSeidel(numberOfAgents,iloscProb);
        return  gaussSeidel.countTime();
    }

	public void countAndWriteTimeOfExecition() throws IOException {
        int n=10;
	    double time1, time2, time3, time4, startTime1, endTime1, startTime2, endTime2, startTime3, endTime3,startTime4, endTime4;
	    FileWriter fileWriter = new FileWriter("Wyniki_czasow.csv");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println("Gauss;Gauss(upgraded);GaussSeidel;Sparse;n = " + n + ";");
    for (n=10;n<=20;n++) {
        //printWriter.println("Gauss;GaussSeidel;n = " + n + ";");

        //for (int q = 1; q <= 10; q++) {
            //Gauss wolny
            startTime1 = System.nanoTime();
            time1 = countSlowGaussTime(n);
            endTime1 = System.nanoTime();
            //Gauss szybki
            startTime2 = System.nanoTime();
            time2 = countFastGaussTime(n);
            endTime2 = System.nanoTime();
            //GaussSeidel
            startTime3 = System.nanoTime();
            time3 = countGaussSeidelTime(n);
            endTime3 = System.nanoTime();
            //Sparse
            startTime4 = System.nanoTime();
            time4 = countResultsSparseTime(n);
            endTime4 = System.nanoTime();

            printWriter.println("dzialanie" + (time1) + ";" + (time2) + ";" + (time3) + ";" + (time4) + ";" +
            "budowanie" + (endTime1 - startTime1 - time1) + ";" + (endTime2 - startTime2 - time2) + ";" + (endTime3 - startTime3 - time3) + ";" + (endTime4 - startTime4 - time4) + ";");
            //}
    }
            printWriter.close();
        }
}
