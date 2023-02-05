package Layers.RecurrentLayers;

import Layers.Activation.Functions.Function;
import Layers.FullyLayers.FCHLayer;
import SimpleClasses.Signal;

public class RecurrentLayer extends FCHLayer {
    public RecurrentLayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
    }

    @Override
    public Signal Forward(Signal input) {
        var result = super.Forward(input);
        if(output.getSignal(0,0,0) != null){
            return SumSignals(result, output);
        }
        else{
            return result;
        }
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
}
