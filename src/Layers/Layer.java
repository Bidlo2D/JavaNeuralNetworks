package Layers;

import Layers.Activation.Functions.Function;
import SimpleClasses.Signal;

import java.io.Serializable;

public abstract class Layer implements Serializable {
    protected Signal output;
    protected Signal input;
    protected Function typeActivation;
    public abstract Signal forward(Signal input);
    public abstract Signal backPropagationTeacher(Signal delta, int right, double E, double A);
    public abstract Signal backPropagationNoTeacher(Signal delta, double E, double A);
}
