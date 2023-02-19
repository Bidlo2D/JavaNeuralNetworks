package SimpleClasses.ComputingUnits;

import java.io.Serializable;

public class NeuronFC implements Serializable, INeuron {
    protected double value;

    private boolean drop = false;

    public void setBlocked(boolean drop) {
        this.drop = drop;
    }

    public NeuronFC(){ }
    public NeuronFC(double value){ this.value = value; }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }
}
