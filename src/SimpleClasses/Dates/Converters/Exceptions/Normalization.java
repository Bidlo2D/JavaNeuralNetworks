package SimpleClasses.Dates.Converters.Exceptions;

import Layers.Activation.Functions.Sigmoid;
import SimpleClasses.Neuron;
import SimpleClasses.Signal;

import java.sql.Array;
import java.util.Collections;
import java.util.List;

public class Normalization {
    public static void ZeroToOne(Signal signal) {
        Double min = signal.Min();
        Double max = signal.Max();
        for (int z = 0; z < signal.sizeZ; z++) {
            for (int x = 0; x < signal.sizeX; x++) {
                for (int y = 0; y < signal.sizeY; y++) {
                    Double value = signal.getValueSignal(z, x, y);
                    signal.setValueSignal(z, x, y,(value - min) / (max - min));
                }
            }
        }
    }
    public static Double ZeroToOne(Double x, Double min, Double max){
        return (x - min) / (max - min);
    }
}
