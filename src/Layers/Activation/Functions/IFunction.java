package Layers.Activation.Functions;

import SimpleClasses.Neuron;

public interface IFunction {
    double Activation(Neuron x);
    double Derivative(Neuron x);
}
