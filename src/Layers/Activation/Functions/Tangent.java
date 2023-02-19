package Layers.Activation.Functions;

import SimpleClasses.ComputingUnits.INeuron;

public class Tangent extends Function {
    @Override
    public double Activation(INeuron x) {
        return 2 / (1 + Math.exp(x.getValue())) - 1;
    }

    @Override
    public double Derivative(INeuron x) {
        return 1 - Math.pow(x.getValue(), 2);
    }
}
