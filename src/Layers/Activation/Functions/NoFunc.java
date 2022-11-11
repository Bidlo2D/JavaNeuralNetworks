package Layers.Activation.Functions;

import SimpleClasses.Neuron;

public class NoFunc implements IFunction{
    @Override
    public double Activation(Neuron x) {
        return x.getValue();
    }

    @Override
    public double Derivative(Neuron x) {
        return x.getValue();
    }
}
