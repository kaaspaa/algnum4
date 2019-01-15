package classes;

import state_class.State;

public class AgentMatrix {
	private int numberOfAgent;
	private int sizeOfMatrix;
	private MyMatrix<Double> agentMatrix;
	private MyMatrix<Double> vectorB;
	private MyMatrix<Double> resultVector;


	public AgentMatrix(int N){
		numberOfAgent = N;
		sizeOfMatrix = (numberOfAgent + 1)*(numberOfAgent + 2)/2;

		agentMatrix = new MyMatrix<Double>(Double.class,sizeOfMatrix);
		vectorB = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);
		resultVector = new MyMatrix<Double>(Double.class,sizeOfMatrix,1);
		//wypelnianie zerami
		//agentMatrix.fillWithZero();
		vectorB.fillWithZero();
		vectorB.setValue(sizeOfMatrix-1,0, 1.0);
		//secondB.fillWithZero();
		fulfillMatrix();
	}

	public MyMatrix<Double> getAgentMatrix(){ return agentMatrix; }
	public MyMatrix<Double> getMatrixB(){ return vectorB; }
	public void setAgentMatrix(MyMatrix<Double> agentMatrix) {this.agentMatrix = agentMatrix;}
	public void setVectorB(MyMatrix<Double> vectorB) {this.vectorB = vectorB;}
	public double getAgentMatrixValue(int row,int col){
		return agentMatrix.getValue(row,col);
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
		double moreY = ((double) y / (double) numberOfAgent) * ((Double.valueOf(numberOfAgent - y - n)) / ((double) numberOfAgent - 1.0)) + ((Double.valueOf(numberOfAgent - y - n)) / numberOfAgent) * ((double) y / (double) (numberOfAgent - 1));
		double moreN = ((double) n / (double) numberOfAgent) * ((Double.valueOf(numberOfAgent - y - n)) / ((double) numberOfAgent - 1.0)) + ((Double.valueOf(numberOfAgent - y - n)) / numberOfAgent) * ((double) n / (double) (numberOfAgent - 1));
		double moreU = ((double) y / (double) numberOfAgent) * ((Double.valueOf(n)) / ((double) numberOfAgent - 1.0)) + ((Double.valueOf(n)) / numberOfAgent) * ((double) y / (double) (numberOfAgent - 1));
		double stays = 1.0 - moreN - moreU - moreY;
		//System.out.println("dla P(" + y + "," + n + ")");
		//System.out.println("N - " + moreN + " Y - " + moreY + " U - " + moreU + " stays - " + stays);

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

	public MyMatrix<Double> countResultVectorSlow(){
		resultVector = agentMatrix.partialChoiseGauss(agentMatrix, vectorB);
		//System.out.println("Results Gauss:");
		//resultVector.printMatrix();

		return resultVector;
	}

	public MyMatrix<Double> countResultVectorFast(){
		resultVector = agentMatrix.upgradedPartialChoiseGauss(agentMatrix, vectorB);
		//System.out.println("Results Gauss:");
		//resultVector.printMatrix();

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

	public double countTimeSlow(){
		double startTime,endTime;
		startTime = System.nanoTime();
		resultVector = agentMatrix.partialChoiseGauss(agentMatrix,vectorB);
		endTime = System.nanoTime();

		//System.out.println("Gauss wolny:");
		//resultVector.printMatrix();
		return endTime - startTime;
	}

	public double countTimeFast(){
		double startTime,endTime;
		startTime = System.nanoTime();
		resultVector = agentMatrix.upgradedPartialChoiseGauss(agentMatrix,vectorB);
		endTime = System.nanoTime();

		//System.out.println("Gauss szybki");
		//resultVector.printMatrix();
		return endTime - startTime;
	}

}