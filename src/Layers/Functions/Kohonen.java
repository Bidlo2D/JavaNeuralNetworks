package Layers.Functions;

import Layers.Functions.Function;
import SimpleClasses.ComputingUnits.Neuron;
import SimpleClasses.ComputingUnits.NeuronWTA;
import SimpleClasses.Signal;

public abstract class Kohonen extends Function {
    public Signal<NeuronWTA> output;
    public Signal<Neuron> distance;
    @Override
    public double activation() { return 0; }
    public double influence(int x) { return 0; }
}
