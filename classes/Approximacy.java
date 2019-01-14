package classes;

public class Approximacy {
	private int degree;
	public MyMatrix<Double> matrixA;
	public MyMatrix<Double> matrixB;
	public MyMatrix<Double> resultMatrix;
	private int m;

	public Approximacy(int degree) {
		this.degree = degree;
		m = degree+1;
		matrixA = new MyMatrix<Double>(Double.class,m);
		matrixB = new MyMatrix<Double>(Double.class,m,1);
		resultMatrix = new MyMatrix<Double>(Double.class,m,1);

	}



}
