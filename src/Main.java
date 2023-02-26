import Collector.Network;
import Layers.Activation.Functions.*;
import Layers.ConvLayers.ConvolutionLayer;
import Layers.ConvLayers.PoolingLayer;
import Layers.EmbeddingLayers.EmbeddingLayer;
import Layers.FullyLayers.FCCLayer;
import Layers.FullyLayers.FCHLayer;
import Layers.RecurrentLayers.RecurrentLayer;
import Layers.WTA.WTALayer;
import SimpleClasses.Dates.Batch;
import SimpleClasses.Dates.Converters.Exceptions.NoDirectoryException;
import SimpleClasses.Dates.MiniBatch;
import SimpleClasses.Signal;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    public static void main (String[] args) throws NoDirectoryException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
/*        int maxlen = 200;
        var convText  = new ConverterText("C:\\Games\\Programs\\DataSets\\TextNet\\Texts",
                TokenType.Word, LanguageStemmer.EN, new RangeNorm(-1.0, 1.0), maxlen);
        var batch = new Batch(convText.dates, 1);*/
        var batch = TestDataClasses(2);
        var net = CreateTestText();
        net.Train(batch);
        //var result = net.getGraphInfo();
        //var result = net.Test(batch);
        //ShowResult(result);
        //GraphPanel draw = new GraphPanel(net.getGraphInfo().getFlat());
        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        new SwingWrapper(chart).displayChart();
        //WTALayer WTALayer1= new WTALayer(5, new Sigmoid());
        //WTALayer1.forward(batch.miniBatches.get(0).signals.get(0));
        System.out.println("END");
    }

    private static Network CreateTestText() {
        int classes = 2;
        Network net = new Network();
        EmbeddingLayer layerE = new EmbeddingLayer(30, new Tangent());
        WTALayer layerWTA = new WTALayer(4, new Tangent());
        RecurrentLayer layerR1 = new RecurrentLayer(30, new Tangent());
        FCHLayer layerFCH1 = new FCHLayer(80, new Sigmoid());
        FCHLayer layerFCH2 = new FCHLayer(80, new Sigmoid());
        RecurrentLayer layerR2 = new RecurrentLayer(25, new Tangent());
        FCCLayer layerC = new FCCLayer(classes, new Softmax());
        //net.AddLayer(layerE);
        // 1
        net.addLayer(layerWTA);
        //net.AddLayer(layerR1);
        // 2
        //net.AddLayer(layerFCH1);
        //net.AddLayer(layerFCH2);
        //net.AddLayer(layerR2);
        // 3
        //net.AddLayer(layerC);
        // Settings
        net.setEpoth(320);
        net.setLearnRate(0.0915);
        return net;
    }

    private static Network CreateTestVision() {
        int classes = 10;
        Network net = new Network();

        ConvolutionLayer cl1 = new ConvolutionLayer(5,16,1, 0);
        PoolingLayer pl1 = new PoolingLayer(2);
        ConvolutionLayer cl2 = new ConvolutionLayer(3,34,3, 0);
        PoolingLayer pl2 = new PoolingLayer(2);
        FCHLayer layer1 = new FCHLayer(120, new Tangent());
        FCHLayer layer2 = new FCHLayer(80, new Tangent());
        FCHLayer layer3 = new FCHLayer(60, new Tangent());
        FCCLayer layer4 = new FCCLayer(classes, new Softmax());
        net.addLayer(cl1);
        net.addLayer(pl1);
        net.addLayer(cl2);
        net.addLayer(pl2);
        net.addLayer(layer1);
        net.addLayer(layer2);
        net.addLayer(layer3);
        net.addLayer(layer4);
        net.setEpoth(300);
        net.setLearnRate(0.0925);
        return net;
    }

    private static void Save(Network net, String path) throws NoDirectoryException, IOException {
        FileOutputStream outputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // сохраняем игру в файл
        objectOutputStream.writeObject(net);

        //закрываем поток и освобождаем ресурсы
        objectOutputStream.close();
    }

    private static Network Load(String path) throws NoDirectoryException, IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Network network = (Network) objectInputStream.readObject();

        return network;
    }

    private static Batch ConvTest(){
        double[] mass = { 4, 5, 8, 7,
                1, 8, 8, 8,
                3, 6, 6, 4,
                6, 5, 7, 8};
        Batch data = new Batch();
        MiniBatch mini = new MiniBatch();
        Signal signal = new Signal(1,4,4, 0);
        for(int i = 0; i < mass.length; i++){
            signal.setValueSignal(i,0,0, mass[i]);
        }
        mini.signals.add(signal);
        data.miniBatches.add(mini);
        return data;
    }

    private static Batch TestDataClasses(int classes) {
        Random rnd = new Random();
        Batch data = new Batch();
        for (int i = 0; i < classes; i++){
            MiniBatch mini = new MiniBatch();

            Signal signal = new Signal(2,1,1, i);
            for(int s = 0; s < signal.fullSize(); s++){
                double value = rnd.nextDouble(-1, 1);
                signal.setValueSignal(s,0,0, value);
            }

            mini.signals.add(signal);

            data.miniBatches.add(mini);
        }

        return data;
    }

    private static void ShowResult(List<Map<Integer, Double>> list){
        for (var input: list) {
            for (var name: input.keySet()) {
                String key = name.toString();
                String value = input.get(name).toString();
                System.out.println("answer = " + key + ", value = " + value);
            }
        }
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
                    if(value >= 0.0){
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
