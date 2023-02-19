package Layers.Activation.Functions;

import SimpleClasses.ComputingUnits.INeuron;

import java.io.Serializable;

public abstract class Function implements Serializable {
    public double Activation(INeuron x) { return x.getValue(); }
    public double Derivative(INeuron x) { return x.getValue(); }
}
