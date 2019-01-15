package classes;

import org.ejml.data.*;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.csc.factory.LinearSolverFactory_DSCC;
import static org.ejml.sparse.FillReducing.NONE;

public class SparseMatrix {
	private int numberOfAgents;
	private int sizeOfMatrix;
	public DMatrixSparseCSC matrixA;
	private DMatrixRMaj matrixB;


	public SparseMatrix(int n){
		numberOfAgents = n;
		sizeOfMatrix = ((numberOfAgents+1)*(numberOfAgents+2))/2;

		matrixA = new DMatrixSparseCSC(sizeOfMatrix,sizeOfMatrix);
		matrixB = new DMatrixRMaj(sizeOfMatrix,1);

		fulfillMatrix();
		setValuesOfB();
		//System.out.print("Sparse: A\n");
		//matrixA.print();
		//System.out.println("B:");
		//matrixB.print();

	}

	public double[] solveSparseMatrix(){
		DMatrixRMaj out = new DMatrixRMaj(sizeOfMatrix, 1);

		LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> solver = LinearSolverFactory_DSCC.lu(NONE);

		solver.setA(matrixA);
		solver.solve(matrixB, out);
		return out.getData();
	}

	public void fulfillMatrix() {
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
			matrixA.set(index1,index2,1.0);
		else if(y == 0 && n == numberOfAgents && index1 == index2)
			matrixA.set(index1,index2,1.0);
		else if(y == 0 && n == 0 && index1 == index2)
			matrixA.set(index1,index2,1.0);
		else if(y == currY && n == currN)
			matrixA.set(index1,index2,-1.0 + stays);
		else if(y == currY && n == currN - 1)
			matrixA.set(index1,index2, moreN);
		else if(y == currY - 1 && n == currN)
			matrixA.set(index1,index2, moreY);
		else if(y == currY + 1 && n == currN + 1)
			matrixA.set(index1,index2, moreU);
		else
			matrixA.set(index1,index2,0.0);
	}

	private void setValuesOfB() {
		for(int i=0;i<sizeOfMatrix;i++){
			matrixB.set(i,0,0.0);
		}
		matrixB.set(sizeOfMatrix-1,0,1.0);
	}

	public double countTime(){
		double startTime,endTime;
		double[] wynik = new double[sizeOfMatrix];
		DMatrixRMaj out = new DMatrixRMaj(sizeOfMatrix, 1);

		LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> solver = LinearSolverFactory_DSCC.lu(NONE);

		solver.setA(matrixA);
		startTime = System.nanoTime();
		solver.solve(matrixB,out);
		endTime = System.nanoTime();

		return endTime - startTime;
	}

}
