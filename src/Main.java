import Collector.Network;
import Layers.Activation.Functions.*;
import Layers.ConvLayers.ConvolutionLayer;
import Layers.ConvLayers.PoolingLayer;
import Layers.EmbeddingLayers.EmbeddingLayer;
import Layers.FullyLayers.FCCLayer;
import Layers.FullyLayers.FCHLayer;
import Layers.Layer;
import Layers.RecurrentLayers.RecurrentLayer;
import SimpleClasses.Dates.Batch;
import SimpleClasses.Dates.Converters.ConverterText;
import SimpleClasses.Dates.Converters.Enums.LanguageStemmer;
import SimpleClasses.Dates.Converters.Enums.TokenType;
import SimpleClasses.Dates.Converters.Other.PorterStemmer.PorterStemmerEN;
import SimpleClasses.Dates.Converters.Other.PorterStemmer.PorterStemmerRU;
import SimpleClasses.Dates.Converters.Other.RangeNorm;
import SimpleClasses.Dates.Converters.Exceptions.NoDirectoryException;
import SimpleClasses.Dates.MiniBatch;
import SimpleClasses.Signal;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main (String[] args) throws NoDirectoryException, IOException, ClassNotFoundException {
        var convText  = new ConverterText("C:\\Games\\Programs\\DataSets\\TextNet\\Texts",
                TokenType.Word, LanguageStemmer.EN, 200);
        var batch = new Batch(convText.dates, 1);
        var net = CreateTestText();
        net.Train(batch);
        var result = net.Test(batch);
        ShowResult(result);
        System.out.println("END");
/*        PorterStemmerEN porter = new PorterStemmerEN();
        var r = porter.StemWord("sandcastles");
        System.out.println(r);*/
    }

    private static Network CreateTestText() {
        int classes = 2;
        Network net = new Network();
        EmbeddingLayer layerE = new EmbeddingLayer(1000, new Sigmoid());
        RecurrentLayer layer1 = new RecurrentLayer(120, new Sigmoid());
        RecurrentLayer layer2 = new RecurrentLayer(80, new Sigmoid());
        RecurrentLayer layer3 = new RecurrentLayer(60, new Sigmoid());
        FCCLayer layer4 = new FCCLayer(classes, new Softmax());
        net.AddLayer(layerE);
        net.AddLayer(layer1);
        net.AddLayer(layer1);
        net.AddLayer(layer2);
        net.AddLayer(layer3);
        net.AddLayer(layer4);
        net.SetEpoth(500);
        net.SetLearnRate(0.0925);
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
        net.AddLayer(cl1);
        net.AddLayer(pl1);
        net.AddLayer(cl2);
        net.AddLayer(pl2);
        net.AddLayer(layer1);
        net.AddLayer(layer2);
        net.AddLayer(layer3);
        net.AddLayer(layer4);
        net.SetEpoth(300);
        net.SetLearnRate(0.0925);
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

            Signal signal = new Signal(1,1,1, i);
            double value = rnd.nextDouble(0, 1);
            signal.setValueSignal(0,0,0, value);

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
