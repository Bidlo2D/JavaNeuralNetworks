package Collector.Initializations;

import SimpleClasses.Batch;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.util.Random;

public class Generation {
    private static Random rnd = new Random();
    public static Signal RandomSignal(int sizeZ, int sizeX, int sizeY, int right)
    {
        Signal image = new Signal(sizeZ, sizeX, sizeY);
        for (int z = 0; z < image.sizeZ; z++)
        {
            for (int x = 0; x < image.sizeX; x++)
            {
                for (int y = 0; y < image.sizeY; y++)
                {
                    double value = rnd.nextDouble(-1, 1);
                    image.setSignal(z, x, y, value);
                }
            }
        }
        return image;
    }
    public static Batch RandomBatch(int sizeMini, int sizeBatch)
    {

        return null;
    }
    public static Weight RandomWeight(int sizeZ, int sizeX)
    {
        Weight weight = new Weight(sizeZ, sizeX);
        for (int x = 0; x < weight.n; x++)
        {
            for (int y = 0; y < weight.m; y++)
            {
                double value = rnd.nextDouble(-1, 1);
                weight.setWeight(x, y, value);
            }
        }
        return weight;
    }
}
