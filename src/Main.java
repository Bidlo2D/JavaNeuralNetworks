import Collector.Network;
import Layers.ConvLayers.ConvolutionLayer;
import Layers.ConvLayers.PoolingLayer;
import Layers.EmbeddingLayers.EmbeddingLayer;
import Layers.FullyLayers.FCCLayer;
import Layers.FullyLayers.FCHLayer;
import Layers.Functions.Activation.ReLU;
import Layers.Functions.Activation.Sigmoid;
import Layers.Functions.Activation.Softmax;
import Layers.Functions.Activation.Tangent;
import Layers.Functions.Influence.WTA;
import Layers.RecurrentLayers.RecurrentLayer;
import Layers.Kohonen.KohonenLayer;
import SimpleClasses.Dates.Batch;
import SimpleClasses.Dates.Converters.ConverterImage;
import SimpleClasses.Dates.Converters.Enums.TypeImage;
import SimpleClasses.Dates.Converters.Exceptions.NoDirectoryException;
import SimpleClasses.Dates.Converters.Other.RangeNorm;
import SimpleClasses.Dates.MiniBatch;
import SimpleClasses.Signal;
import org.knowm.xchart.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    public static void main (String[] args) throws NoDirectoryException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var range = new RangeNorm(-1.0, 1.0);
        var convImage = new ConverterImage("C:\\Games\\Programs\\DataSets\\Numbers(32x32) - 0 - 1 - 2", TypeImage.BW, range);
        var batch = new Batch(convImage.dates, 1);
        //var batch = TestDataClasses();
        var net = CreateTestVision();
        net.Train(batch);
        ShowResult(net.Test(batch));
        System.out.println("END");
    }

    private void KohenGraph() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        var batch = TestDataClasses();
        var net = CreateTestText();
        net.Train(batch);
        var result = net.Test(batch);
        double[] x1Data = net.getWeightLayer(0).getWeightByParam(0).stream().mapToDouble(d -> d).toArray();
        double[] x2Data = net.getWeightLayer(0).getWeightByParam(1).stream().mapToDouble(d -> d).toArray();
        XYChart chart = new XYChartBuilder().width(600).height(500).title("Gaussian Blobs").xAxisTitle("X").yAxisTitle("Y").build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        var flat = batch.FlatBatch();
        var x3Data = separateInput(flat,0,2);
        var x4Data = separateInput(flat,1,2);
        var series1 = chart.addSeries("X1", x1Data, x2Data);
        var series2 = chart.addSeries("X2", x3Data, x4Data);
        new SwingWrapper(chart).displayChart();
    }

    private static Network CreateTestText() {
        int classes = 2;
        Network net = new Network();
        //EmbeddingLayer layerE = new EmbeddingLayer(30, new Tangent());
        KohonenLayer layerWTA = new KohonenLayer(4, new WTA(), 0.0916);
        //RecurrentLayer layerR1 = new RecurrentLayer(30, new Tangent());
        //FCHLayer layerFCH1 = new FCHLayer(80, new Sigmoid());
        //FCHLayer layerFCH2 = new FCHLayer(80, new Sigmoid());
        //RecurrentLayer layerR2 = new RecurrentLayer(25, new Tangent());
        //FCCLayer layerC = new FCCLayer(classes, new Softmax());
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
        int classes = 3;
        Network net = new Network();

        ConvolutionLayer cl1 = new ConvolutionLayer(5,16,1, 0);
        PoolingLayer pl1 = new PoolingLayer(2);
        ConvolutionLayer cl2 = new ConvolutionLayer(3,34,3, 0);
        PoolingLayer pl2 = new PoolingLayer(2);
        FCHLayer layer1 = new FCHLayer(75, new Tangent());
        FCHLayer layer2 = new FCHLayer(150, new Tangent());
        FCHLayer layer3 = new FCHLayer(75, new Tangent());
        FCCLayer layer4 = new FCCLayer(classes, new Softmax());
        //net.addLayer(cl1);
        //net.addLayer(pl1);
        //net.addLayer(cl2);
        //net.addLayer(pl2);
        net.addLayer(layer1);
        net.addLayer(layer2);
        net.addLayer(layer3);
        net.addLayer(layer4);
        net.setEpoth(1000);
        net.setLearnRate(0.00925);
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

    private static Batch TestDataRandomClasses(int classes, int size) {
        Random rnd = new Random();
        Batch data = new Batch();
        for (int i = 0; i < classes; i++){
            MiniBatch mini = new MiniBatch();

            Signal signal = new Signal(size,1,1, i);
            for(int s = 0; s < signal.fullSize(); s++){
                double value = rnd.nextDouble(-1, 1);
                signal.setValueSignal(s,0,0, value);
            }

            mini.signals.add(signal);

            data.miniBatches.add(mini);
        }

        return data;
    }

    private static Batch TestDataClasses() {
        Random rnd = new Random();
        Batch data = new Batch();
        MiniBatch mini = new MiniBatch();
        //Signal signal1 = new Signal(0, new double[]{0.97, 0.80, 0.08});
        //Signal signal2 = new Signal(1,new double[]{-0.97, -0.80, -0.61});
        //Signal signal3 = new Signal(2,new double[]{-0.014, 0.747, -0.047});
        //Signal signal4 = new Signal(1,new double[]{-0.67, -0.74, -0.001});
        //Signal signal5 = new Signal(0,new double[]{0.80, 0.60, 0.755});
        //Signal signal6 = new Signal(1,new double[]{0.00, 1.00, -0.41});
        //Signal signal7 = new Signal(0,new double[]{0.20, 0.97, 0.145});
        //Signal signal8 = new Signal(1,new double[]{-0.30, -0.95, 0.80});

        Signal signal1 = new Signal(0, new double[]{0.974542, 0.4145723, 0.745452});
        Signal signal2 = new Signal(1, new double[]{0.7345412, 0.8522585, 0.205245});
        Signal signal3 = new Signal(2, new double[]{0.7554524, 0.08524276, 0.674545});

        mini.signals.add(signal1);
        mini.signals.add(signal2);
        mini.signals.add(signal3);
        //mini.signals.add(signal4);
        //mini.signals.add(signal5);
        //mini.signals.add(signal6);
        //mini.signals.add(signal7);
        //mini.signals.add(signal8);
        data.miniBatches.add(mini);

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

    private static double[] separateInput(List<Double> list, int param, int countParams){
        List<Double> list2 = new ArrayList<>();
        for(int i = param; i < list.size(); i+=countParams) {
            list2.add(list.get(i));
        }
        return list2.stream().mapToDouble(d -> d).toArray();
    }

    private static void ShowSignal(Signal input, RangeNorm range){
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RED = "\u001B[31m";
        DecimalFormat decimalFormat = new DecimalFormat( "#0.00" );
        for(int z = 0; z < input.sizeZ; z++){
            for(int x = 0; x < input.sizeX; x++) {
                for(int y = 0;y < input.sizeY; y++) {
                    var value = input.getValueSignal(z, x, y);
                    String result = decimalFormat.format(value);
                    if(value >= range.getMax() / 2.0){
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
