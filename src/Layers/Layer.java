package Layers;

import Layers.Activation.Functions.Function;
import SimpleClasses.Signal;

import java.io.Serializable;

public abstract class Layer implements Serializable {
    protected Signal output;
    protected Signal input;
    protected Function typeActivation;
    public abstract Signal Forward(Signal input);
    public abstract Signal BackPropagationTeacher(Signal delta, int right, double E, double A);
    public abstract Signal BackPropagationNoTeacher(Signal delta, double E, double A);
}
