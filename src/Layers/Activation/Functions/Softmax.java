package Layers.Activation.Functions;

import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.Signal;

public class Softmax extends Function {
    public Signal output = null;

    @Override
    public double Activation(INeuron x) {
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
    public double Derivative(INeuron x) {
        double y = Activation(x);
        return y * (1 - y);
    }
}
