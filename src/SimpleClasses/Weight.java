package SimpleClasses;

import java.io.Serializable;
import java.util.Arrays;

public class Weight implements Serializable {
    // Data
    private double[][] matrix;

    // Size
    public int n; public int m;

    // Constructors
    public Weight(int n, int m)
    {
        this.n = n; this.m = m;
        matrix = new double[n][m];
    }
    public Weight(double[][] matrix)
    {
        n = matrix.length; m = matrix[0].length;
        this.matrix= matrix;
    }
    // Indexer
    public double getWeight(int x, int y){
        return matrix[x][y];
    }
    public void setWeight(int x, int y, double value){
        matrix[x][y] = value;
    }
    public Weight getCloneWeight(){
        return new Weight(matrix);
    }
}
