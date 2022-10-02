package Layers;

import SimpleClasses.Signal;

public abstract class Layer {
    protected Signal delta;
    protected Signal output;
    protected Signal input;
    public abstract Signal Forward(Signal input);
    public abstract void BackPropagation();
}
