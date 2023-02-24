package Layers.RecurrentLayers;
import Layers.Activation.Functions.Function;
import Layers.FullyLayers.FCHLayer;
import SimpleClasses.ComputingUnits.NeuronFC;
import SimpleClasses.Signal;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class RecurrentLayer extends FCHLayer {
    public RecurrentLayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
    }
    private Signal memorySignal;
    @Override
    public Signal forward(Signal input) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        this.input = input;
        if(weights == null ||
                biases == null ||
                corrections == null ||
                output == null) { initialization(); }
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            NeuronFC Sum = new NeuronFC();
            Sum.setValue(biases.getValueSignal(w1, 0, 0));
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                double plus = input.getValueSignal(w2, 0, 0) * weights.getWeight(w1, w2);
                Sum.setValue(Sum.getValue() + plus);
            }

            output.setSignal(w1, 0, 0, Sum);
        }
        activation(SumSignals(output, memorySignal));
        memorySignal = new Signal(Arrays.stream(output.getCloneSignals()).toList());
        return output;
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
        return X;
    }

    protected void initialization() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        super.initialization();
        memorySignal = new Signal(output.sizeZ, output.sizeX, output.sizeY);
    }
}
