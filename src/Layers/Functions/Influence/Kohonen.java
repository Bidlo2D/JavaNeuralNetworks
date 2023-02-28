package Layers.Functions.Influence;

import Layers.Functions.Function;
import SimpleClasses.ComputingUnits.Neuron;
import SimpleClasses.ComputingUnits.NeuronWTA;
import SimpleClasses.Signal;

public class Kohonen extends Function {
    public Signal<NeuronWTA> output = null;
    public Signal<Neuron> distance = null;
    public void activation() { return; }
    public double influence(int i) { return 0; }
}
