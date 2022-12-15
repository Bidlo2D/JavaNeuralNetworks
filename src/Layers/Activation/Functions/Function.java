package Layers.Activation.Functions;

import SimpleClasses.Neuron;

import java.io.Serializable;

public abstract class Function implements Serializable {
    public double Activation(Neuron x) { return x.getValue(); }
    public double Derivative(Neuron x) { return x.getValue(); }
}
