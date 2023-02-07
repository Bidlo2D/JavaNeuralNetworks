package Layers.FullyLayers;

import Collector.Initializations.Generation;
import Layers.Activation.Functions.Function;
import Layers.Activation.Functions.Softmax;
import Layers.Enums.TypeLayer;
import Layers.Layer;
import SimpleClasses.Neuron;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

public class FCHLayer extends Layer {
    protected Weight weights;
    protected Signal biases;
    protected Signal corrections;
    public int countNeurons;
    public FCHLayer(int countNeurons, Function typeActivation){
        this.countNeurons = countNeurons;
        this.typeActivation = typeActivation;
    }

    @Override
    public Signal Forward(Signal input) {
        this.input = input;
        if(weights == null ||
           biases == null ||
           corrections == null ||
           output == null) { Initialization(); }
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            Neuron Sum = new Neuron();
            Sum.setValue(biases.getValueSignal(w1, 0, 0));
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                double plus = input.getValueSignal(w2, 0, 0) * weights.getWeight(w1, w2);
                Sum.setValue(Sum.getValue() + plus);
            }

            output.setSignal(w1, 0, 0, Sum);
        }
        Activation(output);
        return output;
    }

    protected void Activation(Signal activation) {
        for (int i = 0; i < activation.fullSize(); i++)
        {
            double result = typeActivation.Activation(activation.getSignal(i, 0, 0));
            activation.setValueSignal(i, 0, 0, result);
        }
    }

    @Override
    public Signal BackPropagation(Signal delta, int right, double E, double A) {
        Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY, true);
        for (int i = 0; i < weights.n; i++)
        {
            double df = 0;
            for (int j = 0; j < weights.m; j++)
            {
                double dout = typeActivation.Derivative(input.getSignal(j, 0, 0));
                df += dout * delta.getValueSignal(i, 0, 0);
                double gradient = (dout * (weights.getWeight(i, j) * delta.getValueSignal(i, 0, 0)));
                deltaOutput.setValueSignal(j, 0, 0, gradient + deltaOutput.getValueSignal(j, 0, 0));
                double GRADw = input.getValueSignal(j, 0, 0) * delta.getValueSignal(i, 0, 0);
                double gradientNext = E * GRADw + A * corrections.getValueSignal(i, 0, 0);
                corrections.setValueSignal(i, 0, 0, gradientNext);
                weights.setWeight(i, j, weights.getWeight(i, j) + corrections.getValueSignal(i, 0, 0));
            }
            biases.setValueSignal(i, 0, 0, (df * E) + biases.getValueSignal(i, 0, 0));
        }
        return deltaOutput;
    }

    protected void Initialization() {
        int sizeZ = input.fullSize();
        output = new Signal(countNeurons, 1, 1, false);
        if(typeActivation.getClass().getSimpleName().equals("Softmax")){
            Softmax castedDog = (Softmax) typeActivation;
            castedDog.output = output;
        }
        corrections = new Signal(countNeurons, 1,1, true);
        biases = Generation.RandomSignal(countNeurons, 1 , 1 , 0, 0, 0.1);
        weights = Generation.RandomWeight(countNeurons, sizeZ, -0.05, 0.05);
    }
}
