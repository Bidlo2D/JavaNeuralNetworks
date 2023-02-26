package Layers.WTA;
import Collector.Initializations.Generation;
import Collector.Network;
import Layers.Activation.Functions.Function;
import Layers.Activation.Functions.Softmax;
import Layers.FullyLayers.FCHLayer;
import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.ComputingUnits.NeuronFC;
import SimpleClasses.ComputingUnits.NeuronWTA;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WTALayer extends FCHLayer {
    protected Signal<NeuronWTA> output;
    protected double loss = 0;
    private int indexWin;

    public WTALayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
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
            double Sum = biases.getValueSignal(w1, 0, 0);
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                Sum += input.getValueSignal(w2, 0, 0) * weights.getWeight(w1, w2);
                //Sum += Math.pow(input.getValueSignal(w2, 0, 0) - weights.getWeight(w1, w2), 2);
            }

            //output.setValueSignal(w1, 0, 0, Math.sqrt(Sum));
            output.setValueSignal(w1, 0, 0, Sum);
        }
        activation(output);
        return output;
    }

    @Override
    protected void activation(Signal activation) {
        var winner = output.max();
        indexWin = output.indexOf(winner);
        winner.win();
        //output.allZero();
        winner.setValue(1);
    }

    public Signal backPropagation(Signal delta, int right, double E, double A) {
        //delta = errorCounting();
        //Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY);
        for (int w1 = 0; w1 <= indexWin; w1++)
        {
            //double df = 0;
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                //df += weights.getWeight(w1, w2) * Math.exp(-sourceNet.getCurrentEpoth() / sourceNet.getEpoth());
                double oldW = weights.getWeight(w1, w2);
                double updateW = oldW + E * (input.getValueSignal(w2, 0 , 0) * A - oldW);
                weights.setWeight(w1, w2, updateW);
            }
        }
        return null;
    }

    protected Signal errorCounting() {
        double error = 0; loss = 0;
        Signal deltaOutput = new Signal(output.sizeZ, output.sizeX, output.sizeY);
        return deltaOutput;
    }

    @Override
    protected void initialization() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int sizeZ = input.fullSize();
        Class s = Class.forName("SimpleClasses.ComputingUnits.NeuronWTA");
        output = new Signal(s, countNeurons, 1, 1);
        deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY);
        if(typeActivation.getClass().getSimpleName().equals("Softmax")){
            Softmax castedDog = (Softmax) typeActivation;
            castedDog.output = output;
        }
        corrections = new Signal(countNeurons, 1,1);
        biases = Generation.RandomSignal(countNeurons, 1 , 1 , 0, 0, 0.1);
        weights = Generation.RandomWeight(countNeurons, sizeZ, -1, 1);
    }

}
