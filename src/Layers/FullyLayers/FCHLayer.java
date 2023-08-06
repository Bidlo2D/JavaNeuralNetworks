package Layers.FullyLayers;
import Collector.Initializations.Generation;
import Collector.Network;
import Layers.Functions.Function;
import Layers.Functions.Activation.Softmax;
import Layers.Layer;
import SimpleClasses.ComputingUnits.NeuronFC;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FCHLayer extends Layer<NeuronFC, Function> {
    protected Weight weights;
    protected Signal biases;
    protected Signal corrections;
    protected int countNeurons;

    public FCHLayer(int countNeurons, Function typeActivation){
        this.countNeurons = countNeurons;
        this.typeActivation = typeActivation;
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

    @Override
    public Signal forward(Signal input) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.input = input;
        if(weights == null ||
           biases == null ||
           corrections == null ||
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
        activation(output);
        return output;
    }

    protected void activation(Signal activation) {
        for (int i = 0; i < activation.fullSize(); i++)
        {
            var neuron = activation.getSignal(i, 0, 0);
            double active = typeActivation.activation(neuron);
            activation.setValueSignal(i, 0, 0, active);
        }
    }

    @Override
    public Signal backPropagation(Signal delta, int right, double E, double A) {
        Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY);
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            double derivative = 0;
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                // Gradient next
                derivative = typeActivation.derivative(input.getSignal(w2, 0, 0)) * delta.getValueSignal(w1, 0, 0); // derivative
                double gradient = derivative * weights.getWeight(w1, w2); // for next layer
                double updateGradient = gradient + deltaOutput.getValueSignal(w2, 0, 0);
                deltaOutput.setValueSignal(w2, 0, 0, updateGradient); // for next layer

                // Weights
                double GRADw = input.getValueSignal(w2, 0, 0) * delta.getValueSignal(w1, 0, 0); // for this layer
                double updateW = E * GRADw + A * corrections.getValueSignal(w1, 0, 0); // calculating the update of weight
                corrections.setValueSignal(w1, 0, 0, updateW); // remember value update
                weights.setWeight(w1, w2, weights.getWeight(w1, w2) + corrections.getValueSignal(w1, 0, 0)); // update weight
            }
            // Biases
            double updateBias = (derivative * E) + biases.getValueSignal(w1, 0, 0);
            biases.setValueSignal(w1, 0, 0, updateBias); // update bias
        }
        return deltaOutput;
    }

    protected void initialization() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int sizeZ = input.fullSize();
        Class s = Class.forName("SimpleClasses.ComputingUnits.NeuronFC");
        output = new Signal(s, countNeurons, 1, 1);
        if(typeActivation.getClass().getSimpleName().equals("Softmax")){
            Softmax castedDog = (Softmax) typeActivation;
            castedDog.output = output;
        }
        corrections = new Signal(countNeurons, 1,1);
        biases = Generation.RandomSignal(countNeurons, 1 , 1 , 0, -0.1, 0.1);
        weights = Generation.RandomWeight(countNeurons, sizeZ, -0.05, 0.05);
    }
}
