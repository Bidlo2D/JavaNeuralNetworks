package Layers.Activation;

import SimpleClasses.Signal;

public class DeactivationFunctions {
    private static Signal Neurons;
    private static final double alpha = 0.13;
    public static double Deactivation(TypeActivation type,double value, Signal input)//Производная
    {
        Neurons = input;
        switch (type)
        {
            case Input:
                return value;
            case ReLU:
                return ReLU(value);
            case Tangent:
                return Tangent(value);
            case Sigmoid:
                return Sigmoid(value);
            case Softmax:
                return SoftMax(value);
            case ELU:
                return ELU(value);
            default:
                return 0;
        }
    }
    private static double Sigmoid(double x)
    {
        return x * (1 - x);
    }
    private static double Tangent(double x)
    {
        return 1 - Math.pow(x, 2);
    }
    private static double ELU(double x)
    {
        if (x > 0) { return 1; }
        else { return x + alpha; }
    }
    private static double ReLU(double x)
    {
        if (x > 0) { return 1; }
        else { return 0; }
    }
    private static double SoftMax(double x)
    {
        return (x * SumSoftmax(1)) / Math.pow(SumSoftmax(0), 2);
    }
    private static double SumSoftmax(int iMode)
    {
        double sum = 0;
        for (int i = iMode; i < Neurons.sizeZ; i++)
        {
            sum += Neurons.getSignal(i, 0, 0);
        }
        return sum;
    }
}
