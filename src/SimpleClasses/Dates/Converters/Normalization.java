package SimpleClasses.Dates.Converters;

import Layers.Activation.Functions.Sigmoid;
import SimpleClasses.Dates.Converters.Enums.RangeNorm;
import SimpleClasses.Neuron;
import SimpleClasses.Signal;

import java.sql.Array;
import java.util.Collections;
import java.util.List;

public class Normalization {

    public static void NormalSignal(Signal signal, RangeNorm range) {
        double minS = signal.Min();
        double maxS = signal.Max();
        for (int z = 0, n = 0; z < signal.sizeZ; z++) {
            for (int x = 0; x < signal.sizeX; x++) {
                for (int y = 0; y < signal.sizeY; y++, n++) {
                    double value = signal.getValueSignal(z, x, y);
                    double norm = ((value - minS) * (range.getMax() - range.getMin())  / (maxS - minS)) + range.getMin();
                    signal.setValueSignal(z, x, y, norm);
                }
            }
        }
    }
}
