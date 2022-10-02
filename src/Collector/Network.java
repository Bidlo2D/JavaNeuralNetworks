package Collector;

import Layers.Layer;
import SimpleClasses.Batch;

import java.util.ArrayList;
import java.util.List;

public class Network {
    public double rateL = 0.01;
    public int epoth = 10;
    private List<Layer> NeuralNetwork = new ArrayList();
    public void Network(){

    }
    public void AddLayer(Layer layer) { if(!NeuralNetwork.contains(layer)){ NeuralNetwork.add(layer); } }
    public void RemoveLayer(Layer layer){ NeuralNetwork.remove(layer); }
    public void Train(Batch input){
        for(int e = 0; e < epoth; e++){
            for(int b = 0; b < input.batches.length - 1; b++){
                for(int m = 0; m < input.batches[b].batch.length - 1; m++){
                    NeuralNetwork.get(e).Forward(input.batches[b].batch[m]);
                }
            }
        }
    }
}
