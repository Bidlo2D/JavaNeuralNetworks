package Layers.Functions.Activation;

import Layers.Functions.Function;
import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.Signal;

public class Softmax extends Function {
    public Signal output = null;

    @Override
    public double activation(INeuron x) {
        double numerator = Math.exp(x.getValue());
        double denominator = numerator;
        int index = output.indexOf(x);
        for (int i = 0; i < output.fullSize(); i++)
        {
            if (i == index)
            {
                continue;
            }
            denominator += Math.exp(output.getValueSignal(i, 0 , 0));
        }
        return numerator / denominator;
    }
    @Override
    public double derivative(INeuron x) {
        double y = activation(x);
        return y * (1 - y);
    }
}
