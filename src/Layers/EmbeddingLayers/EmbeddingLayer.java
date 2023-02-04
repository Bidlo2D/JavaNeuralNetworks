package Layers.EmbeddingLayers;

import Collector.Initializations.Generation;
import Layers.Activation.Functions.Function;
import Layers.Activation.Functions.Softmax;
import Layers.FullyLayers.FCHLayer;
import Layers.Layer;
import SimpleClasses.Neuron;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmbeddingLayer extends FCHLayer {
    public EmbeddingLayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
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
        return output;
    }
}
