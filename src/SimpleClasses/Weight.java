package SimpleClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Double> getFlat(){
        List<Double> list = new ArrayList<>();
        for(int w1 = 0; w1 < n; w1++) {
            for(int w2 = 0; w2 < m; w2++){
                list.add(getWeight(w1, w2));
            }
        }
        return list;
    }

    public List<Double> getWeightByParam(int param){
        List<Double> list = new ArrayList<>();
        for(int w1 = 0; w1 < n; w1++) {
            list.add(getWeight(w1, param));
        }
        return list;
    }
    public void setWeight(int x, int y, double value){
        matrix[x][y] = value;
    }
    public Weight getCloneWeight(){
        return new Weight(matrix);
    }
}
