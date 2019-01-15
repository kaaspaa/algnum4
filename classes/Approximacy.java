package classes;

public class Approximacy {
	private int degree;
	int numberOfAgents;
	public MyMatrix<Double> matrixA;
	public MyMatrix<Double> matrixB;
	public MyMatrix<Double> resultMatrix;
	private int m;

	public Approximacy(int degree,int n) {
		this.degree = degree;
		m = degree+1;
		numberOfAgents = n;
		matrixA = new MyMatrix<Double>(Double.class,m);
		matrixB = new MyMatrix<Double>(Double.class,m,1);
		resultMatrix = new MyMatrix<Double>(Double.class,m,1);

	}

	private double countS(int k) {
		return Math.pow(numberOfAgents,k);
	}

	private double countT(int k,int casee){
		double result;
		Test test = new Test();

		switch (casee) {
			case 1:
				{result = test.countSlowGaussTime(numberOfAgents) * countS(k);}
			case 2:
				{result = test.countFastGaussTime(numberOfAgents) * countS(k);}
			case 3:
				{result = test.countGaussSeidelTime(numberOfAgents) * countS(k);}
			case 4:
				{result = test.countResultsSparseTime(numberOfAgents) * countS(k);
				break;}
				default: {
				result = 0.0;
			}
		}

		return result;
	}

	public MyMatrix<Double> approx(int casee) {
		//AgentMatrix agentMatrix = new AgentMatrix(numberOfAgents);
		matrixB.fillWithZero();
		matrixA.fillWithZero();
		resultMatrix.fillWithZero();
		for(int n=0;n<10;n++) {
			for (int i = 0; i < m; i++) {
				matrixB.setValue(i,0,matrixB.getValue(i,0) + countT(i,casee));
				for(int l = 0; l < m; l++){
					matrixA.setValue(i,l,matrixA.getValue(i,l) + countS(l + i));
				}
			}
		}
		System.out.println("A:");
		matrixA.printMatrix();
		System.out.println("B:");
		matrixB.printMatrix();

		MyMatrix<Double> pom = new MyMatrix<Double>(Double.class,m,1);
		pom = resultMatrix.upgradedPartialChoiseGauss(matrixA,matrixB);
		System.out.println("case - " + casee);
		pom.printMatrix();
		return pom;
	}

}
