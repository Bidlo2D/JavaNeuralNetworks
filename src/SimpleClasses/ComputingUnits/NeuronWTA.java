package SimpleClasses.ComputingUnits;

import java.io.Serializable;


public class NeuronWTA implements Serializable, INeuron {
    private double value;

    private int wins;

    private int block;

    private int rate = 15;

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return wins >= rate && block <= rate ? 0 : value;
    }

    public void reset() {
        wins = 0;
    }

    public void plus() {
        wins += 1;
    }

    public NeuronWTA() {  }
    public NeuronWTA(double value, int rate) { this.value = value; this.rate =  rate; }
}
