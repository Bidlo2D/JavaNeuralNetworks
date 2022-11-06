package Layers.Activation.Functions;

import SimpleClasses.Neuron;

public class ELU implements IFunction{
    private final double alpha = 0.13;
    @Override
    public double Activation(Neuron x) {
        if (x.getValue() > 0) { return x.getValue(); }
        else { return alpha * (Math.exp(x.getValue()) - 1); }
    }

    @Override
    public double Derivative(Neuron x) {
        if (x.getValue() > 0) { return 1; }
        else { return x.getValue() + alpha; }
    }
}
