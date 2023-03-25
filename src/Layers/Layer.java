package Layers;

import Layers.Functions.Function;
import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.Signal;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class Layer<N extends INeuron, T extends Function> implements Serializable {
    protected Signal<N> output;
    protected Signal<N> input;
    protected T typeActivation;
    public abstract Signal forward(Signal input) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    public abstract Signal backPropagation(Signal delta, int right, double E, double A);
    public abstract List<Double> getWeightList();
}
