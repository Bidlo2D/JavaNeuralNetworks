import Collector.Network;
import Layers.Activation.TypeActivation;
import Layers.FCCLayer;
import Layers.FCHLayer;
import SimpleClasses.Dates.Batch;
import SimpleClasses.Dates.ConverterImage.ConverterImage;
import SimpleClasses.Dates.ConverterImage.Exceptions.NoDirectoryException;
import SimpleClasses.Signal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main (String[] args) throws NoDirectoryException {
        int classes = 10;
        Network net = new Network();
        FCHLayer layer1 = new FCHLayer(15, TypeActivation.Sigmoid);
        FCHLayer layer2 = new FCHLayer(20, TypeActivation.Sigmoid);
        FCHLayer layer3 = new FCHLayer(15, TypeActivation.Sigmoid);
        FCCLayer layer4 = new FCCLayer(classes, TypeActivation.Softmax);
        net.AddLayer(layer1);
        net.AddLayer(layer2);
        net.AddLayer(layer3);
        net.AddLayer(layer4);
        net.SetEpoth(1500);
        net.SetLearnRate(0.0175);
        ConverterImage images = new ConverterImage("C:\\Games\\Programs\\Fonts\\Numbers(32x32) - 2 count");
        Batch batchTest = new Batch(images.dates, 2);
        net.Train(batchTest);
        var data = net.Test(batchTest);
        for(int d = 0; d < data.size(); d++){
            System.out.println("answer = " + data.get(d).keySet() + "; " +
                    "value = " +  data.get(d).values());
        }
        System.out.println("END");
    }
}
