package Layers.Activation;

import SimpleClasses.Signal;

public class ActivationFunctions {
    private static final double alpha = 0.13;
    private static Signal Output, Input;
    public static double Activation(TypeActivation type, double value, Signal input, Signal output, int index){
        Input = input;
        Output = output;
        switch (type){
            case Input:
                return value;
            case ReLU:
                return ReLU(value);
            case Tangent:
                return Tangent(value);
            case ELU :
                return ELU(value);
            case Softmax:
                return SoftMax(value, index);
            case Sigmoid:
                return Sigmoid(value);
            default:
                return 0;
        }
    }
    private static double Sigmoid(double x)
    {
        return 1 / (1 + Math.exp(-x));
    }
    private static double Tangent(double x)
    {
        return 2 / (1 + Math.exp(x)) - 1;
    }
    public static double ReLU(double x)
    {
        if (x <= 0) { return 0; }
        else { return x; }
    }
    private static double ELU(double x)
    {
        if (x > 0) { return x; }
        else { return alpha * (Math.exp(x) - 1); }
    }
    private static double SoftMax(double x, int index)
    {
        if (index < Output.sizeZ - 1)
        {
            return Math.exp(x);
        }
        else
        {
            for (int i = 0; i < Output.sizeZ; i++)
            {
                double value = Output.getSignal(i, 0, 0) / SumSoftmaxActive();
                Output.setSignal(i, 0, 0, value);
            }
            return Math.exp(x) / SumSoftmaxActive();
        }
    }
    private static double SumSoftmaxActive()
    {
        double sum = 0;
        for (int i = 0; i < Output.sizeZ; i++)
        {
            sum += Output.getSignal(i, 0, 0);
        }
        return sum;
    }
}
