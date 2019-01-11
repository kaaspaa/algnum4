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

		for(int i=0;i<sizeOfMatrix;i++){
			for (int l=0;l<sizeOfMatrix;l++){
			}
		}

	}

	public void setAgentMatrixValue(int index1, int y, int n) {
		int u = numberOfAgents - y - n;
		double moreY = ((double) y / (double) numberOfAgents) * ((Double.valueOf(numberOfAgents - y - n)) / ((double) numberOfAgents - 1.0)) + ((Double.valueOf(numberOfAgents - y - n)) / numberOfAgents) * ((double) y / (double) (numberOfAgents - 1));
		double moreN = ((double) n / (double) numberOfAgents) * ((Double.valueOf(numberOfAgents - y - n)) / ((double) numberOfAgents - 1.0)) + ((Double.valueOf(numberOfAgents - y - n)) / numberOfAgents) * ((double) n / (double) (numberOfAgents - 1));
		double moreU = ((double) y / (double) numberOfAgents) * ((Double.valueOf(n)) / ((double) numberOfAgents - 1.0)) + ((Double.valueOf(n)) / numberOfAgents) * ((double) y / (double) (numberOfAgents - 1));
		double stays = 1.0 - moreN - moreU - moreY;
//		if (stays < 0.00000000000001)
//			stays = 0;

		int index2 = 0;
		for (int i=0;i<=numberOfAgents;i++){
			for (int l = 0;l<=numberOfAgents - i;l++){
				conditionSet(index1, index2, y, n, i, l, moreY, moreN, moreU, stays);
				index2++;
			}
		}

	}

	public void conditionSet(int index1, int index2, int y, int n, int currY, int currN, double moreY, double moreN, double moreU, double stays){
		if(y == numberOfAgents && n == 0 && index2 == index1)
			matrixA.setValue(index1,index2,1.0);
		else if(y == 0 && n == numberOfAgents && index1 == index2)
			matrixA.setValue(index1,index2,1.0);
		else if(y == 0 && n == 0 && index1 == index2)
			matrixA.setValue(index1,index2,1.0);
		else if(y == currY && n == currN)
			matrixA.setValue(index1,index2,-1.0 + stays);
		else if(y == currY && n == currN - 1)
			matrixA.setValue(index1,index2, moreN);
		else if(y == currY - 1 && n == currN)
			matrixA.setValue(index1,index2, moreY);
		else if(y == currY + 1 && n == currN + 1)
			matrixA.setValue(index1,index2, moreU);
		else
			matrixA.setValue(index1,index2,0.0);
	}

	public void setValuesOfB() {
		for(int i=0;i<sizeOfMatrix;i++){
			vectorB[i] = 0.0;
		}
		vectorB[sizeOfMatrix-1] = 1.0;
	}

	public MyMatrix<Double> countGaussSeidelVector(){
		fulfillMatrix();
		setValuesOfB();

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
		System.out.println("Wynik Gaussa - Seidela:");
		resultVector.printMatrix();
		return resultVector;
	}

	public MyMatrix<Double> countSecondB() {
		for(int i=0;i<sizeOfMatrix;i++){
			for(int l=0;l<sizeOfMatrix;l++){
				secondB.setValue(l,0,secondB.getValue(l,0) + matrixA.getValue(l,i) * resultVector.getValue(l,0));
			}
		}
		System.out.println("Drugie B:\n");
		secondB.printMatrix();
		return secondB;
	}

}
