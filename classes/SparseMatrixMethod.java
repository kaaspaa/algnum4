package classes;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.SparseRealMatrix;

public class SparseMatrixMethod {
	private int sizeOfMatrix;
	private int numberOfAgents;
	private SparseRealMatrix matrixA;
	private SparseRealMatrix vectorB;
	private LUDecomposition solver;

	public SparseMatrixMethod(int numberOfAgents) {
		this.numberOfAgents = numberOfAgents;
		sizeOfMatrix = ((numberOfAgents+1)*(numberOfAgents+2))/2;

		createMatrixes();
		//matrixA.createMatrix(sizeOfMatrix,sizeOfMatrix);
		//vectorB.createMatrix(sizeOfMatrix,1);

		solver = new LUDecomposition(matrixA);
	}

	public void createMatrixes(){
		matrixA = new OpenMapRealMatrix(sizeOfMatrix,sizeOfMatrix);
		vectorB = new OpenMapRealMatrix(sizeOfMatrix,1);
		fulfillMatrix();
		fillVectorB();
	}

	private void fulfillMatrix() {
		int numberOfY=0,numberOfN=0;
		for(int pom=0;pom<sizeOfMatrix;pom++){
			setAgentMatrixValue(pom, numberOfY, numberOfN);
			numberOfN++;
			if(numberOfN > numberOfAgents - numberOfY){
				numberOfN=0;
				numberOfY++;
			}
		}
	}

	private void setAgentMatrixValue(int index1, int y, int n) {
		double moreY = ((double) y / (double) numberOfAgents) * ((Double.valueOf(numberOfAgents - y - n)) / ((double) numberOfAgents - 1.0)) + ((Double.valueOf(numberOfAgents - y - n)) / numberOfAgents) * ((double) y / (double) (numberOfAgents - 1));
		double moreN = ((double) n / (double) numberOfAgents) * ((Double.valueOf(numberOfAgents - y - n)) / ((double) numberOfAgents - 1.0)) + ((Double.valueOf(numberOfAgents - y - n)) / numberOfAgents) * ((double) n / (double) (numberOfAgents - 1));
		double moreU = ((double) y / (double) numberOfAgents) * ((Double.valueOf(n)) / ((double) numberOfAgents - 1.0)) + ((Double.valueOf(n)) / numberOfAgents) * ((double) y / (double) (numberOfAgents - 1));
		double stays = 1.0 - moreN - moreU - moreY;
		//System.out.println("dla P(" + y + "," + n + ")");
		//System.out.println("N - " + moreN + " Y - " + moreY + " U - " + moreU + " stays - " + stays);

		int index2 = 0;
		for (int i=0;i<=numberOfAgents;i++){
			for (int l = 0;l<=numberOfAgents-i;l++){
				conditionSet(index1, index2, y, n, i, l, moreY, moreN, moreU, stays);
				index2++;
			}
		}

	}

	private void conditionSet(int index1, int index2, int y, int n, int currY, int currN, double moreY, double moreN, double moreU, double stays){
		if(y == numberOfAgents && n == 0 && index2 == index1)
			matrixA.setEntry(index1,index2,1.0);
		else if(y == 0 && n == numberOfAgents && index1 == index2)
			matrixA.setEntry(index1,index2,1.0);
		else if(y == 0 && n == 0 && index1 == index2)
			matrixA.setEntry(index1,index2,1.0);
		else if(y == currY && n == currN)
			matrixA.setEntry(index1,index2,-1.0 + stays);
		else if(y == currY && n == currN - 1)
			matrixA.setEntry(index1,index2, moreN);
		else if(y == currY - 1 && n == currN)
			matrixA.setEntry(index1,index2, moreY);
		else if(y == currY + 1 && n == currN + 1)
			matrixA.setEntry(index1,index2, moreU);
		else
			matrixA.setEntry(index1,index2,0.0);
	}

	private void fillVectorB(){
		for(int i=0;i<vectorB.getRowDimension()-1;i++)
			vectorB.setEntry(i,0,0.0);
		vectorB.setEntry(sizeOfMatrix-1,0,1.0);
	}

	public double countSparseTime(){
		double startTime,endTime;
		startTime = System.nanoTime();
		solver.getSolver().solve(vectorB);
		endTime = System.nanoTime();

		return endTime - startTime;
	}

}
