package classes;

import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;

public class Approximacy {
	private MyMatrix<Double> matrixA;
	private MyMatrix<Double> matrixB;
	private MyMatrix<Double> resultMatrix;
	private double[] s;
	private double[] t;
	private int m;

	public Approximacy(int m,double[] arguments, double times[]) {
		this.m = m;
		s = new double[2*m+1];
		t = new double[m+1];
		//numberOfAgents = n;
		matrixA = new MyMatrix<Double>(Double.class,m+1);
		matrixB = new MyMatrix<Double>(Double.class,m+1,1);
		resultMatrix = new MyMatrix<Double>(Double.class,m+1,1);

		resultMatrix.fillWithZero();
		getMatrix(arguments);
		getVector(arguments,times);
	}

	private void getMatrix(double[] arguments) {
		for(int k=0;k<m*2+1;k++){
			s[k] = 0.0;
			for (int i=0;i<arguments.length;i++){
				s[k] += Math.pow(arguments[i],k);
			}
		}
		for(int i=0;i<m+1;i++){
			for(int l=0;l<m+1;l++){
				matrixA.setValue(i,l,s[i+l]);
			}
		}
	}

	private void getVector(double[] arguments, double[] values) {
		for(int k=0;k<m+1;k++){
			t[k]=0.0;
			for(int i=0;i<arguments.length;i++)
				t[k] += values[i] * Math.pow(arguments[i],k);
		}
		for(int i=0;i<m+1;i++){
			matrixB.setValue(i,0,t[i]);
		}
	}

	public MyMatrix<Double> countResults() {
		System.out.println("A:");
		matrixA.printMatrix();
		System.out.println("B:");
		matrixB.printMatrix();
		System.out.println();

		resultMatrix = resultMatrix.upgradedPartialChoiseGauss(matrixA, matrixB);
		resultMatrix.printMatrix();
		return resultMatrix;
	}

	public double solveEquation(double x){
		double pom = ((x+1)*(x+2))/2;
		double result = 0.0;
		for(int i=0;i<m+1;i++){
			result += resultMatrix.getValue(i,0) * Math.pow(pom,i);
		}

		return result;
	}

	public double probaSolve(double x){
		double result = 0.0;
		for(int i=0;i<m+1;i++){
			result += resultMatrix.getValue(i,0) * Math.pow(x,m-i);
		}
		return  result;
	}

}
