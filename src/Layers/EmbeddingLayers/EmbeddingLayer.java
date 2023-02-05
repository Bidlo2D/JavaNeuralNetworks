package Layers.EmbeddingLayers;

import Collector.Initializations.Generation;
import Layers.Activation.Functions.Function;
import Layers.Activation.Functions.Softmax;
import Layers.FullyLayers.FCHLayer;
import Layers.Layer;
import SimpleClasses.Neuron;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmbeddingLayer extends FCHLayer {
    public EmbeddingLayer(int vocabulary, Function typeActivation) {
        super(vocabulary, typeActivation);
    }
}
