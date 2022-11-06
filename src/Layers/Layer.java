package Layers;

import Layers.Activation.Functions.IFunction;
import SimpleClasses.Signal;

public abstract class Layer {
    protected Signal output;
    protected Signal input;
    protected IFunction typeActivation;
    public abstract Signal Forward(Signal input);
    public abstract Signal BackPropagation(Signal delta, int Right, double E, double A);
}
