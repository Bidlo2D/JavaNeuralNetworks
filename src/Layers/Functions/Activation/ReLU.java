package Layers.Functions.Activation;

import Layers.Functions.Function;
import SimpleClasses.ComputingUnits.INeuron;

public class ReLU extends Function {
    @Override
    public double activation(INeuron x) {
        if (x.getValue() <= 0) { return 0; }
        else { return x.getValue(); }
    }

    @Override
    public double derivative(INeuron x) {
        if (x.getValue() > 0) { return 1; }
        else { return 0; }
    }
}
