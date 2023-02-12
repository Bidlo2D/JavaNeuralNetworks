package Collector;

import Layers.Layer;
import SimpleClasses.Dates.Batch;
import SimpleClasses.Signal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Network implements Serializable {
    private static final long serialVersionUID = 1L;
    private double L_rate = 0.01;
    private final double A_rate = 0.3;
    private int epoth = 10;
    private List<Layer> NeuralNetwork = new ArrayList();

    public void SetEpoth (int epoth) { this.epoth = epoth; }
    public void SetLearnRate (double L_rate) { this.L_rate = L_rate; }
    public void AddLayer (Layer layer) { if(!NeuralNetwork.contains(layer)) { NeuralNetwork.add(layer); } }
    public void RemoveLayer (Layer layer) { NeuralNetwork.remove(layer); }

    public void Train(Batch input) {
        for(int e = 0; e < epoth; e++){
            long start = System.currentTimeMillis();
            for(int b = 0; b < input.miniBatches.size(); b++){
                for(int m = 0; m < input.miniBatches.get(b).signals.size(); m++) {
                    ForwardLayers(input.miniBatches.get(b).signals.get(m));
                    BackPropagationLayers(input.miniBatches.get(b).signals.get(m).right);
                }
            }
            long finish = System.currentTimeMillis();
            long elapsed = finish - start;
            System.out.println("epoth - " + (e + 1) + ", time - " + elapsed / 1000 + "c");
        }
    }
    public List<Map<Integer, Double>> Test(Batch input) {
        List<Map<Integer, Double>> answers = new ArrayList();
        for(int b = 0; b < input.miniBatches.size(); b++) {
            for(int m = 0; m < input.miniBatches.get(b).signals.size(); m++) {
                answers.add(ForwardLayers(input.miniBatches.get(b).signals.get(m)));
            }
        }
        return answers;
    }

    private Map<Integer, Double> ForwardLayers(Signal signal){
        for(int l = 0; l < NeuralNetwork.size(); l++) {
            signal = NeuralNetwork.get(l).Forward(signal);
        }

        return signal.getAnswer();
    }

    private void BackPropagationLayers(int right) {
        Signal delta = new Signal();
        for(int l = NeuralNetwork.size() - 1; l >= 0; l--) {
            delta = NeuralNetwork.get(l).BackPropagationTeacher(delta, right, L_rate, A_rate);
        }
    }
}
