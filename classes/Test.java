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
        int n=20;
	    double time1=0, time2=0, time3=0, time4=0, startTime1, endTime1, startTime2, endTime2, startTime3, endTime3,startTime4, endTime4;
	    FileWriter fileWriter = new FileWriter("Wyniki_czasow.csv");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println(";n;Gauss;Gauss(upgraded);GaussSeidel;Sparse; ;n;Gauss;Gauss(upgraded);GaussSeidel;Sparse;");
    for (n=10;n<30;n++) {

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
           // time4 = countResultsSparseTime(n);
            endTime4 = System.nanoTime();

            printWriter.println("dzialanie;" + n + ";" + (time1) + ";" + (time2) + ";" + (time3) + ";" + (time4) +
            ";budowanie;" + n + ";" + (endTime1 - startTime1 - time1) + ";" + (endTime2 - startTime2 - time2) + ";" + (endTime3 - startTime3 - time3) + ";" + (endTime4 - startTime4 - time4) + ";");

    }
        printWriter.close();
    }

    public void countAndWriteEverything() throws IOException {
        int endAgentNum = 60;
        int startAgentNum = 10;
        //rozmiary
        double[] arguments = new double[endAgentNum - startAgentNum];
        //czasy wykonywania
        double[] times1 = new double[endAgentNum - startAgentNum];//Gauss bez optymalizacji
        double[] times2 = new double[endAgentNum - startAgentNum];//Gauss z optymalizacja
        double[] times3 = new double[endAgentNum - startAgentNum];//Gauss-Seidel
        //czasy do budowania
        double startTime,endTime;
        double[] buildTimes1 = new double[endAgentNum - startAgentNum];//Gauss bez optymalizacji
        double[] buildTimes2 = new double[endAgentNum - startAgentNum];//Gausd z optymalizacja
        double[] buildTimes3 = new double[endAgentNum - startAgentNum];//Gauss-Seidel
        //do zapisywania
        FileWriter fileWriter1 = new FileWriter("Czasy.csv");
        PrintWriter printWriter1 = new PrintWriter(fileWriter1);
        FileWriter fileWriter2 = new FileWriter("Approx.csv");
        PrintWriter printWriter2 = new PrintWriter(fileWriter2);
        FileWriter fileWriter3 = new FileWriter("Porownanie.csv");
        PrintWriter printWriter3 = new PrintWriter(fileWriter3);

        for (int i = startAgentNum; i < endAgentNum; i++) {
            arguments[i - startAgentNum] = ((i + 1) * (i + 2)) / 2;

            startTime = System.nanoTime();
            times1[i - startAgentNum] = countSlowGaussTime(i);//czas dzialania
            endTime = System.nanoTime();

            buildTimes1[i - startAgentNum] = endTime - startTime - times1[i - startAgentNum];//czas budowania

            startTime = System.nanoTime();
            times2[i - startAgentNum] = countFastGaussTime(i);//czas dzialania
            endTime = System.nanoTime();

            buildTimes2[i - startAgentNum] = endTime - startTime - times2[i - startAgentNum];//czas budowania

            startTime = System.nanoTime();
            times3[i - startAgentNum] = countGaussSeidelTime(i);//czas dzialania
            endTime = System.nanoTime();

            buildTimes3[i - startAgentNum] = endTime - startTime - times3[i - startAgentNum];//czas budowania
        }
        //czasy:
        printWriter1.println(";n;Gauss;Gauss(upgraded);GaussSeidel;Sparse; ;n;Gauss;Gauss(upgraded);GaussSeidel;Sparse;");
        for(int i=0; i<times1.length; i++){
            printWriter1.println("dzialanie;" + (i + startAgentNum) + ";" + times1[i] + ";" + times2[i] + ";" + times3[i] + ";" +
            ";budowanie;" + (i + startAgentNum) + ";" + buildTimes1[i] + ";" + buildTimes2[i] + ";" + buildTimes3[i]);

        }


        //wielomiany dzialanie:
        Approximacy approximacy1 = new Approximacy(3, arguments, times1);
        Approximacy approximacy2 = new Approximacy(2, arguments, times2);
        Approximacy approximacy3 = new Approximacy(2, arguments, times3);
        MyMatrix<Double> results1;
        MyMatrix<Double> results2;
        MyMatrix<Double> results3;
        results1 = approximacy1.countResults();
        results2 = approximacy2.countResults();
        results3 = approximacy3.countResults();

        printWriter2.println("Dzialanie:");
        printWriter2.print("Gauss(bez optymalizacji);x^0;x^1;x^2;x^3;");
        printWriter2.print("Gauss(zoptymalizowany);x^0;x^1;x^2;");
        printWriter2.print("Gauss-Seidel;x^0;x^1;x^2;\n;");
        for(int i=0;i<results1.rows;i++){
            printWriter2.print(results1.getValue(i,0) + ";");
        }
        printWriter2.print(";");
        for (int i = 0; i < results2.rows; i++) {
            printWriter2.print(results2.getValue(i, 0) + ";");
        }
        printWriter2.print(";");
        for(int i=0;i<results3.rows;i++){
            printWriter2.print(results3.getValue(i,0) + ";");
        }

        //Wielomiany budowanie
        Approximacy approximacy4 = new Approximacy(3, arguments, buildTimes1);
        Approximacy approximacy5 = new Approximacy(2, arguments, buildTimes2);
        Approximacy approximacy6 = new Approximacy(2, arguments, buildTimes3);
        results1 = approximacy4.countResults();
        results2 = approximacy5.countResults();
        results3 = approximacy6.countResults();
        printWriter2.println("\nBudowanie:");
        printWriter2.print("Gauss(bez optymalizacji);x^0;x^1;x^2;x^3;");
        printWriter2.print("Gauss(zoptymalizowany);x^0;x^1;x^2;");
        printWriter2.print("Gauss-Seidel;x^0;x^1;x^2;\n;");
        for(int i=0;i<results1.rows;i++){
            printWriter2.print(results1.getValue(i,0) + ";");
        }
        printWriter2.print(";");
        for (int i = 0; i < results2.rows; i++) {
            printWriter2.print(results2.getValue(i, 0) + ";");
        }
        printWriter2.print(";");
        for(int i=0;i<results3.rows;i++){
            printWriter2.print(results3.getValue(i,0) + ";");
        }

        printWriter2.println("\nA teraz dzialanie + budowanie");

        //wielomiany dzialanie + budowanie
        double[] timesConn1 = new double[endAgentNum - startAgentNum];
        double[] timesConn2 = new double[endAgentNum - startAgentNum];
        double[] timesConn3 = new double[endAgentNum - startAgentNum];
        for(int i=0;i<times1.length;i++){
           timesConn1[i] = times1[i] + buildTimes1[i];
           timesConn2[i] = times2[i] + buildTimes2[i];
           timesConn3[i] = times3[i] + buildTimes3[i];
        }
        Approximacy approximacy7 = new Approximacy(3, arguments, timesConn1);
        Approximacy approximacy8 = new Approximacy(2, arguments, timesConn2);
        Approximacy approximacy9 = new Approximacy(2, arguments, timesConn3);
        results1 = approximacy7.countResults();
        results2 = approximacy8.countResults();
        results3 = approximacy9.countResults();
        printWriter2.print("Gauss(bez optymalizacji);x^0;x^1;x^2;x^3;");
        printWriter2.print("Gauss(zoptymalizowany);x^0;x^1;x^2;");
        printWriter2.print("Gauss-Seidel;x^0;x^1;x^2;\n;");
        for(int i=0;i<results1.rows;i++){
            printWriter2.print(results1.getValue(i,0) + ";");
        }
        printWriter2.print(";");
        for (int i = 0; i < results2.rows; i++) {
            printWriter2.print(results2.getValue(i, 0) + ";");
        }
        printWriter2.print(";");
        for(int i=0;i<results3.rows;i++){
            printWriter2.print(results3.getValue(i,0) + ";");
        }


        //porownanie
        printWriter3.print("wyniki wychodzace;;;;;wyniki obliczone;\n");
        printWriter3.print("liczba agentow;Gauss(bez optymalizacji);Gauss(zoptymalizowany);Gauss-Seidel;" +
                ";liczba agentow;Gauss(bez optymalizacji);Gauss(zoptymalizowany);Gauss-Seidel;;Blad bezwzgledny Gauss;" +
                " Blad bezwzgledny Gauss(zoptymalizowany); Blad bezwzgledny Gauss-Seidel\n");
        for(int i=0;i<times1.length;i++){
            printWriter3.print((i+ startAgentNum) + ";" + timesConn1[i] + ";" + timesConn2[i] + ";" + timesConn3[i] + ";;"
                    +(i+ startAgentNum) + ";" + approximacy7.solveEquation(i+ startAgentNum) + ";" + approximacy8.solveEquation(i + startAgentNum) + ";" + approximacy9.solveEquation(i + startAgentNum) + ";;"
                    + Math.abs(timesConn1[i] -  approximacy7.solveEquation(i+ startAgentNum)) + ";" + Math.abs(timesConn2[i] -  approximacy8.solveEquation(i+ startAgentNum)) + ";" + Math.abs(timesConn3[i] -  approximacy9.solveEquation(i+ startAgentNum)) + "\n");
        }
        printWriter3.print("\nCzas dla 100.000 z budowaniem\n Gauss(bez optymalizacji);" + approximacy7.solveEquation(450) + ";Gauss(z optymalizacja);" + approximacy8.solveEquation(450) + ";Gauss-Seidel;" + approximacy9.solveEquation(450) + "\n");
        printWriter1.close();
        printWriter2.close();
        printWriter3.close();
    }

}
