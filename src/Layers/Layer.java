package Layers;

import Layers.Activation.Functions.Function;
import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.Signal;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class Layer implements Serializable {
    protected Signal<INeuron> output;
    protected Signal<INeuron> input;
    protected Class<INeuron> clazz;
    protected Function typeActivation;
    public abstract Signal forward(Signal input) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    public abstract Signal backPropagation(Signal delta, int right, double E, double A);
    public abstract List<Double> getWeightList();
}
