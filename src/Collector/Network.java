package Collector;

import Layers.Layer;
import SimpleClasses.Batch;
import SimpleClasses.Signal;

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
            for(int b = 0; b < input.miniBatches.length - 1; b++){
                for(int m = 0; m < input.miniBatches[b].signals.length - 1; m++){
                    ForwardLayers(input.miniBatches[b].signals[m]);
                    BackPropagationLayers();
                }
            }
        }
    }
    private void ForwardLayers(Signal signal){
        for(int l = 0; l < NeuralNetwork.size(); l++){
            signal = NeuralNetwork.get(l).Forward(signal);
        }
    }
    private void BackPropagationLayers(){
        for(int l = NeuralNetwork.size() - 1; l > 0; l--){
            Signal delta = new Signal();
            delta = NeuralNetwork.get(l).BackPropagation(delta,0,1,0);
        }
    }
}
