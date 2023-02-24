package SimpleClasses.ComputingUnits;

import java.io.Serializable;

public class NeuronFC extends Neuron implements Serializable {

    private boolean drop = false;

    public void setBlocked(boolean drop) {
        this.drop = drop;
    }

    public NeuronFC(){ }
    public NeuronFC(double value){ this.value = value; }

}
