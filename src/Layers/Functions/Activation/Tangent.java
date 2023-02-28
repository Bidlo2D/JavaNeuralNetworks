package Layers.Functions.Activation;

import Layers.Functions.Function;
import SimpleClasses.ComputingUnits.INeuron;

public class Tangent extends Function {
    @Override
    public double activation(INeuron x) {
        return 2 / (1 + Math.exp(x.getValue())) - 1;
    }

    @Override
    public double derivative(INeuron x) {
        return 1 - Math.pow(x.getValue(), 2);
    }
}
