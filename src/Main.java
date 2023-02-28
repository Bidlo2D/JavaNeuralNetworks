import Collector.Network;
import Layers.ConvLayers.ConvolutionLayer;
import Layers.ConvLayers.PoolingLayer;
import Layers.EmbeddingLayers.EmbeddingLayer;
import Layers.FullyLayers.FCCLayer;
import Layers.FullyLayers.FCHLayer;
import Layers.Functions.Activation.Sigmoid;
import Layers.Functions.Activation.Softmax;
import Layers.Functions.Activation.Tangent;
import Layers.Functions.Influence.WTA;
import Layers.RecurrentLayers.RecurrentLayer;
import Layers.Kohonen.KohonenLayer;
import SimpleClasses.Dates.Batch;
import SimpleClasses.Dates.Converters.Exceptions.NoDirectoryException;
import SimpleClasses.Dates.MiniBatch;
import SimpleClasses.Signal;
import org.knowm.xchart.*;

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
        var batch = TestDataClasses();
        var net = CreateTestText();
        net.Train(batch);
        //var result = net.getGraphInfo();
        var result = net.Test(batch);
        //var data = result.stream().map(m -> m.get)
        //ShowResult(result);
        //GraphPanel draw = new GraphPanel(net.getGraphInfo().getFlat());
        double[] x1Data = net.getWeightLayer(0).getWeightByParam(0).stream().mapToDouble(d -> d).toArray();
        double[] x2Data = net.getWeightLayer(0).getWeightByParam(1).stream().mapToDouble(d -> d).toArray();
        //double[] x3Data = net.getGraphInfo().getWeightByParam(2).stream().mapToDouble(d -> d).toArray();
        XYChart chart = new XYChartBuilder().width(600).height(500).title("Gaussian Blobs").xAxisTitle("X").yAxisTitle("Y").build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        var flat = batch.FlatBatch();
        var x3Data = separateInput(flat,0,2);
        var x4Data = separateInput(flat,1,2);
        var series1 = chart.addSeries("X1", x1Data, x2Data);
        var series2 = chart.addSeries("X2", x3Data, x4Data);
        //chart.addSeries("X2", x2Data, x1Data);
        //chart.addSeries("X3", x1Data, x3Data);
        //var series2 = chart.addSeries("X2", x2Data, x1Data);
        //series1.setMarker(SeriesMarkers.DIAMOND);
        //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        new SwingWrapper(chart).displayChart();
        //WTALayer WTALayer1= new WTALayer(5, new Sigmoid());
        //WTALayer1.forward(batch.miniBatches.get(0).signals.get(0));
        System.out.println("END");
    }

    private static Network CreateTestText() {
        int classes = 2;
        Network net = new Network();
        //EmbeddingLayer layerE = new EmbeddingLayer(30, new Tangent());
        KohonenLayer layerWTA = new KohonenLayer(4, new WTA());
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
        Batch data = new Batch();
        MiniBatch mini = new MiniBatch();
        Signal signal1 = new Signal(new double[]{0.97, 0.80});
        Signal signal2 = new Signal(new double[]{-0.97, -0.80});
        Signal signal3 = new Signal(new double[]{-0.5, 0.50});
        Signal signal4 = new Signal(new double[]{-0.67, 0.74});
        Signal signal5 = new Signal(new double[]{-0.80, 0.60});
        Signal signal6 = new Signal(new double[]{0.00, -1.00});
        Signal signal7 = new Signal(new double[]{0.20, -0.97});
        Signal signal8 = new Signal(new double[]{-0.30, -0.95});
        mini.signals.add(signal1);
        mini.signals.add(signal2);
        mini.signals.add(signal3);
        mini.signals.add(signal4);
        mini.signals.add(signal5);
        mini.signals.add(signal6);
        mini.signals.add(signal7);
        mini.signals.add(signal8);
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
