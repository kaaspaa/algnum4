package classes;

import state_class.State;

public class AgentMatrix {
	private int numberOfAgent;
	private int sizeOfMatrix;
	private MyMatrix<Double> agentMatrix;
	private MyMatrix<Double> vectorB;
	private MyMatrix<Double> secondB;
	private MyMatrix<Double> resultVector;
	private State[] s;


	public AgentMatrix(int N){
		numberOfAgent = N;
		sizeOfMatrix = (numberOfAgent + 1)*(numberOfAgent + 2)/2;

		agentMatrix = new MyMatrix<Double>(Double.class,sizeOfMatrix);
		vectorB = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);
		secondB = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);
		resultVector = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);
		//wypelnianie zerami
		agentMatrix.fillWithZero();
		vectorB.fillWithZero();
		vectorB.setValue(sizeOfMatrix-1,0, 1.0);
		secondB.fillWithZero();

	}

	public void showMeTheMatrix(){
		for (int i=0;i<sizeOfMatrix;i++) {
			for (int l = 0; l < sizeOfMatrix; l++) {
				System.out.print(agentMatrix.getValue(i,l) + ", ");
			}
			System.out.println();
		}
	}

	public void fulfillMatrix() {
		int numberOfY=0,numberOfN=0;
		for(int pom=0;pom<sizeOfMatrix;pom++){
				setAgentMatrixValue(pom, numberOfY, numberOfN);
			numberOfN++;
			if(numberOfN > numberOfAgent - numberOfY){
				numberOfN=0;
				numberOfY++;
			}
		}
	}

	public void setAgentMatrixValue(int index1, int y, int n) {
		int u = numberOfAgent - y - n;
		double moreY = ((double) y / (double) numberOfAgent) * ((Double.valueOf(numberOfAgent - y - n)) / ((double) numberOfAgent - 1.0)) + ((Double.valueOf(numberOfAgent - y - n)) / numberOfAgent) * ((double) y / (double) (numberOfAgent - 1));
		double moreN = ((double) n / (double) numberOfAgent) * ((Double.valueOf(numberOfAgent - y - n)) / ((double) numberOfAgent - 1.0)) + ((Double.valueOf(numberOfAgent - y - n)) / numberOfAgent) * ((double) n / (double) (numberOfAgent - 1));
		double moreU = ((double) y / (double) numberOfAgent) * ((Double.valueOf(n)) / ((double) numberOfAgent - 1.0)) + ((Double.valueOf(n)) / numberOfAgent) * ((double) y / (double) (numberOfAgent - 1));
		double stays = 1.0 - moreN - moreU - moreY;
//		if (stays < 0.00000000000001)
//			stays = 0;
		System.out.println("dla P(" + y + "," + n + ")");
		System.out.println("N - " + moreN + " Y - " + moreY + " U - " + moreU + " stays - " + stays);

		int index2 = 0;
		for (int i=0;i<=numberOfAgent;i++){
			for (int l = 0;l<=numberOfAgent-i;l++){
				conditionSet(index1, index2, y, n, i, l, moreY, moreN, moreU, stays);
				index2++;
			}
		}

	}

	public void conditionSet(int index1, int index2, int y, int n, int currY, int currN, double moreY, double moreN, double moreU, double stays){
		if(y == numberOfAgent && n == 0 && index2 == index1)
			agentMatrix.setValue(index1,index2,1.0);
		else if(y == 0 && n == numberOfAgent && index1 == index2)
			agentMatrix.setValue(index1,index2,1.0);
		else if(y == 0 && n == 0 && index1 == index2)
			agentMatrix.setValue(index1,index2,1.0);
		else if(y == currY && n == currN)
			agentMatrix.setValue(index1,index2,-1.0 + stays);
		else if(y == currY && n == currN - 1)
			agentMatrix.setValue(index1,index2, moreN);
		else if(y == currY - 1 && n == currN)
			agentMatrix.setValue(index1,index2, moreY);
		else if(y == currY + 1 && n == currN + 1)
			agentMatrix.setValue(index1,index2, moreU);
		else
			agentMatrix.setValue(index1,index2,0.0);
	}

	public MyMatrix<Double> countResultVector(){
		resultVector = agentMatrix.partialChoiseGauss(agentMatrix, vectorB);

		System.out.println("Results Gauss:");
		resultVector.printMatrix();

		return resultVector;
	}

	public double getAvgValue(MyMatrix<Double> matrix){
		double avgValue=0.0;
		for(int i=0;i<matrix.columns;i++){
			for(int l=0;l<matrix.rows;l++){
				avgValue = avgValue + matrix.getValue(l,i);
			}
		}
		return avgValue;
	}


	public MyMatrix<Double> countSecondBGauss(){
		vectorB.fillWithZero();
		vectorB.setValue(sizeOfMatrix-1,0, 1.0);
		fulfillMatrix();

		for(int i=0;i<sizeOfMatrix;i++){
			for(int l=0;l<sizeOfMatrix;l++){
				secondB.setValue(l,0,secondB.getValue(l,0) + agentMatrix.getValue(l,i) * resultVector.getValue(l,0));
			}
		}
		System.out.println("Drugie B:\n");
		secondB.printMatrix();
		return secondB;
	}

}