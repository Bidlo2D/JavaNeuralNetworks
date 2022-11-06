import Collector.Network;
import Layers.Activation.Functions.Sigmoid;
import Layers.Activation.Functions.Softmax;
import Layers.FCCLayer;
import Layers.FCHLayer;
import SimpleClasses.Dates.Batch;
import SimpleClasses.Dates.ConverterImage.ConverterImage;
import SimpleClasses.Dates.ConverterImage.Exceptions.NoDirectoryException;
import SimpleClasses.Dates.MiniBatch;
import SimpleClasses.Signal;

import java.text.DecimalFormat;
import java.util.Random;

public class Main {
    public static void main (String[] args) throws NoDirectoryException {
        int classes = 10;
        Network net = new Network();
        FCHLayer layer1 = new FCHLayer(10, new Sigmoid());
        FCHLayer layer2 = new FCHLayer(15, new Sigmoid());
        //FCHLayer layer3 = new FCHLayer(25, new Sigmoid());
        FCCLayer layer4 = new FCCLayer(classes, new Softmax());
        net.AddLayer(layer1);
        net.AddLayer(layer2);
        //net.AddLayer(layer3);
        net.AddLayer(layer4);
        net.SetEpoth(1000);
        net.SetLearnRate(0.25);
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

    private static Batch TestDataBinary(){
        MiniBatch mini1 = new MiniBatch();
        MiniBatch mini2 = new MiniBatch();

        Signal data1 = new Signal(2,1,1, 0);
        data1.setValueSignal(0,0,0, 0);
        data1.setValueSignal(1,0,0, 1);

        Signal data2 = new Signal(2,1,1, 1);
        data2.setValueSignal(0,0,0, 1);
        data2.setValueSignal(1,0,0, 0);

        //Signal data3 = new Signal(2,1,1, 2);
        //data3.setValueSignal(0,0,0, 1);
        //data3.setValueSignal(1,0,0, 1);

        //Signal data4 = new Signal(2,1,1, 3);
        //data4.setValueSignal(0,0,0, 0);
        //data4.setValueSignal(1,0,0, 0);

        Batch data = new Batch();
        mini1.signals.add(data1);
        mini2.signals.add(data2);
        //mini1.signals.add(data3);
        //mini2.signals.add(data4);

        data.miniBatches.add(mini1);
        data.miniBatches.add(mini2);

        return data;
    }
    private static Batch TestDataClasses(int classes) {
        Random rnd = new Random();
        Batch data = new Batch();
        for (int i = 0; i < classes; i++){
            MiniBatch mini = new MiniBatch();

            Signal signal = new Signal(1,1,1, i);
            double value = rnd.nextDouble(0, 1);
            signal.setValueSignal(0,0,0, value);

            mini.signals.add(signal);

            data.miniBatches.add(mini);
        }

        return data;
    }

    private static void ShowSignal(Signal input){
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RED = "\u001B[31m";
        DecimalFormat decimalFormat = new DecimalFormat( "#0.00" );
        for(int z = 0; z < input.sizeZ; z++){
            for(int x = 0; x < input.sizeX; x++) {
                for(int y = 0;y < input.sizeY; y++) {
                    var value = input.getValueSignal(z, x, y);
                    String result = decimalFormat.format(value);
                    if(value > 0.0){
                        System.out.print(result + " " + ANSI_GREEN);
                    }
                    else { System.out.print(result + " " + ANSI_RED); }
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
