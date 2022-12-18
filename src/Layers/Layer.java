package Layers;

import Layers.Activation.Functions.Function;
import Layers.Enums.TypeLayer;
import SimpleClasses.Signal;

import java.io.Serializable;

public abstract class Layer implements Serializable {
    protected Signal output;
    protected Signal input;
    protected Function typeActivation;
    public TypeLayer typeLayer;
    public abstract Signal Forward(Signal input);
    public abstract Signal BackPropagation(Signal delta, int Right, double E, double A);
}
