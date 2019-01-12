package classes;

import org.ejml.data.DMatrixSparse;
import org.ejml.data.DMatrixSparseTriplet;

import java.util.ArrayList;
import java.util.LinkedList;

public class SparseMatrix {
	private int numberOfAgents;
	private int sizeOfMatrix;
	//private VectorXd; nie dzialaja.
	//SparseMatrix<double,ColMajor> A;
	private static final ArrayList<LinkedList<Double>> matrixA = new ArrayList<>();
	private static final ArrayList<LinkedList<Double>> vectorB = new ArrayList<>();
	private static final ArrayList<LinkedList<Double>> resultVector = new ArrayList<>();

	public SparseMatrix(int n){
		numberOfAgents = n;
		sizeOfMatrix = ((numberOfAgents+1)*(numberOfAgents+2))/2;
	}

}
