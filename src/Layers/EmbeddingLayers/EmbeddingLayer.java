package Layers.EmbeddingLayers;

import Layers.Enums.TypeLayer;
import Layers.Layer;
import SimpleClasses.Signal;

public class EmbeddingLayer extends Layer {

    private int embSize;

    public EmbeddingLayer(int embSize) {
        this.embSize = embSize;
        typeLayer = TypeLayer.Input;
    }

    @Override
    public Signal Forward(Signal input) {
        return null;
    }

    @Override
    public Signal BackPropagation(Signal delta, int Right, double E, double A) {
        return null;
    }
}
