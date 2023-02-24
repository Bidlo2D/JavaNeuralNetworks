package SimpleClasses.ComputingUnits;

public class Neuron implements INeuron {

    protected double value;
    public Neuron(){}

    public Neuron(double value){
        this.value = value;
    }

    @Override
    public void setValue(double value) {
        this.value =value;
    }

    @Override
    public double getValue() {
        return 0;
    }
}
