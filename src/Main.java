import Collector.Initializations.Generation;
import Collector.Network;
import Layers.Activation.TypeActivation;
import Layers.FCCLayer;
import Layers.FCHLayer;
import SimpleClasses.Batch;
import SimpleClasses.Signal;

public class Main {
    public static void main (String[] args){
        Network net = new Network();
        FCHLayer layer1 = new FCHLayer(2, TypeActivation.Sigmoid);
        FCHLayer layer2 = new FCHLayer(2, TypeActivation.Sigmoid);
        FCCLayer layer3 = new FCCLayer(5, TypeActivation.Softmax);
        net.AddLayer(layer1);
        net.AddLayer(layer2);
        net.AddLayer(layer3);
        Batch batchTest = new Batch(3,5);
        net.Train(batchTest);
        System.out.println("");
    }
}
