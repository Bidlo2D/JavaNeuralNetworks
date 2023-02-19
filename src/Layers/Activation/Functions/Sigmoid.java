package Layers.Activation.Functions;

import SimpleClasses.ComputingUnits.INeuron;

public class Sigmoid extends Function {
    @Override
    public double Activation(INeuron x) {
        return 1 / (1 + Math.exp(-x.getValue()));
    }

    @Override
    public double Derivative(INeuron x) {
        var value = x.getValue();
        return value * (1 - value);
    }
}
