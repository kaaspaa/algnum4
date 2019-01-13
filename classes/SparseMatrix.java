package classes;

import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparse;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.data.DMatrixSparseTriplet;
import org.ejml.interfaces.linsol.LinearSolverSparse;
import org.ejml.sparse.csc.factory.LinearSolverFactory_DSCC;
import org.ejml.sparse.csc.linsol.lu.LinearSolverLu_DSCC;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.ejml.sparse.FillReducing.NONE;

public class SparseMatrix {
	private int numberOfAgents;
	private int sizeOfMatrix;
	public SparseMatrix(double[][] D) c;
	//LinearSolverLu_DSCC;
	//private VectorXd; nie dzialaja.
	//SparseMatrix<double,ColMajor> A;
	public MyMatrix<Double> matrixA;
	private MyMatrix<Double> matrixB;
	private MyMatrix<Double> resultVector;


	public SparseMatrix(int n){
		numberOfAgents = n;
		sizeOfMatrix = ((numberOfAgents+1)*(numberOfAgents+2))/2;

		matrixA = new MyMatrix<Double>(Double.class,sizeOfMatrix);
		resultVector = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);
		matrixB = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);

	}

	public double[] solveSparseMatrix(){
		DMatrixRMaj out = new DMatrixRMaj(sizeOfMatrix, 1);

		LinearSolverSparse<DMatrixSparseCSC, DMatrixRMaj> solver = LinearSolverFactory_DSCC.lu(NONE);

		solver.setA(matrixA);
		solver.solve(resultVector, out);

		return out.getData();
	}

	public void fulfillMatrix() {
		AgentMatrix ag = new AgentMatrix(numberOfAgents);
		ag.fulfillMatrix();
		matrixA = ag.getAgentMatrix();
	}

	public void setValuesOfB() {
		for(int i=0;i<sizeOfMatrix;i++){
			matrixB[i] = 0.0;
		}
		matrixB[sizeOfMatrix-1] = 1.0;
	}

}
