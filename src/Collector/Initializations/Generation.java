package Collector.Initializations;

import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.util.Random;

public class Generation {
    private static Random rnd = new Random();
    public static Signal RandomSignal(int sizeZ, int sizeX, int sizeY, int right, double min, double max)
    {
        Signal<INeuron> signal = new Signal(sizeZ, sizeX, sizeY, right);
        for (int z = 0; z < signal.sizeZ; z++)
        {
            for (int x = 0; x < signal.sizeX; x++)
            {
                for (int y = 0; y < signal.sizeY; y++)
                {
                    double value = rnd.nextDouble(min, max);
                    signal.setValueSignal(z, x, y, value);
                }
            }
        }
        return signal;
    }

    public static Weight RandomWeight(int sizeZ, int sizeX, double min, double max)
    {
        Weight weight = new Weight(sizeZ, sizeX);
        for (int x = 0; x < weight.n; x++)
        {
            for (int y = 0; y < weight.m; y++)
            {
                double value = rnd.nextDouble(min, max);
                weight.setWeight(x, y, value);
            }
        }
        return weight;
    }

    public static int RandomProbability(int min, int max)
    {
        return rnd.nextInt(min, max);
    }
}
