package SimpleClasses;

public class Neuron {
    private double value;

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
    public Neuron(){ }
    public Neuron(double value){ this.value = value; }
}
