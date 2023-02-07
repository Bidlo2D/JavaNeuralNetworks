package Layers.RecurrentLayers;

import Collector.Initializations.Generation;
import Layers.Activation.Functions.Function;
import Layers.Activation.Functions.Softmax;
import Layers.FullyLayers.FCHLayer;
import SimpleClasses.Neuron;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.util.Arrays;

public class RecurrentLayer extends FCHLayer {
    public RecurrentLayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
    }
    private Signal memorySignal;
    @Override
    public Signal Forward(Signal input) {
        super.Forward(input);
        Activation(SumSignals(output, memorySignal));
        memorySignal = new Signal(Arrays.stream(output.getCloneSignals()).toList());
        return output;
    }

    private Signal ForwardNoOutput(Signal input, Weight weights) {
        var result = new Signal(output.sizeZ, output.sizeX, output.sizeY, true);
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            Neuron Sum = new Neuron();
            Sum.setValue(biases.getValueSignal(w1, 0, 0));
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                double plus = input.getValueSignal(w2, 0, 0) * weights.getWeight(w1, w2);
                Sum.setValue(Sum.getValue() + plus);
            }

            result.setSignal(w1, 0, 0, Sum);
        }
        Activation(result);
        return result;
    }

    private Signal SumSignals(Signal X, Signal Y) {
        if(X.fullSize() != Y.fullSize()){ return X; }
        for (int z = 0; z < X.sizeZ; z++) {
            for (int x = 0; x < X.sizeX; x++) {
                for (int y = 0; y < X.sizeY; y++) {
                    double valueX = X.getValueSignal(z, x, y);
                    double valueY = Y.getValueSignal(z, x, y);
                    X.setValueSignal(z, x, y, valueX + valueY);
                }
            }
        }
        //Activation(X);
        return X;
    }

    protected void Initialization() {
        super.Initialization();
        memorySignal = new Signal(output.sizeZ, output.sizeX, output.sizeY, true);
    }
}
