package Layers.Activation.Functions;

import SimpleClasses.ComputingUnits.INeuron;

public class ELU extends Function {
    private final double alpha = 0.13;
    @Override
    public double Activation(INeuron x) {
        if (x.getValue() > 0) { return x.getValue(); }
        else { return alpha * (Math.exp(x.getValue()) - 1); }
    }

    @Override
    public double Derivative(INeuron x) {
        if (x.getValue() > 0) { return 1; }
        else { return x.getValue() + alpha; }
    }
}
