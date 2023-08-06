package Collector;

import Layers.Layer;
import Layers.Kohonen.KohonenLayer;
import SimpleClasses.Dates.Batch;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.awt.print.Book;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Network implements Serializable {
    private static final long serialVersionUID = 1L;
    public static int iteration = 1;
    private double L_rate = 0.01;
    private final double A_rate = 0.85; // offset
    private int epoth = 10;
    private int currentEpoth;
    private List<Layer> NeuralNetwork = new ArrayList();
    //private Weight graphInfo;

    public void setEpoth(int epoth) { this.epoth = epoth; }
    public int getEpoth () { return epoth; }
    public void setLearnRate(double L_rate) { this.L_rate = L_rate; }
    public void addLayer(Layer layer) { if(!NeuralNetwork.contains(layer)) { NeuralNetwork.add(layer); } }
    public void removeLayer(Layer layer) { NeuralNetwork.remove(layer); }
    public Weight getWeightLayer(int index) {
        var WTA = (KohonenLayer) NeuralNetwork.get(index);
        return WTA.getWeight();
    }

    public void Train(Batch input) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        iteration = 1;
        for(currentEpoth = 0; currentEpoth < epoth; currentEpoth++){
            long start = System.currentTimeMillis();
            for(int b = 0; b < input.miniBatches.size(); b++){
                for(int m = 0; m < input.miniBatches.get(b).signals.size(); m++) {
                    ForwardLayers(input.miniBatches.get(b).signals.get(m));
                    BackPropagationLayers(input.miniBatches.get(b).signals.get(m).right);
                    iteration++;
                }
            }
            long finish = System.currentTimeMillis();
            long elapsed = finish - start;
            System.out.println("epoth - " + (currentEpoth + 1) + ", time - " + elapsed / 1000.0 + "c");
        }
    }

    public List<Map<Integer, Double>> Test(Batch input) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Map<Integer, Double>> answers = new ArrayList();
        for(int b = 0; b < input.miniBatches.size(); b++) {
            for(int m = 0; m < input.miniBatches.get(b).signals.size(); m++) {
                answers.add(ForwardLayers(input.miniBatches.get(b).signals.get(m)));
            }
        }
        return answers;
    }

    private Map<Integer, Double> ForwardLayers(Signal signal) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        for(int l = 0; l < NeuralNetwork.size(); l++) {
            signal = NeuralNetwork.get(l).forward(signal);
        }
        return signal.getAnswer();
    }

    private void BackPropagationLayers(int right) {
        Signal delta = new Signal();
        for(int l = NeuralNetwork.size() - 1; l >= 0; l--) {
            delta = NeuralNetwork.get(l).backPropagation(delta, right, L_rate, A_rate);
        }
    }
}
