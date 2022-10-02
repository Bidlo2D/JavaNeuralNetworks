import Collector.Initializations.Generation;
import Collector.Network;
import Layers.Activation.TypeActivation;
import Layers.FCHLayer;
import SimpleClasses.Signal;

public class Main {
    public static void main (String[] args){
        Network net = new Network();
        FCHLayer layer1 = new FCHLayer(2, TypeActivation.Input);
        FCHLayer layer2 = new FCHLayer(2, TypeActivation.Input);
        net.AddLayer(layer1);
        net.AddLayer(layer2);
        Signal test = Generation.RandomSignal(3,2,2,0);
        layer1.Forward(test);
        System.out.println("");
    }
}
