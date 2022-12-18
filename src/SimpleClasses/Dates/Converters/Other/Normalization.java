package SimpleClasses.Dates.Converters.Other;

import SimpleClasses.Signal;

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
