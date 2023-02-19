package Layers;

import Layers.Activation.Functions.Function;
import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.Signal;

import java.io.Serializable;
import java.util.List;

public abstract class Layer implements Serializable {
    protected Signal<INeuron> output;
    protected Signal<INeuron> input;
    protected Function typeActivation;
    public abstract Signal forward(Signal input);
    public abstract Signal backPropagation(Signal delta, int right, double E, double A);
    public abstract List<Double> getWeightList();
}
