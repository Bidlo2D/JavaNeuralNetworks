package Layers.Functions;

import SimpleClasses.ComputingUnits.INeuron;

import java.io.Serializable;

public abstract class Function<T extends INeuron> implements Serializable {
    public double activation(T x) { return x.getValue(); }
    public double derivative(T x) { return x.getValue(); }
}
