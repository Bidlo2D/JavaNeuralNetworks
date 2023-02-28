package Layers.Functions.Activation;

import Layers.Functions.Function;
import SimpleClasses.ComputingUnits.INeuron;

public class Sigmoid extends Function {
    @Override
    public double activation(INeuron x) {
        return 1 / (1 + Math.exp(-x.getValue()));
    }

    @Override
    public double derivative(INeuron x) {
        var value = x.getValue();
        return value * (1 - value);
    }
}
