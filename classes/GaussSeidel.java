package classes;

public class GaussSeidel {
	private MyMatrix<Double> matrixA;
	private MyMatrix<Double> resultVector;
	private MyMatrix<Double> secondB;
	private int numberOfAgents;
	private int sizeOfMatrix;
	private int iterCoaunt;
	private double prev[];
	private double vectorB[];

	public GaussSeidel(int iloscAgentow, int iloscProb){
		numberOfAgents = iloscAgentow;
		iterCoaunt = iloscProb;
		sizeOfMatrix = ((numberOfAgents+1)*(numberOfAgents+2))/2;

		matrixA = new MyMatrix<Double>(Double.class,sizeOfMatrix);
		resultVector = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);
		secondB = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);

		secondB.fillWithZero();

		prev = new double[sizeOfMatrix];
		vectorB = new double[sizeOfMatrix];

		fulfillMatrix();
		setValuesOfB();
	}

	public void fulfillMatrix() {
		AgentMatrix ag = new AgentMatrix(numberOfAgents);
		ag.fulfillMatrix();
		matrixA = ag.getAgentMatrix();
	}

	public void setValuesOfB() {
		for(int i=0;i<sizeOfMatrix;i++){
			vectorB[i] = 0.0;
		}
		vectorB[sizeOfMatrix-1] = 1.0;
	}

	public MyMatrix<Double> countGaussSeidelVector(){

		for(int i=0;i<sizeOfMatrix;i++) {
			resultVector.setValue(i, 0, 0.0);
			prev[i] = 0.0;
		}

		for(int q=0;q<iterCoaunt;q++){
			for(int i=0;i<sizeOfMatrix;i++){
				resultVector.setValue(i,0,vectorB[i]);
				for(int l=0;l<i;l++){
					resultVector.setValue(i,0,resultVector.getValue(i,0) - (matrixA.getValue(i,l) * prev[l]));//D^-1*L*x
				}
				for(int l=i+1;l<sizeOfMatrix;l++){
					resultVector.setValue(i,0, resultVector.getValue(i,0) - (matrixA.getValue(i,l) * prev[l]));//D^-1*U*x
				}
				resultVector.setValue(i,0,resultVector.getValue(i,0)/matrixA.getValue(i,i));
				for(int l=0;l<sizeOfMatrix;l++)
					prev[l] = resultVector.getValue(l,0);
			}
		}
		//System.out.println("Wynik Gaussa - Seidela:");
		//resultVector.printMatrix();
		return resultVector;
	}

	public double countTime(){
		double startTime, endTime;
		startTime = System.nanoTime();
		countGaussSeidelVector();
		endTime = System.nanoTime();

		return endTime - startTime;
	}

}
