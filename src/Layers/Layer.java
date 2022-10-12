package Layers;

import Layers.Activation.TypeActivation;
import SimpleClasses.Signal;

public abstract class Layer {
    protected Signal output;
    protected Signal input;
    protected TypeActivation typeActivation;
    public abstract Signal Forward(Signal input);
    public abstract Signal BackPropagation(Signal delta, int Right, double E, double A);
}
