package classes;

//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
//import java.util.Random;

public class MyMatrix<T extends Number> {
    T[][] matrix;
    int rows;
    int columns;
    private Class<T> classType;

    public MyMatrix(Class<T> classType, int length) {
        this.rows = length;
        this.columns = length;
        this.matrix = (T[][]) new Number[rows][columns];
        this.classType = classType;
    }

    public MyMatrix(Class<T> classType, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = (T[][]) new Number[rows][columns];
        this.classType = classType;
    }

    public int GetRowsNumber(){
        return  rows;
    }

    public int GetcolumnsNumber(){
        return columns;
    }

    public T getValue(int rowNo, int columnNo){
        try{
            if(columnNo<0 || rowNo<0)
                throw new NullPointerException();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return matrix [rowNo][columnNo];
    }

    public void setValue(int rowNo, int columnNo, T value)
    {
        matrix [rowNo][columnNo] = value;
    }

    public MyMatrix<T> gaussBase(MyMatrix<T> matrix, MyMatrix<T> vector) {

        int n = vector.rows;
        for (int p = 0; p < n; p++) {
            for (int i = p + 1; i < n; i++) {
                CleanMatrix(matrix, vector, n, p, i);
            }
        }

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);
        return resultVector;
    }

    //search biggest value in column( search only in sub-matrix of course) column- can be also understood as iteration
    public MyMatrix<T> partialChoiseGauss(MyMatrix<T> matrix, MyMatrix<T> vector) {

        int n = vector.rows;
        for (int p = 0; p < n; p++) {

            int max = p;
            FlipBiggestRow(matrix, vector, n, p, max);
            for (int i = p + 1; i < n; i++) {
                if(matrix.matrix[i][p].doubleValue() != 0.0)
                CleanMatrix(matrix, vector, n, p, i);
            }
        }

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);
        return resultVector;
    }

    public MyMatrix<T> fullChoiseGauss(MyMatrix<T> matrix, MyMatrix<T> vector, ArrayList<Integer> queue) {

        for (int i = 0; i < matrix.rows; i++) {
            queue.add(i);
        }
        int n = vector.rows;
        for (int p = 0; p < n; p++) {

            findAndSetBiggestValueInMatrix(matrix, vector, p, queue);
            for (int i = p + 1; i < n; i++) {
                CleanMatrix(matrix, vector, n, p, i);
            }
        }

        MyMatrix<T> resultVector = new MyMatrix(classType, vector.rows, 1);
        CountBackwardResult(matrix, vector, n, resultVector);

        this.sortResultsByQueue(resultVector,queue);
        return resultVector;
    }

    private void FlipBiggestRow(MyMatrix<T> matrix, MyMatrix<T> vector, int n, int p, int max) {
        for (int i = p + 1; i < n; i++) {
            if (classType.equals(Float.class)) {
                if (Math.abs(matrix.matrix[i][p].floatValue()) > Math.abs(matrix.matrix[max][p].floatValue())) {
                    max = i;
                }
            } else {
                if (classType.equals(Double.class)) {
                    if (Math.abs(matrix.matrix[i][p].doubleValue()) > Math.abs(matrix.matrix[max][p].doubleValue())) {
                        max = i;
                    }

                }
            }
        }

        flipRows(matrix, p, max);
        flipRows(vector, p, max);
    }

    private void CleanMatrix(MyMatrix<T> matrix, MyMatrix<T> vector, int n, int p, int i) {
        if (classType.equals(Float.class)) {
            float alpha = matrix.matrix[i][p].floatValue() / matrix.matrix[p][p].floatValue();
            vector.matrix[i][0] = (T) (Float) (vector.matrix[i][0].floatValue() - alpha * vector.matrix[p][0].floatValue());
            for (int j = p; j < n; j++) {
                matrix.matrix[i][j] = (T) (Float) (matrix.matrix[i][j].floatValue() - alpha * matrix.matrix[p][j].floatValue());
            }
        } else {
            if (classType.equals(Double.class)) {
                double alpha = matrix.matrix[i][p].doubleValue() / matrix.matrix[p][p].doubleValue();
                vector.matrix[i][0] = (T) (Double) (vector.matrix[i][0].doubleValue() - alpha * vector.matrix[p][0].doubleValue());
                for (int j = p; j < n; j++) {
                    matrix.matrix[i][j] = (T) (Double) (matrix.matrix[i][j].doubleValue() - alpha * matrix.matrix[p][j].doubleValue());
                }
            }
        }
    }

    private void CountBackwardResult(MyMatrix<T> matrix, MyMatrix<T> vector, int n, MyMatrix<T> resultVector) {
        if (classType.equals(Float.class)) {
            for (int i = n - 1; i >= 0; i--) {
                float sum = 0f;
                for (int j = i + 1; j < n; j++) {
                    sum += matrix.matrix[i][j].floatValue() * resultVector.matrix[j][0].floatValue();
                }
                resultVector.matrix[i][0] = (T) (Float) ((vector.matrix[i][0].floatValue() - sum) / matrix.matrix[i][i].floatValue());

            }
        } else {
            if (classType.equals(Double.class)) {
                for (int i = n - 1; i >= 0; i--) {
                    double sum = 0.0;
                    for (int j = i + 1; j < n; j++) {
                        sum += matrix.matrix[i][j].doubleValue() * resultVector.matrix[j][0].doubleValue();
                    }
                    resultVector.matrix[i][0] = (T) (Double) ((vector.matrix[i][0].doubleValue() - sum) / matrix.matrix[i][i].doubleValue());

                }
            }
        }
    }

    public void findAndSetBiggestValueInMatrix(MyMatrix<T> matrix, MyMatrix<T> vector, int p, ArrayList<Integer> queue) {

        Float maxValue = matrix.matrix[p][p].floatValue();
        int rowIndex = p;
        int columnIndex = p;
        for (int ii = p; ii < matrix.rows; ii++) {
            for (int jj = p; jj < matrix.columns; jj++) {

                if (Math.abs(matrix.matrix[ii][jj].floatValue()) > maxValue) {
                    maxValue = Math.abs(matrix.matrix[ii][jj].floatValue());
                    rowIndex = ii;
                    columnIndex = jj;
                }
            }
        }

        flipRows(matrix, p, rowIndex);
        flipRows(vector, p, rowIndex);
        flipColumns(matrix, p, columnIndex, queue);
    }

    public MyMatrix<T> sortResultsByQueue(MyMatrix<T> vectorMatrix, ArrayList<Integer> queue) {

        MyMatrix<T> tmp = new MyMatrix(classType, vectorMatrix.rows, 1);
        for (int ii = 0; ii < vectorMatrix.rows; ii++) {
            for (int jj = 0; jj < vectorMatrix.columns; jj++) {
                tmp.matrix[ii][jj] = vectorMatrix.matrix[ii][jj];
            }
        }
        for (int i = 0; i < vectorMatrix.rows; i++) {
            vectorMatrix.matrix[queue.get(i)][0] = tmp.matrix[i][0];
        }
        return vectorMatrix;
    }

    public MyMatrix<T> flipRows(MyMatrix<T> finalMatrix, int row1, int row2) {
        if (row1 == row2) {
            return finalMatrix;
        }
        for (int i = 0; i < finalMatrix.columns; i++) {
            T tmp = finalMatrix.matrix[row1][i];
            finalMatrix.matrix[row1][i] = finalMatrix.matrix[row2][i];
            finalMatrix.matrix[row2][i] = tmp;
        }
        return finalMatrix;
    }

    //When columns are flipped, results order does to. Need to save it in queue arrayList
    public MyMatrix<T> flipColumns(MyMatrix<T> finalMatrix, int column1, int column2, ArrayList<Integer> queue) {
        if (column1 == column2) {
            return finalMatrix;
        }
        int tmp = queue.get(column1);
        queue.set(column1, queue.get(column2));
        queue.set(column2, tmp);

        for (int i = 0; i < finalMatrix.rows; i++) {
            T tmp2 = finalMatrix.matrix[i][column1];
            finalMatrix.matrix[i][column1] = finalMatrix.matrix[i][column2];
            finalMatrix.matrix[i][column2] = tmp2;
        }
        return finalMatrix;
    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%26.26s  ", matrix[i][j]);
            }
            System.out.println("");
        }
    }

    public void fillWithZero() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (classType.equals(Float.class)) {
                    Float zero = 0f;
                    matrix[i][j] = (T) zero;
                } else {
                    if (classType.equals(Double.class)) {
                        Double zero = new Double(0);
                        matrix[i][j] = (T) zero;
                    }
                }
            }
        }
    }
}
