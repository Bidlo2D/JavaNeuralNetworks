package Layers.Activation.Functions;

import SimpleClasses.Neuron;

public class Sigmoid implements IFunction{
    @Override
    public double Activation(Neuron x) {
        return 1 / (1 + Math.exp(-x.getValue()));
    }

    @Override
    public double Derivative(Neuron x) {
        var value = x.getValue();
        return value * (1 - value);
    }
}
