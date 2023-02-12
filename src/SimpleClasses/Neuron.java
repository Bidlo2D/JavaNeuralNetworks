package SimpleClasses;

import java.io.Serializable;
import java.util.Comparator;

public class Neuron implements Serializable {
    private double value;

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Neuron(){ }
    public Neuron(double value){ this.value = value; }

    public static int compare (Neuron n1, Neuron n2){
        if(n1.getValue()> n2.getValue())
            return 1;
        else if(n1.getValue()< n2.getValue())
            return -1;
        else
            return 0;
    }
}
