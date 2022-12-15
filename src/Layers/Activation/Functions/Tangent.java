package Layers.Activation.Functions;

import SimpleClasses.Neuron;

public class Tangent extends Function {
    @Override
    public double Activation(Neuron x) {
        return 2 / (1 + Math.exp(x.getValue())) - 1;
    }

    @Override
    public double Derivative(Neuron x) {
        return 1 - Math.pow(x.getValue(), 2);
    }
}
