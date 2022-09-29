package Layers;

import SimpleClasses.Signal;

public abstract class Layer {
    protected Signal delta;
    protected Signal output;
    protected Signal input;
    public abstract void Forward(Signal input);
    public abstract void BackPropagation();
}
