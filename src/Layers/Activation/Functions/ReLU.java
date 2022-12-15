package Layers.Activation.Functions;

import SimpleClasses.Neuron;

public class ReLU extends Function {
    @Override
    public double Activation(Neuron x) {
        if (x.getValue() <= 0) { return 0; }
        else { return x.getValue(); }
    }

    @Override
    public double Derivative(Neuron x) {
        if (x.getValue() > 0) { return 1; }
        else { return 0; }
    }
}
