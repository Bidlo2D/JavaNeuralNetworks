package Layers.Kohonen;
import Collector.Initializations.Generation;
import Collector.Network;
import Layers.FullyLayers.FCHLayer;
import Layers.Functions.Function;
import Layers.Functions.Kohonen;
import Layers.Layer;
import SimpleClasses.ComputingUnits.NeuronWTA;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class KohonenLayer extends Layer<NeuronWTA, Kohonen> {
    private double dynamicsA;
    private Weight weights;
    private Signal biases;
    protected int countNeurons;

    public KohonenLayer(int countNeurons, Kohonen typeInfluence, double dynamicsA) {
        this.countNeurons = countNeurons;
        this.typeActivation = typeInfluence;
        this.dynamicsA = dynamicsA;
    }

    public Weight getWeight() {
        return weights.getCloneWeight();
    }

    @Override
    public Signal forward(Signal input) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.input = input;
        if(weights == null ||
                biases == null ||
                output == null) { initialization(); }
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            double Sum = biases.getValueSignal(w1, 0, 0);
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                Sum += input.getValueSignal(w2, 0, 0) * weights.getWeight(w1, w2);
            }

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
                double updateW = oldW + dynamicsA * typeActivation.influence(w1) * (input.getValueSignal(w2, 0 , 0)  * B - oldW);
                weights.setWeight(w1, w2, updateW);
            }
        }
        dynamicsA = A / Network.iteration + B;
        return null;
    }

    @Override
    public List<Double> getWeightList() {
        List<Double> weightsList = new ArrayList<>();
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                weightsList.add(weights.getWeight(w1, w2));
            }
        }
        return  weightsList;
    }

    protected void initialization() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int sizeZ = input.fullSize();
        Class o = Class.forName("SimpleClasses.ComputingUnits.NeuronWTA");
        Class d = Class.forName("SimpleClasses.ComputingUnits.Neuron");
        typeActivation.distance  = new Signal(d, countNeurons, 1, 1);
        output = new Signal(o, countNeurons, 1, 1);
        typeActivation.output = output;
        biases = Generation.RandomSignal(countNeurons, 1 , 1 , 0, 0, 0.1);
        weights = Generation.RandomWeight(countNeurons, sizeZ, -1, 1);
    }

}
