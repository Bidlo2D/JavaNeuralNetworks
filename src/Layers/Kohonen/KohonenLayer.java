package Layers.Kohonen;
import Collector.Initializations.Generation;
import Collector.Network;
import Layers.Functions.Function;
import Layers.Functions.Activation.Softmax;
import Layers.FullyLayers.FCHLayer;
import Layers.Functions.Influence.Kohonen;
import SimpleClasses.ComputingUnits.Neuron;
import SimpleClasses.ComputingUnits.NeuronWTA;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.lang.reflect.InvocationTargetException;

public class KohonenLayer extends FCHLayer {
    protected Signal<NeuronWTA> output;
    protected Kohonen typeActivation;
    protected double loss = 0;
    private double a = 0.0915;
    private int indexWin;

    public KohonenLayer(int countNeurons, Kohonen typeInfluence) {
        super(countNeurons, typeInfluence);
        this.typeActivation = typeInfluence;
    }

    public Weight getWeight() {
        return weights.getCloneWeight();
    }

    @Override
    public Signal forward(Signal input) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.input = input;
        if(weights == null ||
                biases == null ||
                corrections == null ||
                output == null) { initialization(); }
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            //double Dis = 0;
            double Sum = biases.getValueSignal(w1, 0, 0);
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                Sum += input.getValueSignal(w2, 0, 0) * weights.getWeight(w1, w2);
                //Dis += Math.pow(input.getValueSignal(w2, 0, 0) - weights.getWeight(w1, w2), 2);
            }

            //distance.setValueSignal(w1, 0, 0, Math.sqrt(Dis));
            output.setValueSignal(w1, 0, 0, Sum);
        }
        typeActivation.activation();
        return output;
    }

    @Override
    public Signal backPropagation(Signal delta, int right, double A, double B) {
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                double oldW = weights.getWeight(w1, w2);
                double updateW = oldW + a * typeActivation.influence(w1) * (input.getValueSignal(w2, 0 , 0)  * B - oldW);
                a = A / Network.iteration + B;
                weights.setWeight(w1, w2, updateW);
            }
        }
        return null;
    }

    @Override
    protected void initialization() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int sizeZ = input.fullSize();
        Class o = Class.forName("SimpleClasses.ComputingUnits.NeuronWTA");
        Class d = Class.forName("SimpleClasses.ComputingUnits.Neuron");
        typeActivation.distance  = new Signal(d, countNeurons, 1, 1);
        output = new Signal(o, countNeurons, 1, 1);
        deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY);
        typeActivation.output = output;
        corrections = new Signal(countNeurons, 1,1);
        biases = Generation.RandomSignal(countNeurons, 1 , 1 , 0, 0, 0.1);
        weights = Generation.RandomWeight(countNeurons, sizeZ, -1, 1);
    }

}
