package Layers.Activation.Functions;

import SimpleClasses.ComputingUnits.INeuron;

public class ReLU extends Function {
    @Override
    public double Activation(INeuron x) {
        if (x.getValue() <= 0) { return 0; }
        else { return x.getValue(); }
    }

    @Override
    public double Derivative(INeuron x) {
        if (x.getValue() > 0) { return 1; }
        else { return 0; }
    }
}
