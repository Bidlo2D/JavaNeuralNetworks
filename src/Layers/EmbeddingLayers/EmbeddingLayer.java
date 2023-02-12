package Layers.EmbeddingLayers;
import Layers.Activation.Functions.Function;
import Layers.FullyLayers.FCHLayer;

public class EmbeddingLayer extends FCHLayer {
    public EmbeddingLayer(int vocabulary, Function typeActivation) {
        super(vocabulary, typeActivation);
    }
}
